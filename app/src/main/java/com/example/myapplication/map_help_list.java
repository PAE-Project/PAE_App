package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class map_help_list extends AppCompatActivity {
    ListView listView;
    Button go_register;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_help_list);

        listView = findViewById(R.id.listView);


        ArrayList<ListData> listViewData = new ArrayList<>();
        for (int i=0; i<3; ++i)
        {
            ListData listData = new ListData();
            listData.num = String.valueOf(i+1);
            listData.title = "테스트 이름입니다 "+(i+1);

            listData.body_1 = "주소 /";
            listData.body_2 = "010-1111-1111";

            listViewData.add(listData);
        }


        ListAdapter oAdapter = new CustomListView(listViewData);
        listView.setAdapter(oAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(map_help_list.this, main_help_info.class);
                startActivity(intent);
            }
        });

    }
}