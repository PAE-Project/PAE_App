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
import java.time.LocalDate;
import java.util.ArrayList;

public class main_consult3 extends AppCompatActivity {
    ListView listView;
    Button go_register;
    View.OnClickListener cl;
    String nickname;
    ArrayList<String> emailList = new ArrayList<>();
    ArrayList<String> detailList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> fieldList = new ArrayList<>();
    ArrayList<String> genderList = new ArrayList<>();
    ArrayList<String> costList = new ArrayList<>();

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_consult3);

        listView = findViewById(R.id.listView);

        connect con = new connect();
        con.start();

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {


                }

            }
        };

    }

    class connect extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://3.35.45.245:8080/api/consultants");
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
                    if(jsonObject.getString("field").equals("육아 상담")){
                        detailList.add(jsonObject.getString("detail"));
                        emailList.add(jsonObject.getString("email"));
                        genderList.add(jsonObject.getString("gender"));
                        fieldList.add(jsonObject.getString("field"));
                        nameList.add(jsonObject.getString("name"));
                        costList.add(jsonObject.getString("cost"));
                    }
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject.getString("field").equals("가정 상담")){
                        detailList.add(jsonObject.getString("detail"));
                        emailList.add(jsonObject.getString("email"));
                        genderList.add(jsonObject.getString("gender"));
                        fieldList.add(jsonObject.getString("field"));
                        nameList.add(jsonObject.getString("name"));
                        costList.add(jsonObject.getString("cost"));
                    }
                }

                for (int i = 0; i < detailList.size(); i++) {
                    ListData listData = new ListData();
                    listData.num = String.valueOf(i + 1);
                    listData.title = detailList.get(i);

                    listData.body_1 = "상담 내용 : " + fieldList.get(i);
                    listData.body_1 = "상담원 : " + nameList.get(i);
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
                                Intent intent = new Intent(main_consult3.this, main_consult_info.class);
                                intent.putExtra("detail", detailList.get(position));
                                intent.putExtra("email", emailList.get(position));
                                intent.putExtra("gender", genderList.get(position));
                                intent.putExtra("field", fieldList.get(position));
                                intent.putExtra("name", nameList.get(position));
                                intent.putExtra("cost", costList.get(position));
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