package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_board_detail extends AppCompatActivity {
    TextView title_tv, content_tv, date_tv;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String content = intent.getStringExtra("content");
        category = intent.getStringExtra("category");

        title_tv = (TextView) findViewById(R.id.title_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        date_tv = (TextView) findViewById(R.id.date_tv);

        title_tv.setText(title);
        content_tv.setText(content);
        date_tv.setText(date);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(category.equals("육아 정보")){
            Intent intent = new Intent(activity_board_detail.this, main_board1.class);
            startActivity(intent);
            finish();
        }
        if(category.equals("교육 정보")){
            Intent intent = new Intent(activity_board_detail.this, main_board2.class);
            startActivity(intent);
            finish();
        }
        if(category.equals("지원 정보")){
            Intent intent = new Intent(activity_board_detail.this, main_board3.class);
            startActivity(intent);
            finish();
        }
        if(category.equals("일상 정보")){
            Intent intent = new Intent(activity_board_detail.this, main_board4.class);
            startActivity(intent);
            finish();
        }
    }

}
