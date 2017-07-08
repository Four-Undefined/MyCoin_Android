/*package com.example.lwxwl.coin.Utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.example.lwxwl.coin.BuildConfig;
import com.example.lwxwl.coin.Data.DB;
import com.example.lwxwl.coin.Data.DBHelper;
import com.example.lwxwl.coin.Model.CoinApplication;
import com.example.lwxwl.coin.Model.CoinRecord;
import com.example.lwxwl.coin.R;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class CoinUtils {

    public static int editRecordPosition = -1;

    //public static String LOGO_PATH = "/sdcard/logo/";
    //public static String LOGO_NAME = "logo.jpg";


    //public static int MY_BLUE;

    public static CoinRecord backupCoCoinRecord;

    public static String PASSWORD = "1234";

    public static int[] WEEKDAY_SHORT_START_ON_MONDAY = {
            0,
            R.string.monday_short,
            R.string.tuesday_short,
            R.string.wednesday_short,
            R.string.thursday_short,
            R.string.friday_short,
            R.string.saturday_short,
            R.string.sunday_short
    };

    public static int[] WEEKDAY_SHORT_START_ON_SUNDAY = {
            0,
            R.string.sunday_short,
            R.string.monday_short,
            R.string.tuesday_short,
            R.string.wednesday_short,
            R.string.thursday_short,
            R.string.friday_short,
            R.string.saturday_short
    };

    public static int[] WEEKDAY_START_ON_MONDAY = {
            0,
            R.string.monday,
            R.string.tuesday,
            R.string.wednesday,
            R.string.thursday,
            R.string.friday,
            R.string.saturday,
            R.string.sunday
    };

    public static int[] WEEKDAY_START_ON_SUNDAY = {
            0,
            R.string.sunday,
            R.string.monday,
            R.string.tuesday,
            R.string.wednesday,
            R.string.thursday,
            R.string.friday,
            R.string.saturday
    };

    public static String[] FLOATINGLABELS = {
            "",
            "",
            "十",
            "百",
            "千",
            "万",
            "十万",
            "百万",
            "千万",
            "亿",
            "十亿"};

    public static String[] BUTTONS = {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "D",
            "0",
            "Y"
    };

    public static int[] TODAY_VIEW_EMPTY_TIP = {
            R.string.today_empty,
            R.string.yesterday_empty,
            R.string.this_week_empty,
            R.string.last_week_empty,
            R.string.this_month_empty,
            R.string.last_month_empty,
            R.string.this_year_empty,
            R.string.last_year_empty
    };

    public static int[] MONTHS_SHORT = {0,
            R.string.january_short,
            R.string.february_short,
            R.string.march_short,
            R.string.april_short,
            R.string.may_short,
            R.string.june_short,
            R.string.july_short,
            R.string.august_short,
            R.string.september_short,
            R.string.october_short,
            R.string.november_short,
            R.string.december_short
    };

    public static int[] MONTHS = {
            0,
            R.string.january,
            R.string.february,
            R.string.march,
            R.string.april,
            R.string.may,
            R.string.june,
            R.string.july,
            R.string.august,
            R.string.september,
            R.string.october,
            R.string.november,
            R.string.december
    };

    public static int TODAY_VIEW_TITLE[] = {
            R.string.today_view_today,
            R.string.today_view_yesterday,
            R.string.today_view_this_week,
            R.string.today_view_last_week,
            R.string.today_view_this_month,
            R.string.today_view_last_month,
            R.string.today_view_this_year,
            R.string.today_view_last_year
    };

    public static int[] TAG_ICON = {
            R.drawable.ic_eat,
            R.drawable.ic_hung,
            R.drawable.ic_cloth,
            R.drawable.ic_play,
            R.drawable.ic_education,
            R.drawable.ic_general,
    };

    public static int[] TAG_COLOR = {
            R.color.ic_eat,
            R.color.ic_hung,
            R.color.ic_cloth,
            R.color.ic_play,
            R.color.ic_education,
            R.color.ic_general,
    };

    public static int[] TAG_NAME = {
            R.string.tag_eat,
            R.string.tag_hung,
            R.string.tag_cloth,
            R.string.tag_play,
            R.string.tag_education,
            R.string.tag_general,

    };

    public static Typeface typefaceLatoRegular = null;
    public static Typeface typefaceLatoHairline = null;
    public static Typeface typefaceLatoLight = null;

    public static void init(Context context) {

        typefaceLatoRegular = Typeface.createFromAsset(
                context.getAssets(), "fonts/Lato-Regular.ttf");
        typefaceLatoHairline = Typeface.createFromAsset(
                context.getAssets(), "fonts/Lato-Hairline.ttf");
        typefaceLatoLight = Typeface.createFromAsset(
                context.getAssets(), "fonts/LatoLatin-Light.ttf");
        relativeSizeSpan = new RelativeSizeSpan(2f);
        redForegroundSpan = new ForegroundColorSpan(Color.parseColor("#ff5252"));
        greenForegroundSpan = new ForegroundColorSpan(Color.parseColor("#4ca550"));
        whiteForegroundSpan = new ForegroundColorSpan(Color.parseColor("#ffffff"));

        lastColor0 = "";
        lastColor1 = "";
        lastColor2 = "";

        random = new Random();

       //MY_BLUE = ContextCompat.getColor(CoinApplication.getAppContext(), R.color.my_blue);
    }

    public static Typeface GetTypeface() {
        if (typefaceLatoLight == null) init(CoinApplication.getAppContext());
        if ("en".equals(Locale.getDefault().getLanguage()))
            return typefaceLatoLight;
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return Typeface.DEFAULT;
        return typefaceLatoLight;
    }

    public static String GetLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String GetWhetherBlank() {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "";
        else
            return " ";
    }

    public static String GetWhetherFuck() {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "日";
        else
            return "";
    }

    public static String GetInMoney(int money) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "¥" + money;
        else
            return "$" + money + " ";
    }

    public static String GetInRecords(int records) {
        return records + "'s";
    }

    public static String GetSpendString(int money) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "消费 ¥" + money;
        else
            return "Spend $" + money + " ";
    }

    public static String GetSpendString(double money) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "消费 ¥" + (int)money;
        else
            return "Spend $" + (int)money + " ";
    }

    public static String GetPercentString(double percent) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return " (占" + String.format("%.2f", percent) + "%)";
        else
            return " (takes " + String.format("%.2f", percent) + "%)";
    }

    public static String GetPurePercentString(double percent) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return " " + String.format("%.2f", percent) + "%";
        else
            return " " + String.format("%.2f", percent) + "%";
    }

    public static String GetTodayViewTitle(int fragmentPosition) {
        return CoinApplication.getAppContext().getString(TODAY_VIEW_TITLE[fragmentPosition]);
    }

    public static boolean WEEK_START_WITH_SUNDAY = false;

    public static String GetAxisDateName(int type, int position) {
        switch (type) {
            case Calendar.HOUR_OF_DAY:
                return position + "";
            case Calendar.DAY_OF_WEEK:
                if (WEEK_START_WITH_SUNDAY) return CoinApplication.getAppContext().getResources()
                        .getString(WEEKDAY_SHORT_START_ON_SUNDAY[position + 1]);
                else return CoinApplication.getAppContext().getResources()
                        .getString(WEEKDAY_SHORT_START_ON_MONDAY[position + 1]);
            case Calendar.DAY_OF_MONTH:
                return (position + 1) + "";
            case Calendar.MONTH:
                return CoinApplication.getAppContext().getResources()
                        .getString(MONTHS_SHORT[position + 1]);
            default:
                return "";
        }
    }

    public static int GetTodayViewEmptyTip(int fragmentPosition) {
        return TODAY_VIEW_EMPTY_TIP[fragmentPosition];
    }

    public static String GetMonthShort(int i) {
        return CoinApplication.getAppContext().getResources().getString(MONTHS_SHORT[i]);
    }

    public static String GetMonth(int i) {
        return CoinApplication.getAppContext().getResources().getString(MONTHS[i]);
    }

    public static String GetWeekDay(int position) {
        if (WEEK_START_WITH_SUNDAY) return CoinApplication.getAppContext().getResources()
                .getString(WEEKDAY_START_ON_SUNDAY[position + 1]);
        else return CoinApplication.getAppContext().getResources()
                .getString(WEEKDAY_START_ON_MONDAY[position + 1]);
    }

    public static Calendar GetTodayLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetTodayRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetYesterdayLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetYesterdayRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (CoinUtils.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek]);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek]);
        }
        return calendar;
    }

    public static Calendar GetThisWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetThisWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetLastWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (CoinUtils.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] - 7);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] - 7);
        }
        return calendar;
    }

    public static Calendar GetLastWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetLastWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetNextWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (CoinUtils.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] + 7);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] + 7);
        }
        return calendar;
    }

    public static Calendar GetNextWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetNextWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetNextWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetNextWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetThisWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetThisWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetLastWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetLastWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetThisMonthLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisMonthRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastMonthLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastMonthRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisYearLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisYearRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastYearLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastYearRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static boolean ClickButtonDelete(int position) {
        return position == 9;
    }

    public static boolean ClickButtonCommit(int position) {
        return position == 11;
    }

    public static boolean ClickButtonIsZero(int position) {
        return position == 10;
    }

    public static double ToDollas(double money, String currency) {

        return 1.0 * money;

    }

    public static boolean IsStringRelation(String s1, String s2) {
        return true;
    }

    public static RelativeSizeSpan relativeSizeSpan;
    public static ForegroundColorSpan redForegroundSpan;
    public static ForegroundColorSpan greenForegroundSpan;
    public static ForegroundColorSpan whiteForegroundSpan;

    private static String lastColor0, lastColor1, lastColor2;

    private static Random random;

    private static String[] Colors = {"#F44336",
            "#E91E63",
            "#9C27B0",
            "#673AB7",
            "#3F51B5",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};

    public static int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        while (Colors[p].equals(lastColor0)
                || Colors[p].equals(lastColor1)
                || Colors[p].equals(lastColor2)) {
            p = random.nextInt(Colors.length);
        }
        lastColor0 = lastColor1;
        lastColor1 = lastColor2;
        lastColor2 = Colors[p];
        return Color.parseColor(Colors[p]);
    }

    public static int GetTagColorResource(int tag) {
        return TAG_COLOR[tag + 2];
    }

    public static int GetTagColor(int tag) {
        return ContextCompat.getColor(CoinApplication.getAppContext(), TAG_COLOR[tag + 3]);
    }

    public static int GetTagIcon(int tagId) {
        return TAG_ICON[tagId + 2];
    }

    public static Drawable GetTagIconDrawable(int tagId) {
        return ContextCompat.getDrawable(
                CoinApplication.getAppContext(), TAG_ICON[tagId + 2]);
    }

    public static String GetTagName(int tagId) {
        return CoinApplication.getAppContext().getResources().getString(TAG_NAME[tagId + 2]);
    }

    public static <K, V extends Comparable<V>> Map<K, V> SortTreeMapByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k1).compareTo(map.get(k2));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    private static final int[] EMPTY_STATE = new int[] {};

    public static void clearState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(EMPTY_STATE);
        }
    }

    public static class MyShakeAnimator extends BaseViewAnimator {
        private int amplitude;

        public MyShakeAnimator() {
            amplitude = 25;
        }

        public MyShakeAnimator(int amplitude) {
            this.amplitude = amplitude;
        }

        @Override
        public void prepare(View target) {
            int amplitude1 = (int)(amplitude * 0.4);
            int amplitude2 = (int)(amplitude * 0.2);
            getAnimatorAgent().playTogether(
                    ObjectAnimator.ofFloat(target, "translationX", 0, amplitude, -amplitude,
                            amplitude, -amplitude, amplitude, -amplitude, amplitude, -amplitude,
                            amplitude, -amplitude, amplitude1, -amplitude1, amplitude2, -amplitude2,
                            0)
            );
        }
    }

    public static boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = CoinApplication.getAppContext()
                .getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = CoinApplication.getAppContext()
                    .getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getDeeperColor(int color) {
        int alpha = Color.alpha(color);
        int red = (int)(Color.red(color) * 0.8);
        int green = (int)(Color.green(color) * 0.8);
        int blue = (int)(Color.blue(color) * 0.8);
        return Color.argb(alpha, red, green, blue);
    }

    public static int getAlphaColor(int color) {
        int alpha = 6;
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static void getKeyBoard(MaterialEditText editText, Context context) {
        editText.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void getKeyBoard(EditText editText) {
        editText.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                CoinApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = CoinApplication.getAppContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int getToolBarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return mActionBarSize;
    }



    public static void showToast(Context context, String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(context);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setText(text);
        superToast.show();
    }

    private static String lastToast = "";
    public static void showToast(Context context, String text) {
        if (context == null) return;
        if (lastToast.equals(text)) {
            SuperToast.cancelAllSuperToasts();
        } else {
            lastToast = text;
        }
        SuperToast superToast = new SuperToast(context);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setText(text);
        superToast.show();
    }

    public static void showToast(Context context, int textId) {
        String text = context.getResources().getString(textId);
        if (context == null) return;
        if (lastToast.equals(text)) {
            SuperToast.cancelAllSuperToasts();
        } else {
            lastToast = text;
        }
        SuperToast superToast = new SuperToast(context);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setText(text);
        superToast.show();
    }

    public static boolean isPointInsideView(float x, float y, View view){
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if(( x > viewX && x < (viewX + view.getWidth())) &&
                ( y > viewY && y < (viewY + view.getHeight()))){
            return true;
        } else {
            return false;
        }
    }

    public static int GetScreenWidth(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int GetScreenHeight(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static Point GetScreenSize(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Double INPUT_MIN_EXPENSE = 0d;
    public static Double INPUT_MAX_EXPENSE = 99999d;

    public static String GetCurrentVersion() {
        return "Coin V" + CoinApplication.VERSION / 100 + "." + CoinApplication.VERSION % 100 / 10 + "." + CoinApplication.VERSION % 10;
    }

    public static String GetString(Context context, int i) {
        return context.getResources().getString(i);
    }

    public static String GetCalendarString(Context context, Calendar calendar) {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        return (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
    }

    public static String GetCalendarStringRecordCheckDialog(Context context, Calendar calendar) {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + calendar.get(Calendar.DAY_OF_MONTH) + GetWhetherFuck() + " " + calendar.get(Calendar.YEAR);
        }
        return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
    }

    public static String GetCalendarStringDayExpenseSort(Context context, int year, int month, int day) {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[month]) + " " + day + " " + year;
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[month]) + day + GetWhetherFuck() + " " + year;
        }
        return context.getResources().getString(MONTHS_SHORT[month]) + " " + day + " " + year;
    }

    public static String GetCalendarString(Context context, String string) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(sdf.parse(string));
        } catch (ParseException p) {

        }
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        return (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
    }

    public static String GetRecordDatabasePath(Context context) {
        String databasePath = "";
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            databasePath = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            databasePath = "/data/data/" + context.getPackageName() + "/databases/";
        }
        databasePath += DB.DB_NAME_STRING;
        if (BuildConfig.DEBUG) Log.d("CoCoin", "Get record database path " + databasePath);
        return databasePath;
    }

    // if the uploaded file's size and name is the same, the BmobProFile.upload will not upload in fact
    public static void deleteBmobUploadCach(Context context) {
        DBHelper dbHelper = new DBHelper(context, "bmob", null, 1);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        sqliteDatabase.delete("upload", "_id>?", new String[]{"0"});
//        String databasePath = "";
//        if (android.os.Build.VERSION.SDK_INT >= 17) {
//            databasePath = context.getApplicationInfo().dataDir + "/databases/";
//        } else {
//            databasePath = "/data/data/" + context.getPackageName() + "/databases/";
//        }
//        databasePath += "bmob";
//        File file = new File(databasePath);
//        if (file.exists()) file.delete();
    }

    // the tagId is clothes, food, house and traffic
    public static int IsCFHT(int tagId) {
        if (tagId == 2) {
            return 0;
        } else if (tagId == -3 || tagId == -2 || tagId == -1 || tagId == 0 || tagId == 15 || tagId == 19 || tagId == 20) {
            return 1;
        } else if (tagId == 3 || tagId == 24) {
            return 2;
        } else if (tagId == 4 || tagId == 5) {
            return 3;
        }
        return -1;
    }

    public static int textCounter(String s) {
        int counter = 0;
        for (char c : s.toCharArray()) {
            if (c < 128) {
                counter++;
            } else {
                counter += 2;
            }
        }
        return counter;
    }

    public static void copyToClipboard(String content, Context context)
    {
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }



    private static CoinUtils ourInstance = new CoinUtils();

    public static CoinUtils getInstance() {
        if (ourInstance == null || typefaceLatoLight == null || typefaceLatoHairline == null) {
            ourInstance = new CoinUtils();
            init(CoinApplication.getAppContext());
        }
        return ourInstance;
    }

    private CoinUtils() {
    }
}
*/