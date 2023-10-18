package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListView1 extends BaseAdapter {
    LayoutInflater layoutInflater = null;
    private ArrayList<ListData> listViewData = null;
    private int count = 0;

    public CustomListView1(ArrayList<ListData> listData) {
        listViewData = listData;
        count = listViewData.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final Context context = parent.getContext();
            if (layoutInflater == null) {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = layoutInflater.inflate(R.layout.custom_listview1, parent, false);
        }
        ImageView mainImage = convertView.findViewById(R.id.mainImage);

        TextView num = convertView.findViewById(R.id.num);
        TextView title = convertView.findViewById(R.id.title);
        TextView body_1 = convertView.findViewById(R.id.body_1);
        TextView body_2 = convertView.findViewById(R.id.body_2);
        TextView body_3 = convertView.findViewById(R.id.body_3);

        mainImage.setImageResource(listViewData.get(position).mainImage);

        num.setText(listViewData.get(position).num);
        title.setText(listViewData.get(position).title);
        body_1.setText(listViewData.get(position).body_1);
        body_2.setText(listViewData.get(position).body_2);
        body_3.setText(listViewData.get(position).body_3);

        return convertView;
    }
}