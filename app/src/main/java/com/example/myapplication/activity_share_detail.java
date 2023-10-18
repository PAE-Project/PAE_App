package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class activity_share_detail extends AppCompatActivity {
    TextView title_tv, content_tv, price_tv, category_tv, state_tv, nickname_tv;
    ImageView img;
    private LinearLayout recipeViewLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String price = intent.getStringExtra("price");
        String category = intent.getStringExtra("category");
        String state = intent.getStringExtra("state");
        String nickname = intent.getStringExtra("nickname");


        title_tv = (TextView) findViewById(R.id.title_tv);
        price_tv = (TextView) findViewById(R.id.price_tv);
        category_tv = (TextView) findViewById(R.id.category_tv);
        nickname_tv = (TextView) findViewById(R.id.nickname_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        img = (ImageView) findViewById(R.id.img);

        img.setImageResource(R.drawable.logo);


        title_tv.setText(title);
        if(price.equals("0")){
            price_tv.setText("무료나눔");
        } else{
            price_tv.setText(price+"원");
        }

        category_tv.setText(category);
        content_tv.setText(state);
        nickname_tv.setText(nickname);
//        date_tv.setText(date);

    }
}
