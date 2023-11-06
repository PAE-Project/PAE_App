package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class login_sign_up2 extends AppCompatActivity {
    EditText email, password, passwordCheck;
    Button go_0004;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up2);

        go_0004 = (Button) findViewById(R.id.go_0004);
        email = (EditText) findViewById(R.id.email_0003);
        password = (EditText) findViewById(R.id.password_0003);
        passwordCheck = (EditText) findViewById(R.id.password_0003);
        Intent getIntent = getIntent();
        boolean helper = getIntent.getBooleanExtra("helper",true);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login_sign_up3.class);
                switch (v.getId()) {
                    case R.id.go_0004:
                        Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;


                        if(email.length()==0 && passwordCheck.length()==0 && password.length()==0){
                            Toast.makeText(getApplicationContext(),"값을 모두 입력해주세요",Toast.LENGTH_LONG).show();
                        }else if(email.length()==0){
                            Toast.makeText(getApplicationContext(),"이메일을 입력해주세요",Toast.LENGTH_LONG).show();
                        }else if(password.length()==0){
                            Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요",Toast.LENGTH_LONG).show();
                        }else if(passwordCheck.length()==0){
                            Toast.makeText(getApplicationContext(),"비밀번호 확인을 입력해주세요",Toast.LENGTH_LONG).show();
                        }else{
                            if(pattern.matcher(email.getText().toString()).matches() && validatePassword(password.getText().toString())){
                                if(password.getText().toString().equals(passwordCheck.getText().toString())){
                                    intent.putExtra("pw",password.getText().toString());
                                    intent.putExtra("email",email.getText().toString());
                                    intent.putExtra("helper",helper);
                                    startActivity(intent);
                                    break;
                                } else{
                                    Toast toast = Toast.makeText(getApplicationContext(), "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            } else {
                                if(!(pattern.matcher(email.getText().toString()).matches())){
                                    Toast toast = Toast.makeText(getApplicationContext(), "이메일 형식이 아닙니다.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "영문+숫자+특수문자 포함 8자 이상.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            }
                        }
                }
            }
        };
        go_0004.setOnClickListener(cl);

    }
    //비밀번호 정규식
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM
            = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{8,16}$"); // 4자리 ~ 16자리까지 가능

    // 비밀번호 검사
    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }

}
