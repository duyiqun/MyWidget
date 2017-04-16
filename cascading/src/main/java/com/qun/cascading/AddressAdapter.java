package com.qun.cascading;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Qun on 2017/4/16.
 */

public class AddressAdapter extends BaseAdapter {

    private List<Address> mAddressList;

    public AddressAdapter(List<Address> addressList) {
        this.mAddressList = addressList;
    }

    @Override
    public int getCount() {
        return mAddressList == null ? 0 : mAddressList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Address address = mAddressList.get(position);
        viewHolder.tvAddress.setText(address.name);
        //需要根据address的isSelect属性判断当前convertView是否显示选中状态
        if (address.isSelected) {
            viewHolder.tvAddress.setBackgroundResource(R.mipmap.choose_item_selected);
        } else {
            viewHolder.tvAddress.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvAddress;
    }
}
