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

public class main_my_share extends AppCompatActivity {
    ListView listView;
    Button go_register;
    View.OnClickListener cl;
    String email_data;
    String nickname;
    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> stateList = new ArrayList<>();
    ArrayList<String> imgList = new ArrayList<>();
    ArrayList<String> nicknameList = new ArrayList<>();
    ArrayList<String> priceList = new ArrayList<>();;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_my_share);


        listView = findViewById(R.id.listView);
        Intent secondIntent = getIntent();
        email_data = secondIntent.getStringExtra("이메일");
        nickname = secondIntent.getStringExtra("닉네임");


        connect con = new connect();
        con.start();

    }


    class connect extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://3.35.45.245:8080/api/shares");
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
                    if(jsonObject.getString("nickname").equals(nickname)){
                        titleList.add(jsonObject.getString("title"));
                        priceList.add(jsonObject.getString("price"));
                        categoryList.add(jsonObject.getString("category"));
                        stateList.add(jsonObject.getString("state"));
                        nicknameList.add(jsonObject.getString("nickname"));
                        imgList.add(jsonObject.getString("img"));
                    }
                }

                for (int i = 0; i < titleList.size(); i++) {
                    ListData listData = new ListData();
                    listData.num = String.valueOf(i + 1);
                    listData.mainImage = R.drawable.ic_launcher_foreground;
                    listData.title = titleList.get(i);

                    listData.body_1 = "종류 : " + categoryList.get(i);
                    listData.body_2 = "상태 : " + stateList.get(i);
                    listData.body_3 = "닉네임 : " + nicknameList.get(i);
                    listViewData.add(listData);
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ListAdapter oAdapter = new CustomListView1(listViewData);
                        listView.setAdapter(oAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(main_my_share.this, activity_share_detail.class);
                                intent.putExtra("title", titleList.get(position));
                                intent.putExtra("price", priceList.get(position));
                                intent.putExtra("category", categoryList.get(position));
                                intent.putExtra("state", stateList.get(position));
                                intent.putExtra("nickname", nicknameList.get(position));
                                //intent.putExtra("img", imgList.get(position));
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