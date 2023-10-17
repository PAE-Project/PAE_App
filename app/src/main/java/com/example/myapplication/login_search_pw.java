package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login_search_pw extends AppCompatActivity {
    Button go_login, check;
    EditText email, password, passwordCheck;
    String pw, email_data;
    View.OnClickListener cl;
    private OkHttpClient client = new OkHttpClient();
    public JSONObject userdata = new JSONObject();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_search_pw);

        go_login = (Button) findViewById(R.id.go_login);
        email = (EditText) findViewById(R.id.email);
        check = (Button) findViewById(R.id.check);
        password = (EditText) findViewById(R.id.password_0007);
        passwordCheck = (EditText) findViewById(R.id.passwordCheck_0007);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.go_login:
                        LocalDate date = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            date = LocalDate.now();
                        }
                        try {
                            userdata.put("pw",password.getText().toString());
                            userdata.put("update_at",date);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        // RequestBody 생성
                        RequestBody body = RequestBody.create(userdata.toString(), JSON);

                        // Request 생성
                        Request request = new Request.Builder()
                                .url("http://3.35.45.245:8080/api/user/email/"+email_data)
                                .put(body)
                                .build();

                        // 비동기 방식으로 요청 전송
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code " + response);
                                }

                                // 수신된 JSON 데이터 디버그 로그로 출력
                                try {
                                    String responseData = response.body().string();
                                    JSONObject receivedJson = new JSONObject(responseData);
                                    Log.d("Received JSON", receivedJson.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Toast toast = Toast.makeText(getApplicationContext(), "변경완료", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), login_sign_in.class);
                        startActivity(intent);
                        break;
                    case R.id.check:
                        connect con = new connect();
                        con.start();
                        break;
                }

            }
        };
        check.setOnClickListener(cl);
        go_login.setOnClickListener(cl);

    }
    class connect extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://3.35.45.245:8080/api/user/" + email.getText().toString());
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
                JSONObject json = null;
                json = new JSONObject(data);
                pw = json.getString("pw");
                email_data = json.getString("email");
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(getApplicationContext(), "비밀번호" + pw, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }, 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
