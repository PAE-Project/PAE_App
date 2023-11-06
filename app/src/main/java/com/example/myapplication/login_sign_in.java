package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class login_sign_in extends AppCompatActivity {
    TextView go_0002, go_0007, go_0008;
    Button go_1001;
    public EditText edit_email, edit_password;
    public static String email, pw, loginResult = "false";
    View.OnClickListener cl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_in);

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
                        Intent intent = new Intent(getApplicationContext(), login_sign_up1.class);
                        startActivity(intent);
                        break;

                    case R.id.femail:
                        intent = new Intent(getApplicationContext(), login_search_email.class);
                        startActivity(intent);
                        break;

                    case R.id.fpassword:
                        intent = new Intent(getApplicationContext(), login_search_pw.class);
                        startActivity(intent);
                        break;

                    case R.id.go_1001:
                        email = edit_email.getText().toString();
                        pw = edit_password.getText().toString();
                        if(email.length() == 0 && pw.length() == 0){
                            Toast.makeText(getApplicationContext(), "값을 모두 입력해주세요", Toast.LENGTH_LONG).show();
                        } else if (email.length() == 0) {
                            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_LONG).show();
                        } else if (pw.length() == 0) {
                            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                        } else{
                            connect con = new connect();
                            con.start();
                        }
                        break;
                }
            }
        };
        go_0002.setOnClickListener(cl);
        go_0007.setOnClickListener(cl);
        go_0008.setOnClickListener(cl);
        go_1001.setOnClickListener(cl);

    }

    class connect extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://3.35.45.245:8080/api/login/" + email + "/" + pw);
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
                String put_email = email;
                // 서버 응답을 처리한 후에 UI 업데이트를 수행합니다.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loginResult = result.toString();
                        if (loginResult.equals("true")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "로그인 성공.", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(getApplicationContext(), main_screen1.class);
                            intent.putExtra("이메일", email);
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
    }
}
