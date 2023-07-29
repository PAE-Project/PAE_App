package com.example.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class main_1001 extends AppCompatActivity {
    TextView go_1004, go_1007;
    Toolbar toolbar;
    RelativeLayout ll;
    Button go_3001_3002_3003;
    View.OnClickListener cl;
    private DrawerLayout drawerLayout;
    private View drawerView;
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_1001);
        FrameLayout contentFrame = findViewById(R.id.content_frame); // 1. 기반이 되는 FrameLayout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 2. inflater 생성
        inflater.inflate(R.layout.main_button,contentFrame,true); // 3. (넣을 xml 파일명, 기반 layout 객체, true)
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        go_1004 = (TextView) findViewById(R.id.go_1004);
        go_1007 = (TextView) findViewById(R.id.go_1007);
        go_3001_3002_3003 = (Button) findViewById(R.id.go_3001_3002_3003);
        drawerView = (View) findViewById(R.id.drawerView);
        drawerLayout.setDrawerListener(listener);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_1004:
                        Intent intent = new Intent(getApplicationContext(), main_1004_1005.class);
                        startActivity(intent);
                        break;
                    case R.id.go_3001_3002_3003:
                        intent = new Intent(getApplicationContext(), main_3001_3002_3003.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        go_1004.setOnClickListener(cl);
        go_3001_3002_3003.setOnClickListener(cl);
        String name = "홍길동";
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name+"님 환영합니다.");
        ll = (RelativeLayout) findViewById(R.id.ll);
        ll.bringToFront();

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu); //왼쪽 상단 버튼 아이콘 지정

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
