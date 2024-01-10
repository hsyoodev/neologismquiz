package com.hsyoodev.neologismquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 데이터베이스 생성
        new DBHelper(this);

        TextView firstWordText = findViewById(R.id.firstWordText);
        TextView secondWordText = findViewById(R.id.secondWordText);

        String[] appNames = getResources().getString(R.string.app_name).split(" ");

        firstWordText.setText(appNames[0]);
        secondWordText.setText(appNames[1]);

        // 애니메이션
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
        Animation tingtingAnimation = AnimationUtils.loadAnimation(this, R.anim.tingting);
        tingtingAnimation.setAnimationListener(animationListener);
        firstWordText.startAnimation(tingtingAnimation);
        secondWordText.startAnimation(tingtingAnimation);
    }
}