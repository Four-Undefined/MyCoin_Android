/*

package com.example.lwxwl.coin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lwxwl.coin.Model.RecordManager;
import com.example.lwxwl.coin.R;
import com.example.lwxwl.coin.Utils.CoinUtils;

import java.nio.ByteBuffer;

public class TagChooseGridViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private int fragmentPosition;
    private int count = 0;

    public TagChooseGridViewAdapter(Context context, int fragmentPosition) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.fragmentPosition = fragmentPosition;
    }

    @Override
    public int getCount() {
        if ((fragmentPosition + 1) * 6 >= (RecordManager.TAGS.size() - 2)) {
            return (RecordManager.TAGS.size() - 2) % 6;
        } else {
            return 6;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.item_grid, null);
            holder.tagName = (TextView)convertView.findViewById(R.id.tag_name);
            holder.tagImage = (ImageView)convertView.findViewById(R.id.tag_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        byte[] arr = RecordManager.TAGS.get(fragmentPosition * 6 + position + 2).getId();
        ByteBuffer wrapped = ByteBuffer.wrap(arr);
        short num = wrapped.getShort();

        ByteBuffer dbuf = ByteBuffer.allocate(2);
        dbuf.putShort(num);
        byte[] bytes = dbuf.array();

        holder.tagName.setText(CoinUtils.GetTagName(num));
        holder.tagName.setTypeface(CoinUtils.typefaceLatoLight);
        holder.tagImage.setImageResource(CoinUtils.GetTagIcon(num));

        return convertView;
    }

    private class ViewHolder {
        TextView tagName;
        ImageView tagImage;
    }

}

*/