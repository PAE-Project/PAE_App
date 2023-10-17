package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class main_screen1 extends AppCompatActivity {
    TextView find_email, change_pw, change_info;
    Toolbar toolbar;
    Button go_info, go_map, go_board, go_share, go_consult;
    View.OnClickListener cl;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen1);
        FrameLayout contentFrame = findViewById(R.id.content_frame); // 1. 기반이 되는 FrameLayout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 2. inflater 생성
        inflater.inflate(R.layout.main_screen2,contentFrame,true); // 3. (넣을 xml 파일명, 기반 layout 객체, true)
        go_consult = (Button) findViewById(R.id.go_consult);
        go_map = (Button) findViewById(R.id.go_map);
        go_board = (Button) findViewById(R.id.go_board);
        go_info = (Button) findViewById(R.id.go_info);
        go_share = (Button) findViewById(R.id.go_share);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.navigation_view);

        Intent secondIntent = getIntent();
        String email = secondIntent.getStringExtra("이메일");

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.change_pw:
                        Intent intent = new Intent(getApplicationContext(), main_update_pw.class);
                        intent.putExtra("이메일", email);
                        startActivity(intent);
                        break;
                    case R.id.change_info:
                        intent = new Intent(getApplicationContext(), main_update_my_info.class);
                        startActivity(intent);
                        break;
                    case R.id.go_0001:
                        intent = new Intent(getApplicationContext(), login_start.class);
                        startActivity(intent);
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu); //왼쪽 상단 버튼 아이콘 지정

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_consult:
                        Intent intent = new Intent(getApplicationContext(), main_consult.class);
                        startActivity(intent);
                        break;
                    case R.id.go_map:
                        intent = new Intent(getApplicationContext(), map.class);
                        startActivity(intent);
                        break;
                    case R.id.go_info:
                        intent = new Intent(getApplicationContext(), main_info.class);
                        startActivity(intent);
                        break;
//                    case R.id.go_share:
//                        intent = new Intent(getApplicationContext(), main_.class);
//                        startActivity(intent);
//                        break;
                    case R.id.go_board:
                        intent = new Intent(getApplicationContext(), main_board.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        go_consult.setOnClickListener(cl);
        go_info.setOnClickListener(cl);
        go_board.setOnClickListener(cl);
        go_map.setOnClickListener(cl);
    }


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
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
