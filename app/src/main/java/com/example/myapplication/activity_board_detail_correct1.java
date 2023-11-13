package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

public class activity_board_detail_correct1 extends AppCompatActivity {
    TextView date_tv, category_tv;
    public JSONObject userdata = new JSONObject();
    EditText title_et, content_et;
    Button modify, delete;
    View.OnClickListener cl;
    String nickname;
    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail_correct1);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String content = intent.getStringExtra("content");
        nickname = intent.getStringExtra("nickname");
        String num = intent.getStringExtra("num");


        title_et = (EditText) findViewById(R.id.title_et);
        content_et = (EditText) findViewById(R.id.content_et);
        date_tv = (TextView) findViewById(R.id.date_tv);
        category_tv = (TextView) findViewById(R.id.category_tv);
        modify = (Button) findViewById(R.id.modify);
        delete = (Button) findViewById(R.id.delete);

        category_tv.setText("눌러서 상태 변경");
        title_et.setText(title);
        date_tv.setText(date);
        content_et.setText(content);

        category_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup1 = new PopupMenu(activity_board_detail_correct1.this, view);
                getMenuInflater().inflate(R.menu.popup1, popup1.getMenu());

                //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
                popup1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.know:
                                category_tv.setText("육아 정보");
                                break;

                            case R.id.free:
                                category_tv.setText("일상 정보");
                                break;

                            case R.id.study:
                                category_tv.setText("교육 정보");
                                break;

                            case R.id.support:
                                category_tv.setText("지원 정보");
                                break;
                        }
                        return false;
                    }
                });
                popup1.show();
                return true;
            }
        });
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.modify:
                        LocalDate date_now = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            date_now = LocalDate.now();
                        }
                        try {
                            userdata.put("title", title_et.getText().toString());
                            userdata.put("category", category_tv.getText().toString());
                            userdata.put("content", content_et.getText().toString());
                            userdata.put("nickname", nickname);
                            userdata.put("updated_at", date_now);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        // RequestBody 생성
                        RequestBody body = RequestBody.create(userdata.toString(), JSON);

                        // Request 생성
                        Request request = new Request.Builder()
                                .url("http://3.35.45.245:8080/api/board/"+num)
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
                        Toast toast = Toast.makeText(getApplicationContext(), "수정완료",Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(activity_board_detail_correct1.this, main_my_board.class);
                        intent.putExtra("닉네임", nickname);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.delete:
                        // RequestBody 생성
                        // Request 생성
                        request = new Request.Builder()
                                .url("http://3.35.45.245:8080/api/board/"+num)
                                .delete()
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
                        toast = Toast.makeText(getApplicationContext(), "삭제 완료",Toast.LENGTH_SHORT);
                        toast.show();
                        intent = new Intent(activity_board_detail_correct1.this, main_my_board.class);
                        intent.putExtra("닉네임", nickname);
                        startActivity(intent);
                        finish();
                        break;
                }

            }
        };
        modify.setOnClickListener(cl);
        delete.setOnClickListener(cl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(activity_board_detail_correct1.this, activity_board_detail_correct.class);
        intent.putExtra("닉네임", nickname);
        startActivity(intent);
        finish();
    }
}
