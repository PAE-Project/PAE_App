package com.example.myapplication;

import static com.example.myapplication.login_sign_up2.VALID_PASSWOLD_REGEX_ALPHA_NUM;

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
import java.util.regex.Matcher;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class main_update_pw extends AppCompatActivity {
    EditText password, passwordCheck;
    String email_data;
    View.OnClickListener cl;
    Button check, home;
    private OkHttpClient client = new OkHttpClient();
    public JSONObject userdata = new JSONObject();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_update_pw);
        password = (EditText) findViewById(R.id.pw);
        passwordCheck = (EditText) findViewById(R.id.pw_ch);
        home = (Button) findViewById(R.id.home);

        Intent secondIntent = getIntent();
        email_data = secondIntent.getStringExtra("이메일");
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.home:
                        if (passwordCheck.length() == 0 && password.length() == 0) {
                            Toast.makeText(getApplicationContext(), "값을 모두 입력해주세요", Toast.LENGTH_LONG).show();
                        } else if (password.length() == 0) {
                            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                        } else if (passwordCheck.length() == 0) {
                            Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력해주세요", Toast.LENGTH_LONG).show();
                        } else {
                            if (validatePassword(password.getText().toString())) {
                                if (password.getText().toString().equals(passwordCheck.getText().toString())) {
                                    LocalDate date = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                        date = LocalDate.now();
                                    }
                                    try {
                                        userdata.put("pw", password.getText().toString());
                                        userdata.put("update_at", date);
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    // RequestBody 생성
                                    RequestBody body = RequestBody.create(userdata.toString(), JSON);

                                    // Request 생성
                                    Request request = new Request.Builder()
                                            .url("http://3.35.45.245:8080/api/user/email/" + email_data)
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
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            } else {

                                Toast toast = Toast.makeText(getApplicationContext(), "영문+숫자+특수문자 포함 8자 이상.", Toast.LENGTH_SHORT);
                                toast.show();

                            }
                        }
                }
            }
        };
        home.setOnClickListener(cl);
    }

    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }

}