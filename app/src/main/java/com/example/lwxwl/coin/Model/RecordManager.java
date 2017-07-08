/*package com.example.lwxwl.coin.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lwxwl.coin.BuildConfig;
import com.example.lwxwl.coin.Data.DB;
import com.example.lwxwl.coin.R;
import com.example.lwxwl.coin.Utils.CoinToast;
import com.example.lwxwl.coin.Utils.CoinUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RecordManager {

    private static RecordManager recordManager = null;

    private static DB db;

    // the selected values in list activity
    public static Double SELECTED_SUM;
    public static List<CoinRecord> SELECTED_RECORDS;

    public static Integer SUM;
    public static List<CoinRecord> RECORDS;
    public static List<Tag> TAGS;
    public static Map<Integer, String> TAG_NAMES;

    public static boolean RANDOM_DATA = false;
    private final int RANDOM_DATA_NUMBER_ON_EACH_DAY = 3;
    private final int RANDOM_DATA_EXPENSE_ON_EACH_DAY = 30;

    private static boolean FIRST_TIME = true;

    public static int SAVE_TAG_ERROR_DATABASE_ERROR = -1;
    public static int SAVE_TAG_ERROR_DUPLICATED_NAME = -2;

    public static int DELETE_TAG_ERROR_DATABASE_ERROR = -1;
    public static int DELETE_TAG_ERROR_TAG_REFERENCE = -2;

    private RecordManager(Context context) {
        try {
            db = db.getInstance(context);
            if (BuildConfig.DEBUG) if (BuildConfig.DEBUG) Log.d("Coin", "db.getInstance(context) S");
        } catch(IOException e) {
            e.printStackTrace();
        }
        if (FIRST_TIME) {
// if the app starts firstly, create tags
            SharedPreferences preferences = context.getSharedPreferences("Values", Context.MODE_PRIVATE);
            if (preferences.getBoolean("FIRST_TIME", true)) {
                createTags();
                SharedPreferences.Editor editor =
                        context.getSharedPreferences("Values", Context.MODE_PRIVATE).edit();
                editor.putBoolean("FIRST_TIME", false);
                editor.commit();
            }
        }
        if (RANDOM_DATA) {

            SharedPreferences preferences =
                    context.getSharedPreferences("Values", Context.MODE_PRIVATE);
            if (preferences.getBoolean("RANDOM", false)) {
                return;
            }

            randomDataCreater();

            SharedPreferences.Editor editor =
                    context.getSharedPreferences("Values", Context.MODE_PRIVATE).edit();
            editor.putBoolean("RANDOM", true);
            editor.commit();

        }
    }

    // getInstance
    public synchronized static RecordManager getInstance(Context context) {
        if (RECORDS == null || TAGS == null || TAG_NAMES == null || SUM == null || recordManager == null) {
            SUM = 0;
            RECORDS = new LinkedList<>();
            TAGS = new LinkedList<>();
            TAG_NAMES = new HashMap<>();
            recordManager = new RecordManager(context);

            db.getData();

            if (BuildConfig.DEBUG) {
                if (BuildConfig.DEBUG) Log.d("Coin", "Load " + RECORDS.size() + " records S");
                if (BuildConfig.DEBUG) Log.d("Coin", "Load " + TAGS.size() + " tags S");
            }

            TAGS.add(0, new Tag(-1, "Sum Histogram", -4));
            TAGS.add(0, new Tag(-2, "Sum Pie", -5));

            for (Tag tag : TAGS) TAG_NAMES.put(tag.getId(), tag.getName());

            sortTAGS();
        }
        return recordManager;
    }

    // saveRecord
    public static long saveRecord(final CoinRecord coinRecord) {
        long insertId = -1;
        // this is a new CoinRecord, which is not uploaded
        coinRecord.setIsUploaded(false);
        if (BuildConfig.DEBUG)
            if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.saveRecord: Save " + coinRecord.toString() + " S");
        insertId = db.saveRecord(coinRecord);
        if (insertId == -1) {
            if (BuildConfig.DEBUG)
                if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.saveRecord: Save the above coCoinRecord FAIL!");
            CoinToast.getInstance()
                    .showToast(R.string.save_failed_local, R.color.pink);
        } else {
            if (BuildConfig.DEBUG)
                if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.saveRecord: Save the above coCoinRecord SUCCESSFULLY!");
            RECORDS.add(coinRecord);
            SUM += (int) coinRecord.getMoney();
            CoinToast.getInstance()
                    .showToast(R.string.save_successfully_local, R.color.purple);
        }
        return insertId;
    }

    // save tag
    public static int saveTag(Tag tag) {
        int insertId = -1;
        if (BuildConfig.DEBUG) {
            if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.saveTag: " + tag.toString());
        }
        boolean duplicatedName = false;
        for (Tag t : TAGS) {
            if (t.getName().equals(tag.getName())) {
                duplicatedName = true;
                break;
            }
        }
        if (duplicatedName) {
            return SAVE_TAG_ERROR_DUPLICATED_NAME;
        }
        insertId = db.saveTag(tag);
        if (insertId == -1) {
            if (BuildConfig.DEBUG) {
                if (BuildConfig.DEBUG) Log.d("Coin", "Save the above tag FAIL!");
                return SAVE_TAG_ERROR_DATABASE_ERROR;
            }
        } else {
            if (BuildConfig.DEBUG) {
                if (BuildConfig.DEBUG) Log.d("Coin", "Save the above tag SUCCESSFULLY!");
            }
            TAGS.add(tag);
            TAG_NAMES.put(tag.getId(), tag.getName());
            sortTAGS();
        }
        return insertId;
    }

    // delete a CoinRecord
    public static long deleteRecord(final CoinRecord coinRecord, boolean deleteInList) {
        long deletedNumber = db.deleteRecord(coinRecord.getId());
        if (deletedNumber > 0) {
            if (BuildConfig.DEBUG) Log.d("Coin",
                    "recordManager.deleteRecord: Delete " + coinRecord.toString() + " S");
            User user = BmobUser.getCurrentUser(CoinApplication.getAppContext(), User.class);

            CoinToast.getInstance()
                    .showToast(R.string.delete_successfully_local, R.color.pink);
            // update RECORDS list and SUM
            SUM -= (int) coinRecord.getMoney();
            if (deleteInList) {
                int size = RECORDS.size();
                for (int i = 0; i < RECORDS.size(); i++) {
                    if (RECORDS.get(i).getId() == coinRecord.getId()) {
                        RECORDS.remove(i);
                        if (BuildConfig.DEBUG) Log.d("Coin",
                                "recordManager.deleteRecord: Delete in RECORD " + coinRecord.toString() + " S");
                        break;
                    }
                }
            }
        } else {
            if (BuildConfig.DEBUG) Log.d("Coin",
                    "recordManager.deleteRecord: Delete " + coinRecord.toString() + " F");
            CoinToast.getInstance()
                    .showToast(R.string.delete_failed_local, R.color.pink);
        }


        return coinRecord.getId();
    }

    public static int deleteTag(int id) {
        int deletedId = -1;
        if (BuildConfig.DEBUG) Log.d("Coin",
                "Manager: Delete tag: " + "Tag(id = " + id + ", deletedId = " + deletedId + ")");
        boolean tagReference = false;
        for (CoinRecord coinRecord : RECORDS) {
            if (coinRecord.getTag() == id) {
                tagReference = true;
                break;
            }
        }
        if (tagReference) {
            return DELETE_TAG_ERROR_TAG_REFERENCE;
        }
        deletedId = db.deleteTag(id);
        if (deletedId == -1) {
            if (BuildConfig.DEBUG) Log.d("Coin", "Delete the above tag FAIL!");
            return DELETE_TAG_ERROR_DATABASE_ERROR;
        } else {
            if (BuildConfig.DEBUG) Log.d("Coin", "Delete the above tag SUCCESSFULLY!");
            for (Tag tag : TAGS) {
                if (tag.getId() == deletedId) {
                    TAGS.remove(tag);
                    break;
                }
            }
            TAG_NAMES.remove(id);
            sortTAGS();
        }
        return deletedId;
    }

    private static int p;
    public static long updateRecord(final CoinRecord coinRecord) {
        long updateNumber = db.updateRecord(coinRecord);
        if (updateNumber <= 0) {
            if (BuildConfig.DEBUG) {
                if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateRecord " + coinRecord.toString() + " F");
            }
            CoinToast.getInstance().showToast(R.string.update_failed_local, R.color.pink);
        } else {
            if (BuildConfig.DEBUG) {
                if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateRecord " + coinRecord.toString() + " S");
            }
            p = RECORDS.size() - 1;
            for (; p >= 0; p--) {
                if (RECORDS.get(p).getId() == coinRecord.getId()) {
                    SUM -= (int)RECORDS.get(p).getMoney();
                    SUM += (int) coinRecord.getMoney();
                    RECORDS.get(p).set(coinRecord);
                    break;
                }
            }
            coinRecord.setIsUploaded(false);

            db.updateRecord(coinRecord);
            CoinToast.getInstance().showToast(R.string.update_successfully_local, R.color.purple);
        }
        return updateNumber;
    }

    // update the records changed to server/////////////////////////////////////////////////////////////
    private static boolean isLastOne = false;
    public static long updateOldRecordsToServer() {
        long counter = 0;
        User user = BmobUser
                .getCurrentUser(CoinApplication.getAppContext(), User.class);
        if (user != null) {
// already login////////////////////////////////////////////////////////////////////////////////////
            isLastOne = false;
            for (int i = 0; i < RECORDS.size(); i++) {
                if (i == RECORDS.size() - 1) isLastOne = true;
                final CoinRecord coinRecord = RECORDS.get(i);
                if (!coinRecord.getIsUploaded()) {
// has been changed/////////////////////////////////////////////////////////////////////////////////
                    if (coinRecord.getLocalObjectId() != null) {
// there is an old coCoinRecord in server, we should update this coCoinRecord///////////////////////////////////
                        coinRecord.setUserId(user.getObjectId());
                        coinRecord.update(CoinApplication.getAppContext(),
                                coinRecord.getLocalObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        if (BuildConfig.DEBUG) {
                                            if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateOldRecordsToServer update online " + coinRecord.toString() + " S");
                                        }
                                        coinRecord.setIsUploaded(true);
                                        coinRecord.setLocalObjectId(coinRecord.getObjectId());
                                        db.updateRecord(coinRecord);
// after updating, get the old records from server//////////////////////////////////////////////////
                                        if (isLastOne) getRecordsFromServer();
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        if (BuildConfig.DEBUG) {
                                            if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateOldRecordsToServer update online " + coinRecord.toString() + " F");
                                        }
                                        if (BuildConfig.DEBUG) {
                                            if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateOldRecordsToServer update online code" + code + " msg " + msg );
                                        }
                                    }
                                });
                    } else {
                        counter++;
                        coinRecord.setUserId(user.getObjectId());
                        coinRecord.save(CoinApplication.getAppContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                if (BuildConfig.DEBUG) {
                                    if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateOldRecordsToServer save online " + coinRecord.toString() + " S");
                                }
                                coinRecord.setIsUploaded(true);
                                coinRecord.setLocalObjectId(coinRecord.getObjectId());
                                db.updateRecord(coinRecord);
// after updating, get the old records from server//////////////////////////////////////////////////
                                if (isLastOne) getRecordsFromServer();
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                if (BuildConfig.DEBUG) {
                                    if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateOldRecordsToServer save online " + coinRecord.toString() + " F");
                                }
                                if (BuildConfig.DEBUG) {
                                    if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateOldRecordsToServer save online code" + code + " msg " + msg );
                                }
                            }
                        });
                    }
                }
            }
        } else {

        }

        if (BuildConfig.DEBUG) {
            if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.updateOldRecordsToServer update " + counter + " records to server.");
        }

        if (RECORDS.size() == 0) getRecordsFromServer();

        return counter;
    }

    public static long updateTag(Tag tag) {
        int updateId = -1;
        if (BuildConfig.DEBUG) Log.d("Coin",
                "Manager: Update tag: " + tag.toString());
        updateId = db.updateTag(tag);
        if (updateId == -1) {
            if (BuildConfig.DEBUG) Log.d("Coin", "Update the above tag FAIL!");
        } else {
            if (BuildConfig.DEBUG) Log.d("Coin", "Update the above tag SUCCESSFULLY!" + " - " + updateId);
            for (Tag t : TAGS) {
                if (t.getId() == tag.getId()) {
                    t.set(tag);
                    break;
                }
            }
            sortTAGS();
        }
        return updateId;
    }

    //get records from server to local
    private static long updateNum;
    public static long getRecordsFromServer() {
        updateNum = 0;
        BmobQuery<CoinRecord> query = new BmobQuery<CoinRecord>();
        query.addWhereEqualTo("userId",
                BmobUser.getCurrentUser(CoinApplication.getAppContext(), User.class).getObjectId());
        query.setLimit(Integer.MAX_VALUE);
        query.findObjects(CoinApplication.getAppContext(), new FindListener<CoinRecord>() {
            @Override
            public void onSuccess(List<CoinRecord> object) {
                if (BuildConfig.DEBUG) {
                    if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.getRecordsFromServer get " + object.size() + " records from server");
                }
                updateNum = object.size();
                for (CoinRecord coCoinRecord : object) {
                    boolean exist = false;
                    for (int i = RECORDS.size() - 1; i >= 0; i--) {
                        if (coCoinRecord.getObjectId().equals(RECORDS.get(i).getLocalObjectId())) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        CoinRecord newCoCoinRecord = new CoinRecord();
                        newCoCoinRecord.set(coCoinRecord);
                        newCoCoinRecord.setId(-1);
                        RECORDS.add(newCoCoinRecord);
                    }
                }

                Collections.sort(RECORDS, new Comparator<CoinRecord>() {
                    @Override
                    public int compare(CoinRecord lhs, CoinRecord rhs) {
                        if (lhs.getCalendar().before(rhs.getCalendar())) {
                            return -1;
                        } else if (lhs.getCalendar().after(rhs.getCalendar())) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

                db.deleteAllRecords();

                SUM = 0;
                for (int i = 0; i < RECORDS.size(); i++) {
                    RECORDS.get(i).setLocalObjectId(RECORDS.get(i).getObjectId());
                    RECORDS.get(i).setIsUploaded(true);
                    db.saveRecord(RECORDS.get(i));
                    SUM += (int)RECORDS.get(i).getMoney();
                }

                if (BuildConfig.DEBUG) {
                    if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.getRecordsFromServer save " + RECORDS.size() + " records");
                }
            }
            @Override
            public void onError(int code, String msg) {
                if (BuildConfig.DEBUG) {
                    if (BuildConfig.DEBUG) Log.d("Coin", "recordManager.getRecordsFromServer error " + msg);
                }
            }
        });

        return updateNum;
    }

    public static int getCurrentMonthExpense() {
        Calendar calendar = Calendar.getInstance();
        Calendar left = CoinUtils.GetThisMonthLeftRange(calendar);
        int monthSum = 0;
        for (int i = RECORDS.size() - 1; i >= 0; i--) {
            if (RECORDS.get(i).getCalendar().before(left)) break;
            monthSum += RECORDS.get(i).getMoney();
        }
        return monthSum;
    }

    public static List<CoinRecord> queryRecordByTime(Calendar c1, Calendar c2) {
        List<CoinRecord> list = new LinkedList<>();
        for (CoinRecord coCoinRecord : RECORDS) {
            if (coCoinRecord.isInTime(c1, c2)) {
                list.add(coCoinRecord);
            }
        }
        return list;
    }

    public static List<CoinRecord> queryRecordByCurrency(String currency) {
        List<CoinRecord> list = new LinkedList<>();
        for (CoinRecord coCoinRecord : RECORDS) {
            if (coCoinRecord.getCurrency().equals(currency)) {
                list.add(coCoinRecord);
            }
        }
        return list;
    }

    public static List<CoinRecord> queryRecordByTag(int tag) {
        List<CoinRecord> list = new LinkedList<>();
        for (CoinRecord coinRecord : RECORDS) {
            if (coinRecord.getTag() == tag) {
                list.add(coinRecord);
            }
        }
        return list;
    }

    public static List<CoinRecord> queryRecordByMoney(double money1, double money2, String currency) {
        List<CoinRecord> list = new LinkedList<>();
        for (CoinRecord coinRecord : RECORDS) {
            if (coinRecord.isInMoney(money1, money2, currency)) {
                list.add(coinRecord);
            }
        }
        return list;
    }

    public static List<CoinRecord> queryRecordByRemark(String remark) {
        List<CoinRecord> list = new LinkedList<>();
        for (CoinRecord coinRecord : RECORDS) {
            if (CoinUtils.IsStringRelation(coinRecord.getRemark(), remark)) {
                list.add(coinRecord);
            }
        }
        return list;
    }

    private void createTags() {
        saveTag(new Tag(-1, "Meal",                -1));
        saveTag(new Tag(-1, "Clothing & Footwear", 1));
        saveTag(new Tag(-1, "Home",                2));
        saveTag(new Tag(-1, "Traffic",             3));
        saveTag(new Tag(-1, "Vehicle Maintenance", 4));
        saveTag(new Tag(-1, "Book",                5));
        saveTag(new Tag(-1, "Hobby",               6));
        saveTag(new Tag(-1, "Internet",            7));
        saveTag(new Tag(-1, "Friend",              8));
        saveTag(new Tag(-1, "Education",           9));
        saveTag(new Tag(-1, "Entertainment",      10));
        saveTag(new Tag(-1, "Medical",            11));
        saveTag(new Tag(-1, "Insurance",          12));
        saveTag(new Tag(-1, "Donation",           13));
        saveTag(new Tag(-1, "Sport",              14));
        saveTag(new Tag(-1, "Snack",              15));
        saveTag(new Tag(-1, "Music",              16));
        saveTag(new Tag(-1, "Fund",               17));
        saveTag(new Tag(-1, "Drink",              18));
        saveTag(new Tag(-1, "Fruit",              19));
        saveTag(new Tag(-1, "Film",               20));
        saveTag(new Tag(-1, "Baby",               21));
        saveTag(new Tag(-1, "Partner",            22));
        saveTag(new Tag(-1, "Housing Loan",       23));
        saveTag(new Tag(-1, "Pet",                24));
        saveTag(new Tag(-1, "Telephone Bill",     25));
        saveTag(new Tag(-1, "Travel",             26));
        saveTag(new Tag(-1, "Lunch",              -2));
        saveTag(new Tag(-1, "Breakfast",          -3));
        saveTag(new Tag(-1, "MidnightSnack",      0));
        sortTAGS();
    }

    private void randomDataCreater() {

        Random random = new Random();

        List<CoinRecord> createdCoCoinRecords = new ArrayList<>();

        Calendar now = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.set(2015, 0, 1, 0, 0, 0);
        c.add(Calendar.SECOND, 1);

        while (c.before(now)) {
            for (int i = 0; i < RANDOM_DATA_NUMBER_ON_EACH_DAY; i++) {
                Calendar r = (Calendar)c.clone();
                int hour = random.nextInt(24);
                int minute = random.nextInt(60);
                int second = random.nextInt(60);

                r.set(Calendar.HOUR_OF_DAY, hour);
                r.set(Calendar.MINUTE, minute);
                r.set(Calendar.SECOND, second);
                r.add(Calendar.SECOND, 0);

                int tag = random.nextInt(TAGS.size());
                int expense = random.nextInt(RANDOM_DATA_EXPENSE_ON_EACH_DAY) + 1;

                CoinRecord coCoinRecord = new CoinRecord();
                coCoinRecord.setCalendar(r);
                coCoinRecord.setMoney(expense);
                coCoinRecord.setTag(tag);
                coCoinRecord.setCurrency("RMB");
                coCoinRecord.setRemark("备注：这里显示备注~");

                createdCoCoinRecords.add(coCoinRecord);
            }
            c.add(Calendar.DATE, 1);
        }

        Collections.sort(createdCoCoinRecords, new Comparator<CoinRecord>() {
            @Override
            public int compare(CoinRecord lhs, CoinRecord rhs) {
                if (lhs.getCalendar().before(rhs.getCalendar())) {
                    return -1;
                } else if (lhs.getCalendar().after(rhs.getCalendar())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        for (CoinRecord coCoinRecord : createdCoCoinRecords) {
            saveRecord(coCoinRecord);
        }
    }

    // Todo bug here
    private static void sortTAGS() {
        Collections.sort(TAGS, new Comparator<Tag>() {
            @Override
            public int compare(Tag lhs, Tag rhs) {
                if (lhs.getWeight() != rhs.getWeight()) {
                    return Integer.valueOf(lhs.getWeight()).compareTo(rhs.getWeight());
                } else if (!lhs.getName().equals(rhs.getName())) {
                    return lhs.getName().compareTo(rhs.getName());
                } else {
                    return Integer.valueOf(lhs.getId()).compareTo(rhs.getId());
                }
            }
        });
    }

}
*/