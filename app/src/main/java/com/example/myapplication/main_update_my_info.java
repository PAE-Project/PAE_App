package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class main_update_my_info extends AppCompatActivity {
    public JSONObject userdata = new JSONObject();
    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Button home;
    EditText ch_name, ch_nick, ch_phone, ch_address1, ch_address2;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_update_my_info);

        ch_name = (EditText) findViewById(R.id.ch_name);
        ch_nick = (EditText) findViewById(R.id.ch_nick);
        ch_phone = (EditText) findViewById(R.id.ch_phone);
        ch_address1 = (EditText) findViewById(R.id.ch_address1);
        ch_address2 = (EditText) findViewById(R.id.ch_address2);
        home = (Button) findViewById(R.id.home);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.home:
                        try {
                            userdata.put("name", ch_name.getText().toString());
                            userdata.put("nickname", ch_nick.getText().toString());
                            userdata.put("phone", ch_phone.getText().toString());
                            userdata.put("address", ch_address1.getText().toString()+" "+ch_address2.getText().toString());

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // RequestBody 생성
                        RequestBody body = RequestBody.create(userdata.toString(), JSON);

                        // Request 생성
                        Request request = new Request.Builder()
                                .url("http://3.35.45.245:8080/api/user/22")
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
                        Intent intent = new Intent(getApplicationContext(), main_screen1.class);
                        startActivity(intent);
                        break;

                }

            }
        };
        home.setOnClickListener(cl);

    }
}