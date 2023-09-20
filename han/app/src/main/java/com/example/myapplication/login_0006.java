package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.ContentValues;
import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class login_0006 extends AppCompatActivity {
    TextView go_0002, go_0007, go_0008;
    Button go_1001;
    public EditText edit_email, edit_password;
    public static String email, pw, result = "false";
    View.OnClickListener cl;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0006);

        go_0002 = (TextView) findViewById(R.id.SignUp);
        go_0008 = (TextView) findViewById(R.id.femail);
        go_0007 = (TextView) findViewById(R.id.fpassword);
        go_1001 = (Button) findViewById(R.id.go_1001);
        edit_email = (EditText) findViewById(R.id.email_0006);
        edit_password = (EditText) findViewById(R.id.password_0006);

        cl = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.SignUp:
                        Intent intent = new Intent(getApplicationContext(), login_0002.class);
                        startActivity(intent);
                        break;

                    case R.id.femail:
                        intent = new Intent(getApplicationContext(), login_0008.class);
                        startActivity(intent);
                        break;

                    case R.id.fpassword:
                        intent = new Intent(getApplicationContext(), login_0007.class);
                        startActivity(intent);
                        break;

                    case R.id.go_1001:
                        email = edit_email.getText().toString();
                        pw = edit_password.getText().toString();
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL("http://10.0.2.2:8080/api/login/"+email+"/"+pw);
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

                                    // 서버 응답을 처리한 후에 UI 업데이트를 수행합니다.
                                    final String loginResult = result.toString();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(loginResult.equals("true")){
                                                Intent intent = new Intent(getApplicationContext(), main_1001.class);
                                                startActivity(intent);
                                            } else {
                                                Toast toast = Toast.makeText(getApplicationContext(), "로그인 실패.", Toast.LENGTH_SHORT);
                                                toast.show();
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        break;
                }
            }
        };
        go_0002.setOnClickListener(cl);
        go_0007.setOnClickListener(cl);
        go_0008.setOnClickListener(cl);
        go_1001.setOnClickListener(cl);

    }

}
