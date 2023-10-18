package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class main_board extends AppCompatActivity {
    Button free, study, support, know;
    String email, nickname;
    View.OnClickListener cl;
    ArrayList<String> emailList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board);
        free = (Button) findViewById(R.id.free);
        study = (Button) findViewById(R.id.study);
        support = (Button) findViewById(R.id.support);
        know = (Button) findViewById(R.id.know);
        Intent intent = getIntent();
        email = intent.getStringExtra("이메일");
        System.out.println(email);
        connect con = new connect();
        con.start();
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.free:
                        Intent intent = new Intent(getApplicationContext(), main_board1.class);
                        intent.putExtra("닉네임",nickname);
                        startActivity(intent);
                        break;
                    case R.id.study:
                        intent = new Intent(getApplicationContext(), main_board2.class);
                        intent.putExtra("닉네임",nickname);
                        startActivity(intent);
                        break;
                    case R.id.support:
                        intent = new Intent(getApplicationContext(), main_board3.class);
                        intent.putExtra("닉네임",nickname);
                        startActivity(intent);
                        break;
                    case R.id.know:
                        intent = new Intent(getApplicationContext(), main_board4.class);
                        intent.putExtra("닉네임",nickname);
                        startActivity(intent);
                        break;

                }

            }
        };
        free.setOnClickListener(cl);
        study.setOnClickListener(cl);
        support.setOnClickListener(cl);
        know.setOnClickListener(cl);
    }

    class connect extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://3.35.45.245:8080/api/user/"+email);
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
                JSONObject jsonObject = new JSONObject(data);
                nickname = jsonObject.getString("nickname");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}