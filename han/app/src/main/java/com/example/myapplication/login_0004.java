package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class login_0004 extends AppCompatActivity {
    Button go_0006, male, female;
    EditText Name, NickName, Year, Month, Day, Phonenum, adress1, adress2;
    String na, ni, ph, ad1, ad2;
    int y, m, d;
    View.OnClickListener cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0004);

        go_0006 = (Button) findViewById(R.id.go_0006);
        male = (Button) findViewById(R.id.male);
        female = (Button) findViewById(R.id.female);
        Name = (EditText) findViewById(R.id.Name);
        NickName = (EditText) findViewById(R.id.NickName);
        Year = (EditText) findViewById(R.id.Year);
        Month = (EditText) findViewById(R.id.Month);
        Day = (EditText) findViewById(R.id.Day);
        Phonenum = (EditText) findViewById(R.id.Phonenum);
        adress1 = (EditText) findViewById(R.id.adress1);
        adress2 = (EditText) findViewById(R.id.adress2);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_0006:
//                        na = Name.getText().toString();
//                        ni = NickName.getText().toString();
//                        y = Integer.parseInt(Year.getText().toString());
//                        m = Integer.parseInt(Month.getText().toString());
//                        d = Integer.parseInt(Day.getText().toString());
//                        ph = Phonenum.getText().toString();
//                        ad1 = adress1.getText().toString();
//                        ad2 = adress2.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), login_0006.class);
                        startActivity(intent);
                        break;
                    case R.id.male:
                        male.setSelected(true);
                        female.setSelected(false);
                        break;
                    case R.id.female:
                        male.setSelected(false);
                        female.setSelected(true);
                        break;
                }

            }
        };
        go_0006.setOnClickListener(cl);
        male.setOnClickListener(cl);
        female.setOnClickListener(cl);
    }
}