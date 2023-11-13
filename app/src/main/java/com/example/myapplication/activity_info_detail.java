package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class activity_info_detail extends AppCompatActivity {
    TextView title_tv, content_tv;
    private LinearLayout recipeViewLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
//        String date = intent.getStringExtra("date");
//        String img = intent.getStringExtra("img");
//        String url = intent.getStringExtra("url");


        title_tv = (TextView) findViewById(R.id.title_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        final String url = "_e5evpygDz8";


        title_tv.setText(title);
        content_tv.setText(content);
//        date_tv.setText(date);
        recipeViewLinearLayout = findViewById(R.id.recipe_view_linearLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 30;
        YouTubePlayerView ypv = new YouTubePlayerView(activity_info_detail.this);
        ypv.setLayoutParams(params);
        recipeViewLinearLayout.addView(ypv);
        getLifecycle().addObserver(ypv);
        ypv.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(url,0);
            }
        });
    }
}
