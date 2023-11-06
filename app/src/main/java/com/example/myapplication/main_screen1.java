package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

public class main_screen1 extends AppCompatActivity {
    TextView tv_logo, tv_name, tv_info;
    Toolbar toolbar;
    String email, nickname, address, name, date, gender;
    Button go_info, go_map, go_board, go_share, go_consult;
    View.OnClickListener cl;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView ivMenu;
    LocalDate now;
    int age;
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
        tv_logo = (TextView) findViewById(R.id.tv_logo);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.navigation_view);
        ivMenu=findViewById(R.id.iv_menu);

        setSupportActionBar(toolbar);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        Intent secondIntent = getIntent();
        email = secondIntent.getStringExtra("이메일");


        finduser fin = new finduser();
        fin.start();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.change_pw:
                        Intent intent = new Intent(getApplicationContext(), main_update_pw.class);
                        intent.putExtra("이메일", email);
                        startActivity(intent);
                        break;
                    case R.id.my_board:
                        intent = new Intent(getApplicationContext(), main_my_board.class);
                        intent.putExtra("이메일", email);
                        intent.putExtra("닉네임", nickname);
                        startActivity(intent);
                        break;
                    case R.id.my_share:
                        intent = new Intent(getApplicationContext(), main_my_share.class);
                        intent.putExtra("이메일", email);
                        intent.putExtra("닉네임", nickname);
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

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        View nav_header_view = navigationView.getHeaderView(0);

        tv_name = (TextView) nav_header_view.findViewById(R.id.tv_name);
        tv_info = (TextView) nav_header_view.findViewById(R.id.tv_info);


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
                    case R.id.go_share:
                        intent = new Intent(getApplicationContext(), main_share.class);
                        intent.putExtra("이메일", email);
                        intent.putExtra("닉네임", nickname);
                        intent.putExtra("주소", address);
                        startActivity(intent);
                        break;
                    case R.id.go_board:
                        intent = new Intent(getApplicationContext(), main_board.class);
                        intent.putExtra("이메일", email);
                        startActivity(intent);
                        break;
                }
            }
        };
        go_consult.setOnClickListener(cl);
        go_info.setOnClickListener(cl);
        go_board.setOnClickListener(cl);
        go_map.setOnClickListener(cl);
        go_share.setOnClickListener(cl);
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
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    class finduser extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://3.35.45.245:8080/api/user/"+email);
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


                final String data = result.toString();
                JSONObject userdata = null;
                userdata = new JSONObject(data);
                nickname = userdata.getString("nickname");
                address = userdata.getString("address");
                name = userdata.getString("name");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    now = LocalDate.now();
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    age = now.getYear() - Integer.parseInt(userdata.getString("date").substring(0,4));
                }
                if(userdata.getBoolean("gender")) {
                    gender = "남자";
                } else{
                    gender = "여자";
                }
                tv_logo.setText(name+"님 환영합니다");
                tv_name.setText(name+"님 환영합니다");
                tv_info.setText(gender+", "+age);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
