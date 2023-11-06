package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegisterActivity_board extends AppCompatActivity {
    public JSONObject userdata = new JSONObject();
    TextView date_tv, nickname_tv;
    EditText title_et, content_et;
    Button reg;
    View.OnClickListener cl;
    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    LocalDate date_now = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_board);
        reg = (Button) findViewById(R.id.reg_button);

        title_et = (EditText) findViewById(R.id.title_et);
        content_et = (EditText) findViewById(R.id.content_et);
        date_tv = (TextView) findViewById(R.id.date_tv);
        nickname_tv = (TextView) findViewById(R.id.nickname_tv);

        Intent secondIntent = getIntent();
        String nickname = secondIntent.getStringExtra("닉네임");
        String category = secondIntent.getStringExtra("카테고리");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date_now = LocalDate.now();
        }
        nickname_tv.setText(nickname);
        date_tv.setText(date_now.toString());

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.reg_button:

                        try {
                            userdata.put("title", title_et.getText().toString());
                            userdata.put("category", category);
                            userdata.put("content", content_et.getText().toString());
                            userdata.put("date", date_now);
                            userdata.put("nickname", nickname);
                            userdata.put("created_at", date_now);
                            userdata.put("updated_at", date_now);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // RequestBody 생성
                        RequestBody body = RequestBody.create(userdata.toString(), JSON);

                        // Request 생성
                        Request request = new Request.Builder()
                                .url("http://3.35.45.245:8080/api/board")
                                .post(body)
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
                        Toast toast = Toast.makeText(getApplicationContext(), "작성완료",Toast.LENGTH_SHORT);
                        toast.show();
                        if(category.equals("육아 정보")){
                            Intent intent = new Intent(getApplicationContext(), main_board1.class);
                            startActivity(intent);
                        }
                        else if(category.equals("교육 정보")){
                            Intent intent = new Intent(getApplicationContext(), main_board2.class);
                            startActivity(intent);
                        }else if(category.equals("지원 정보")){
                            Intent intent = new Intent(getApplicationContext(), main_board3.class);
                            startActivity(intent);
                        }else if(category.equals("일상 정보")){
                            Intent intent = new Intent(getApplicationContext(), main_board4.class);
                            startActivity(intent);
                        }



                        break;

                }

            }
        };
        reg.setOnClickListener(cl);
    }
}
