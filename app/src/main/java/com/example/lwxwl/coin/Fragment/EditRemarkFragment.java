/*package com.example.lwxwl.coin.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.lwxwl.coin.Model.CoinApplication;
import com.example.lwxwl.coin.Model.RecordManager;
import com.example.lwxwl.coin.Model.SettingManager;
import com.example.lwxwl.coin.Utils.CoinUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

public class EditRemarkFragment extends Fragment {

    private int fragmentPosition;
    private int tagId = -1;

    public MaterialEditText editView;

    private View mView;

    Activity activity;

    static public EditRemarkFragment newInstance(int position, int type) {
        EditRemarkFragment fragment = new EditRemarkFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("type", type);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_edit_remark, container, false);
        editView = (MaterialEditText)mView.findViewById(R.id.remark);

        if (getArguments().getInt("type") == CoinFragmentManager.MAIN_ACTIVITY_FRAGMENT) {
            CoinFragmentManager.mainActivityEditRemarkFragment = this;
        } else if (getArguments().getInt("type") == CoinFragmentManager.EDIT_RECORD_ACTIVITY_FRAGMENT) {
            CoinFragmentManager.editRecordActivityEditRemarkFragment = this;
        }

        boolean shouldChange
                = SettingManager.getInstance().getIsMonthLimit()
                && SettingManager.getInstance().getIsColorRemind()
                && RecordManager.getCurrentMonthExpense()
                >= SettingManager.getInstance().getMonthWarning();

        setEditColor(shouldChange);

        if (getArguments().getInt("type") == CoinFragmentManager.EDIT_RECORD_ACTIVITY_FRAGMENT
                && CoinUtils.editRecordPosition != -1) {
            CoinFragmentManager.editRecordActivityEditRemarkFragment
                    .setRemark(RecordManager.SELECTED_RECORDS.get(CoinUtils.editRecordPosition).getRemark());
            CoinFragmentManager.editRecordActivityEditRemarkFragment.setLastSelection();
        }

        return mView;
    }

    public interface OnTagItemSelectedListener {
        void onTagItemPicked(int position);
    }

    public void updateTags() {

    }

    public int getTagId() {
        return tagId;
    }

    public void setTag(int p) {
        tagId = RecordManager.TAGS.get(p).getId();
    }

    public String getNumberText() {
        return editView.getText().toString();
    }

    public void setNumberText(String string) {
        editView.setText(string);
    }

    public String getHelpText() {
        return editView.getHelperText();
    }

    public void setHelpText(String string) {
        editView.setHelperText(string);
    }

    public void setLastSelection() {
        editView.setSelection(editView.getText().length());
    }

    public void editRequestFocus() {
        editView.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                CoinApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(editView, InputMethodManager.SHOW_IMPLICIT);
    }

    public void setEditColor(boolean shouldChange) {
        if (shouldChange) {
            editView.setTextColor(SettingManager.getInstance().getRemindColor());
            editView.setPrimaryColor(SettingManager.getInstance().getRemindColor());
            editView.setHelperTextColor(SettingManager.getInstance().getRemindColor());
        } else {
            editView.setTextColor(getResources().getColor(android.R.color.black));
            editView.setPrimaryColor(getResources().getColor(android.R.color.black));
            editView.setHelperTextColor(getResources().getColor(android.R.color.black));
        }
    }

    public String getRemark() {
        return editView.getText().toString();
    }

    public void setRemark(String string) {
        editView.setText(string);
    }

}
*/