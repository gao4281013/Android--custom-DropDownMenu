package com.baiiu.filter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiiu.filter.R;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;

import java.util.List;

/**
 * Created by baiiu on 15/12/23.
 * 菜单条目适配器
 */
public abstract class SimpleLeftTextAdapter<T> extends BaseBaseAdapter<T> {

    private final LayoutInflater inflater;

    public SimpleLeftTextAdapter(List<T> list, Context context) {
        super(list, context);
        inflater = LayoutInflater.from(context);
    }

    public static class FilterItemHolder {
        FilterCheckedTextView checkedTextView;
        FilterCheckedTextView rightCheckedTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FilterItemHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_left_item_filter, parent, false);

            holder = new FilterItemHolder();
            holder.checkedTextView = (FilterCheckedTextView) convertView.findViewById(R.id.tv_left_item_filter);
            holder.checkedTextView.setPadding(0, UIUtil.dp(context, 15), 0, UIUtil.dp(context, 15));
            initCheckedTextView(holder.checkedTextView);

            holder.rightCheckedTextView = (FilterCheckedTextView)convertView.findViewById(R.id.tv_right_item);
            holder.rightCheckedTextView.setPadding(0, UIUtil.dp(context, 15), 0, UIUtil.dp(context, 15));
            initRightCheckedTextView(holder.checkedTextView);

            convertView.setTag(holder);
        } else {
            holder = (FilterItemHolder) convertView.getTag();
        }

        T t = list.get(position);
        holder.checkedTextView.setText(provideText(t));
        holder.rightCheckedTextView.setText(provideRightText(t));

        return convertView;
    }

    public abstract String provideText(T t);

    public abstract String provideRightText(T t);

    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
    }

    protected void initRightCheckedTextView(FilterCheckedTextView checkedTextView) {
    }


}
