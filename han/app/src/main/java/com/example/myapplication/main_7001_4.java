package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class main_7001_4 extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list3);

        listView = findViewById(R.id.listView);

        ArrayList<ListData> listViewData = new ArrayList<>();
        for (int i=0; i<10; ++i)
        {
            ListData listData = new ListData();
            listData.num = String.valueOf(i+1);
            listData.title = "테스트"+i;

            listData.name = "이름 /";
            listData.date = "3333-33-33";

            listViewData.add(listData);
        }


        ListAdapter oAdapter = new CustomListView(listViewData);
        listView.setAdapter(oAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickName = listViewData.get(position).title;
                Log.d("확인","name : "+clickName);
            }
        });

    }
}