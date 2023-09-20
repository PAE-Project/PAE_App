package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class main_7001_1 extends AppCompatActivity {
    ListView listView;
    Button go_register;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        go_register = (Button) findViewById(R.id.go_register);

        listView = findViewById(R.id.listView);


        ArrayList<ListData> listViewData = new ArrayList<>();
        for (int i=0; i<5; ++i)
        {
            ListData listData = new ListData();
            listData.num = String.valueOf(i+1);
            listData.title = "테스트 제목입니다 "+(i+1);

            listData.name = "이름 /";
            listData.date = "0000-00-00";

            listViewData.add(listData);
        }


        ListAdapter oAdapter = new CustomListView(listViewData);
        listView.setAdapter(oAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(main_7001_1.this, DetailActivity.class);
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