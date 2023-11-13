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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

public class main_board1 extends AppCompatActivity {
    ListView listView;
    Button go_register;
    View.OnClickListener cl;
    String nickname;
    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> contentList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> nicknameList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board1);
        Intent intent = getIntent();
        nickname = intent.getStringExtra("닉네임");
        go_register = (Button) findViewById(R.id.go_register);
        listView = findViewById(R.id.listView);


        connect con = new connect();
        con.start();
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go_register:

                        Intent intent = new Intent(getApplicationContext(), RegisterActivity_board.class);
                        intent.putExtra("닉네임",nickname);
                        intent.putExtra("카테고리", "육아 정보");
                        startActivity(intent);
                        finish();
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
                ArrayList<ListData> listViewData = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject.getString("category").equals("육아 정보")){
                        titleList.add(jsonObject.getString("title"));
                        contentList.add(jsonObject.getString("content"));
                        dateList.add(jsonObject.getString("date"));
                        nicknameList.add(jsonObject.getString("nickname"));
                    }
                }

                for (int i = 0; i < titleList.size(); i++) {
                    ListData listData = new ListData();
                    listData.num = String.valueOf(i + 1);
                    listData.title = titleList.get(i);

                    listData.body_1 = "닉네임 : " + nicknameList.get(i);
                    listData.body_2 = "작성일 : " + dateList.get(i);
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
                                Intent intent = new Intent(main_board1.this, activity_board_detail.class);
                                intent.putExtra("title", titleList.get(position));
                                intent.putExtra("date", dateList.get(position));
                                intent.putExtra("content", contentList.get(position));
                                intent.putExtra("category", "육아 정보");
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}