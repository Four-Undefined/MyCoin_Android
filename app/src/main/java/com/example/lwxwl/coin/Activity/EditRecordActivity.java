/*
package com.example.lwxwl.coin.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.example.lwxwl.coin.Adapter.EditMoneyRemarkFragmentAdapter;
import com.example.lwxwl.coin.Adapter.TagChooseFragmentAdapter;
import com.example.lwxwl.coin.Fragment.CoinFragmentManager;
import com.example.lwxwl.coin.Fragment.WriteFragment;
import com.example.lwxwl.coin.Model.CoinRecord;
import com.example.lwxwl.coin.Model.RecordManager;
import com.example.lwxwl.coin.UI.MyGridView;
import com.example.lwxwl.coin.Utils.CoinUtils;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.SuperToast;

public class EditRecordActivity extends AppCompatActivity
        implements WriteFragment.OnTagItemSelectedListener {

    private Context mContext;
    private boolean IS_CHANGED = false;
    private boolean FIRST_EDIT = true;
    private int position = -1;

    private ViewPager tagViewPager;
    private TagChooseFragmentAdapter tagAdapter;

    private CoCoinScrollableViewPager editViewPager;
    private EditMoneyRemarkFragmentAdapter editAdapter;

    private MyGridView myGridView;
    private ButtonGridViewAdapter myGridViewAdapter;

    private final int NO_TAG_TOAST = 0;
    private final int NO_MONEY_TOAST = 1;
    private final int SAVE_SUCCESSFULLY_TOAST = 4;
    private final int SAVE_FAILED_TOAST = 5;

    private SuperToast superToast;

    private MaterialIconView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mContext = this;

        superToast = new SuperToast(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("POSITION");
            CoinUtils.editRecordPosition = RecordManager.SELECTED_RECORDS.size() - 1 - position;
        } else {
            CoinUtils.editRecordPosition = -1;
        }


        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(mContext, R.color.statusBarColor));
        } else{
            // do something for phones running an SDK before lollipop
        }

// edit viewpager///////////////////////////////////////////////////////////////////////////////////
        editViewPager = (CoCoinScrollableViewPager)findViewById(R.id.edit_pager);
        editViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        editAdapter = new EditMoneyRemarkFragmentAdapter(
                getSupportFragmentManager(), CoinFragmentManager.EDIT_RECORD_ACTIVITY_FRAGMENT);

        editViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {
                    CoinFragmentManager.editRecordActivityEditRemarkFragment.editRequestFocus();
                } else {
                    CoinFragmentManager.editRecordActivityEditMoneyFragment.editRequestFocus();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        editViewPager.setAdapter(editAdapter);

// tag viewpager
        tagViewPager = (ViewPager)findViewById(R.id.viewpager);
        tagViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        if (RecordManager.TAGS.size() % 8 == 0)
            tagAdapter = new TagChooseFragmentAdapter(getSupportFragmentManager(), RecordManager.TAGS.size() / 8);
        else
            tagAdapter = new TagChooseFragmentAdapter(getSupportFragmentManager(), RecordManager.TAGS.size() / 8 + 1);

        tagViewPager.setAdapter(tagAdapter);

        myGridView = (MyGridView)findViewById(R.id.gridview);
        myGridViewAdapter = new ButtonGridViewAdapter(this);
        myGridView.setAdapter(myGridViewAdapter);

        myGridView.setOnItemClickListener(gridViewClickListener);
        myGridView.setOnItemLongClickListener(gridViewLongClickListener);

        myGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        myGridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        View lastChild = myGridView.getChildAt(myGridView.getChildCount() - 1);
                        myGridView.setLayoutParams(
                                new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT, lastChild.getBottom()));
                    }
                });

        back = (MaterialIconView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("IS_CHANGED", IS_CHANGED);
        intent.putExtra("POSITION", position);
        setResult(RESULT_OK, intent);

        CoinUtils.editRecordPosition = -1;

        super.finish();
    }

    private AdapterView.OnItemClickListener gridViewClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            buttonClickOperation(false, position);
        }
    };

    private AdapterView.OnItemLongClickListener gridViewLongClickListener
            = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            buttonClickOperation(true, position);
            return true;
        }
    };

    private void buttonClickOperation(boolean longClick, int position) {
        if (IS_CHANGED) {
            return;
        }
        if (CoinFragmentManager.editRecordActivityEditMoneyFragment.getNumberText().toString().equals("0")
                && !CoinUtils.ClickButtonCommit(position)) {
            if (CoinUtils.ClickButtonDelete(position)
                    || CoinUtils.ClickButtonIsZero(position)) {

            } else {
                CoinFragmentManager.editRecordActivityEditMoneyFragment.setNumberText(CoinUtils.BUTTONS[position]);
            }
        } else {
            if (CoinUtils.ClickButtonDelete(position)) {
                if (longClick) {
                    CoinFragmentManager.editRecordActivityEditMoneyFragment.setNumberText("0");
                    CoinFragmentManager.editRecordActivityEditMoneyFragment.setHelpText(" ");
                    CoinFragmentManager.editRecordActivityEditMoneyFragment.setHelpText(
                            CoinUtils.FLOATINGLABELS[CoinFragmentManager.editRecordActivityEditMoneyFragment
                                    .getNumberText().toString().length()]);
                } else {
                    CoinFragmentManager.editRecordActivityEditMoneyFragment.setNumberText(
                            CoinFragmentManager.editRecordActivityEditMoneyFragment.getNumberText().toString()
                                    .substring(0, CoinFragmentManager.editRecordActivityEditMoneyFragment
                                            .getNumberText().toString().length() - 1));
                    if (CoinFragmentManager.editRecordActivityEditMoneyFragment.getNumberText().toString().length() == 0) {
                        CoinFragmentManager.editRecordActivityEditMoneyFragment.setNumberText("0");
                        CoinFragmentManager.editRecordActivityEditMoneyFragment.setHelpText(" ");
                    }
                }
            } else if (CoinUtils.ClickButtonCommit(position)) {
                commit();
            } else {
                if (FIRST_EDIT) {
                    CoinFragmentManager.editRecordActivityEditMoneyFragment.setNumberText(CoinUtils.BUTTONS[position]);
                    FIRST_EDIT = false;
                } else {
                    CoinFragmentManager.editRecordActivityEditMoneyFragment
                            .setNumberText(CoinFragmentManager.editRecordActivityEditMoneyFragment
                                    .getNumberText().toString() + CoinUtils.BUTTONS[position]);
                }
            }
        }
        CoinFragmentManager.editRecordActivityEditMoneyFragment.setHelpText(CoinUtils.FLOATINGLABELS[
                CoinFragmentManager.editRecordActivityEditMoneyFragment.getNumberText().toString().length()]);
    }

    private void commit() {
        if (CoinFragmentManager.editRecordActivityEditMoneyFragment.getTagId() == -1) {
            showToast(NO_TAG_TOAST);
        } else if (CoinFragmentManager.editRecordActivityEditMoneyFragment.getNumberText().toString().equals("0")) {
            showToast(NO_MONEY_TOAST);
        } else  {
            CoinRecord coinRecord = new CoinRecord();
            coinRecord.set(RecordManager.SELECTED_RECORDS.get(RecordManager.getInstance(mContext).SELECTED_RECORDS.size() - 1 - position));
            coinRecord.setMoney(Float.valueOf(CoinFragmentManager.editRecordActivityEditMoneyFragment.getNumberText().toString()));
            coinRecord.setTag(CoinFragmentManager.editRecordActivityEditMoneyFragment.getTagId());
            coinRecord.setRemark(CoinFragmentManager.editRecordActivityEditRemarkFragment.getRemark());
            long updateId = RecordManager.updateRecord(coinRecord);
            if (updateId == -1) {
                if (!superToast.isShowing()) {
                    showToast(SAVE_FAILED_TOAST);
                }
            } else {
                IS_CHANGED = true;
                RecordManager.SELECTED_RECORDS.set(RecordManager.getInstance(mContext).SELECTED_RECORDS.size() - 1 - position, coCoinRecord);
                for (int i = RecordManager.getInstance(mContext).RECORDS.size() - 1; i >= 0; i--) {
                    if (coinRecord.getId() == RecordManager.RECORDS.get(i).getId()) {
                        RecordManager.RECORDS.set(i, coinRecord);
                        break;
                    }
                }
                onBackPressed();
            }
        }
    }

    private void showToast(int toastType) {
        SuperToast.cancelAllSuperToasts();
        SuperActivityToast.cancelAllSuperActivityToasts();

        superToast.setAnimations(CoinUtils.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);

        switch (toastType) {
            case NO_MONEY_TOAST:

                superToast.setText(mContext.getResources().getString(R.string.toast_no_money));
                superToast.setBackground(SuperToast.Background.BLUE);
                superToast.getTextView().setTypeface(CoCoinUtil.typefaceLatoLight);

                break;
            case SAVE_SUCCESSFULLY_TOAST:

                superToast.setText(
                        mContext.getResources().getString(R.string.toast_save_successfully));
                superToast.setBackground(SuperToast.Background.GREEN);
                superToast.getTextView().setTypeface(CoCoinUtil.typefaceLatoLight);

                break;
            case SAVE_FAILED_TOAST:

                superToast.setText(mContext.getResources().getString(R.string.toast_save_failed));
                superToast.setBackground(SuperToast.Background.RED);
                superToast.getTextView().setTypeface(CoinUtils.typefaceLatoLight);

                break;
            default:

                break;
        }

        superToast.show();
    }

    @Override
    public void onTagItemPicked(int position) {
        CoinFragmentManager.editRecordActivityEditMoneyFragment.setTag(tagViewPager.getCurrentItem() * 6 + position + 2);
    }

    @Override
    public void onAnimationStart(int id) {

    }

    private float x1, x2, y1, y2;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = ev.getX();
                y1 = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                x2 = ev.getX();
                y2 = ev.getY();
                if (editViewPager.getCurrentItem() == 0
                        && CoinUtils.isPointInsideView(x2, y2, editViewPager)
                        && CoinUtils.GetScreenWidth(mContext) - x2 <= 60) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onDestroy() {
        for (int i = 0; i < CoinFragmentManager.tagChooseFragments.size(); i++) {
            if (CoinFragmentManager.tagChooseFragments.get(i) != null) {
                CoinFragmentManager.tagChooseFragments.get(i).onDestroy();
                CoinFragmentManager.tagChooseFragments.set(i, null);
            }
        }
        if (CoinFragmentManager.editRecordActivityEditMoneyFragment != null) {
            CoinFragmentManager.editRecordActivityEditMoneyFragment.onDestroy();
            CoinFragmentManager.editRecordActivityEditMoneyFragment = null;
        }
        if (CoinFragmentManager.editRecordActivityEditRemarkFragment != null) {
            CoinFragmentManager.editRecordActivityEditRemarkFragment.onDestroy();
            CoinFragmentManager.editRecordActivityEditRemarkFragment = null;
        }
        super.onDestroy();
    }
}
*/