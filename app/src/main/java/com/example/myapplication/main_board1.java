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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class main_board1 extends AppCompatActivity {
    ListView listView;
    Button go_register;
    View.OnClickListener cl;
    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> contentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board1);

        go_register = (Button) findViewById(R.id.go_register);

        listView = findViewById(R.id.listView);

        connect con = new connect();
        con.start();

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go_register:
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                        break;

                }

            }
        };
        go_register.setOnClickListener(cl);

    }

    class connect extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://3.35.45.245:8080/api/boards");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET"); //전송방식
                connection.setDoOutput(false);       //데이터를 쓸 지 설정
                connection.setDoInput(true);        //데이터를 읽어올지 설정

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                // 출력물의 라인과 그 합에 대한 변수.
                StringBuilder result = new StringBuilder();
                String line;


                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }


                final String data = result.toString();
                JSONArray jsonArray = new JSONArray(data);
                final ArrayList<ListData> listViewData = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject.getString("category").equals("육아 정보")){
                        titleList.add(jsonObject.getString("title"));
                        categoryList.add(jsonObject.getString("category"));
                        contentList.add(jsonObject.getString("content"));
                    }
                }
                System.out.println(titleList);
                for (int i = 0; i < titleList.size(); i++) {
                    ListData listData = new ListData();
                    String title = titleList.get(i);
                    listData.num = String.valueOf(i + 1);
                    listData.title = title;

                    listData.body_1 = "주소";
                    listData.body_2 = "날짜";
                    listViewData.add(listData);
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ListAdapter oAdapter = new CustomListView(listViewData);
                        listView.setAdapter(oAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(main_board1.this, activity_consult_detail.class);
                                intent.putExtra("title", titleList.get(position));
                                intent.putExtra("category", categoryList.get(position));
                                intent.putExtra("content", contentList.get(position));
                                startActivity(intent);

                            }
                        });
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}