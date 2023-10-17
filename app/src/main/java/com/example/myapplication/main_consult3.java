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

public class main_consult3 extends AppCompatActivity {
    ListView listView;
    Button go_register;
    View.OnClickListener cl;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> costList = new ArrayList<>();
    ArrayList<String> fieldList = new ArrayList<>();
    ArrayList<Boolean> genderList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_consult3);

        listView = findViewById(R.id.listView);

        connect con = new connect();
        con.start();

    }

    class connect extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/consultants");
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

                int num = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameList.add(jsonObject.optString("name"));
                    costList.add(jsonObject.optString("cost"));
                    genderList.add(jsonObject.optBoolean("gender"));
                    fieldList.add(jsonObject.optString("field"));

                    ListData listData = new ListData();
                    String name = nameList.get(i);
                    String cost = costList.get(i);
                    String field = fieldList.get(i);
                    boolean gender_check = genderList.get(i);
                    String gender;
                    listData.num = String.valueOf(num + 1);
                    listData.title = field;

                    listData.body_1 = name;
                    if(gender_check) {
                        gender = "남자";
                    }else{
                        gender = "여지";
                    }
                    listData.body_2 = gender;
                    num++;
                    listViewData.add(listData);


                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            ListAdapter oAdapter = new CustomListView(listViewData);
                            listView.setAdapter(oAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(main_consult3.this, main_consult_info.class);
                                    intent.putExtra("name", nameList.get(position));
                                    intent.putExtra("cost", costList.get(position));
                                    intent.putExtra("field", fieldList.get(position));
                                    intent.putExtra("gender", gender);
                                    startActivity(intent);

                                }
                            });
                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}