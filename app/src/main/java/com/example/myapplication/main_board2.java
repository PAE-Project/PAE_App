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

public class main_board2 extends AppCompatActivity {
    ListView listView;
    Button go_register;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board2);

        go_register = (Button) findViewById(R.id.go_register);

        listView = findViewById(R.id.listView);

        ArrayList<ListData> listViewData = new ArrayList<>();
        for (int i=0; i<10; ++i)
        {
            ListData listData = new ListData();
            listData.num = String.valueOf(i+1);
            listData.title = "테스트"+i;

            listData.body_1 = "이름 /";
            listData.body_2 = "1111-11-11";

            listViewData.add(listData);
        }


        ListAdapter oAdapter = new CustomListView(listViewData);
        listView.setAdapter(oAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(main_board2.this, activity_board_detail.class);
                startActivity(intent);
            }
        });

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_register:
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                        break;

                }

            }
        };
        go_register.setOnClickListener(cl);

    }
}