/*package com.example.lwxwl.coin.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lwxwl.coin.BuildConfig;
import com.example.lwxwl.coin.Model.CoinRecord;
import com.example.lwxwl.coin.Model.RecordManager;
import com.example.lwxwl.coin.Model.Tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;


public class DB {
    public static final String DB_NAME_STRING = "Coin Database.db";
    public static final String RECORD_DB_NAME_STRING = "Record";
    public static final String TAG_DB_NAME_STRING = "Tag";

    public static final int VERSION = 1;

    private static DB db;
    private SQLiteDatabase sqliteDatabase;
    private DBHelper dbHelper;

    private DB(Context context) throws IOException {
        dbHelper = new DBHelper(context, DB_NAME_STRING, null, VERSION);
        sqliteDatabase = dbHelper.getWritableDatabase();
    }

    public synchronized static DB getInstance(Context context)
            throws IOException {
        if (db == null) db = new DB(context);
        return db;
    }

    public void getData() {
        RecordManager.RECORDS = new LinkedList<>();
        RecordManager.TAGS = new LinkedList<>();

        Cursor cursor = sqliteDatabase
                .query(TAG_DB_NAME_STRING, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setId(cursor.getInt(cursor.getColumnIndex("ID")) - 1);
                tag.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                tag.setWeight(cursor.getInt(cursor.getColumnIndex("WEIGHT")));
                RecordManager.TAGS.add(tag);
            } while (cursor.moveToNext());
            if (cursor != null) cursor.close();
        }

        cursor = sqliteDatabase
                .query(RECORD_DB_NAME_STRING, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                CoinRecord coinRecord = new CoinRecord();
                coinRecord.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                coinRecord.setMoney(cursor.getFloat(cursor.getColumnIndex("MONEY")));
                coinRecord.setCurrency(cursor.getString(cursor.getColumnIndex("CURRENCY")));
                coinRecord.setTag(cursor.getInt(cursor.getColumnIndex("TAG")));
                coinRecord.setCalendar(cursor.getString(cursor.getColumnIndex("TIME")));
                coinRecord.setRemark(cursor.getString(cursor.getColumnIndex("REMARK")));
                coinRecord.setUserId(cursor.getString(cursor.getColumnIndex("USER_ID")));
                coinRecord.setLocalObjectId(cursor.getString(cursor.getColumnIndex("OBJECT_ID")));
                coinRecord.setIsUploaded(
                        cursor.getInt(cursor.getColumnIndex("IS_UPLOADED")) == 0 ? false : true);

                if (BuildConfig.DEBUG) Log.d("Coin Debugger", "Load " + coinRecord.toString() + " S");

                RecordManager.RECORDS.add(coinRecord);
                RecordManager.SUM += (int) coinRecord.getMoney();
            } while (cursor.moveToNext());
            if (cursor != null) cursor.close();
        }
    }

    // return the row ID of the newly inserted row, or -1 if an error occurred
    public long saveRecord(CoinRecord coCoinRecord) {
        ContentValues values = new ContentValues();
        values.put("MONEY", coCoinRecord.getMoney());
        values.put("CURRENCY", coCoinRecord.getCurrency());
        values.put("TAG", coCoinRecord.getTag());
        values.put("TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(coCoinRecord.getCalendar().getTime()));
        values.put("REMARK", coCoinRecord.getRemark());
        values.put("USER_ID", coCoinRecord.getUserId());
        values.put("OBJECT_ID", coCoinRecord.getLocalObjectId());
        values.put("IS_UPLOADED", coCoinRecord.getIsUploaded().equals(Boolean.FALSE) ? 0 : 1);
        long insertId = sqliteDatabase.insert(RECORD_DB_NAME_STRING, null, values);
        coCoinRecord.setId(insertId);
        if (BuildConfig.DEBUG)
            Log.d("Coin Debugger", "db.saveRecord " + coCoinRecord.toString() + " S");
        return insertId;
    }

    // return the row ID of the newly inserted row, or -1 if an error occurred
    public int saveTag(Tag tag) {
        ContentValues values = new ContentValues();
        values.put("NAME", tag.getName());
        values.put("WEIGHT", tag.getWeight());
        int insertId = (int)sqliteDatabase.insert(TAG_DB_NAME_STRING, null, values);
        tag.setId(insertId);
        if (BuildConfig.DEBUG) Log.d("Coin Debugger", "db.saveTag " + tag.toString() + " S");
        return insertId - 1;
    }

    // return the id of the record deleted
    public long deleteRecord(long id) {
        long deletedNumber = sqliteDatabase.delete(RECORD_DB_NAME_STRING,
                "ID = ?",
                new String[]{id + ""});
        if (BuildConfig.DEBUG)
            Log.d("Coin Debugger", "db.deleteRecord id = " + id + " S");
        if (BuildConfig.DEBUG)
            Log.d("Coin Debugger", "db.deleteRecord number = " + deletedNumber + " S");
        return id;
    }

    // return the id of the tag deleted
    public int deleteTag(int id) {
        int deletedNumber = sqliteDatabase.delete(TAG_DB_NAME_STRING,
                "ID = ?",
                new String[]{(id + 1) + ""});
        if (BuildConfig.DEBUG)
            Log.d("Coin Debugger", "db.deleteTag id = " + id + " S");
        if (BuildConfig.DEBUG)
            Log.d("Coin Debugger", "db.deleteTag number = " + deletedNumber + " S");
        return id;
    }

    // return the id of the coinRecord update
    public long updateRecord(CoinRecord coinRecord) {
        ContentValues values = new ContentValues();
        values.put("ID", coinRecord.getId());
        values.put("MONEY", coinRecord.getMoney());
        values.put("CURRENCY", coinRecord.getCurrency());
        values.put("TAG", coinRecord.getTag());
        values.put("TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(coinRecord.getCalendar().getTime()));
        values.put("REMARK", coinRecord.getRemark());
        values.put("USER_ID", coinRecord.getUserId());
        values.put("OBJECT_ID", coinRecord.getLocalObjectId());
        values.put("IS_UPLOADED", coinRecord.getIsUploaded().equals(Boolean.FALSE) ? 0 : 1);
        sqliteDatabase.update(RECORD_DB_NAME_STRING, values,
                "ID = ?",
                new String[]{coinRecord.getId() + ""});
        if (BuildConfig.DEBUG)
            Log.d("Coin Debugger", "db.updateRecord " + coinRecord.toString() + " S");
        return coinRecord.getId();
    }

    // return the id of the tag update
    public int updateTag(Tag tag) {
        ContentValues values = new ContentValues();
        values.put("NAME", tag.getName());
        values.put("WEIGHT", tag.getWeight());
        sqliteDatabase.update(TAG_DB_NAME_STRING, values,
                "ID = ?",
                new String[]{(tag.getId() + 1) + ""});
        if (BuildConfig.DEBUG)
            Log.d("Coin Debugger", "db.updateTag " + tag.toString() + " S");
        return tag.getId();
    }

    // delete all the records
    public int deleteAllRecords() {
        int deleteNum = sqliteDatabase.delete(RECORD_DB_NAME_STRING, null, null);
        Log.d("Coin Debugger", "db.deleteAllRecords " + deleteNum + " S");
        return deleteNum;
    }
}
*/