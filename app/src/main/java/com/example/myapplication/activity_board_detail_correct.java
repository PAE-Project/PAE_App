package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_board_detail_correct extends AppCompatActivity {
    TextView title_tv, content_tv, date_tv, category_tv;
    Button modify;
    String nickname;
    View.OnClickListener cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail_correct);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String content = intent.getStringExtra("content");
        String num = intent.getStringExtra("num");
        String category = intent.getStringExtra("category");
        nickname = intent.getStringExtra("nickname");

        title_tv = (TextView) findViewById(R.id.title_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        date_tv = (TextView) findViewById(R.id.date_tv);
        category_tv = (TextView) findViewById(R.id.category_tv);
        modify = (Button) findViewById(R.id.modify);

        title_tv.setText(title);
        content_tv.setText(content);
        category_tv.setText(category);
        date_tv.setText(date);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.modify:
                        Intent intent = new Intent(activity_board_detail_correct.this, activity_board_detail_correct1.class);
                        intent.putExtra("num", num);
                        intent.putExtra("title", title);
                        intent.putExtra("date", date);
                        intent.putExtra("content", content);
                        intent.putExtra("nickname", nickname);
                        startActivity(intent);
                        finish();
                        break;

                }

            }
        };
        modify.setOnClickListener(cl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(activity_board_detail_correct.this, main_my_board.class);
        startActivity(intent);
        intent.putExtra("닉네임", nickname);
        finish();
    }
}
