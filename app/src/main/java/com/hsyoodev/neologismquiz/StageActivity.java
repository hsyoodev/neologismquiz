package com.hsyoodev.neologismquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class StageActivity extends AppCompatActivity {
    private Button stageButton1, stageButton2, stageButton3, stageButton4, stageButton5, stageButton6, stageButton7, stageButton8, stageButton9, stageButton10;
    private List<Button> stageButtons;
    private SharedPreferences sharedPreferences;
    private int stageNumber;
    private Animation scaleAnimation;
    private Button currentStageButton;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        // 광고
        createAdMob();

        // 커스텀 툴바
        createToolbar();

        stageButton1 = findViewById(R.id.stageButton1);
        stageButton2 = findViewById(R.id.stageButton2);
        stageButton3 = findViewById(R.id.stageButton3);
        stageButton4 = findViewById(R.id.stageButton4);
        stageButton5 = findViewById(R.id.stageButton5);
        stageButton6 = findViewById(R.id.stageButton6);
        stageButton7 = findViewById(R.id.stageButton7);
        stageButton8 = findViewById(R.id.stageButton8);
        stageButton9 = findViewById(R.id.stageButton9);
        stageButton10 = findViewById(R.id.stageButton10);

        stageButtons = new ArrayList<>();
        stageButtons.add(stageButton1);
        stageButtons.add(stageButton2);
        stageButtons.add(stageButton3);
        stageButtons.add(stageButton4);
        stageButtons.add(stageButton5);
        stageButtons.add(stageButton6);
        stageButtons.add(stageButton7);
        stageButtons.add(stageButton8);
        stageButtons.add(stageButton9);
        stageButtons.add(stageButton10);

        for (int i = 0; i < stageButtons.size(); i++) {
            final int stageNumber = i + 1;
            Button stageButton = stageButtons.get(i);
            stageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StageActivity.this, QuizActivity.class);
                    intent.putExtra("stageNumber", stageNumber);
                    startActivity(intent);
                }
            });
        }

        // 현재 스테이지 표시하기
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        scaleAnimation = AnimationUtils.loadAnimation(StageActivity.this, R.anim.stage_button_scale);
        stageNumber = sharedPreferences.getInt("stageNumber", 1);
        currentStageButton = stageButtons.get(stageNumber - 1);
        currentStageButton.startAnimation(scaleAnimation);

        // 다음 스테이지 비활성화
        for (int i = stageNumber; i < stageButtons.size(); i++) {
            Button stageButton = stageButtons.get(i);
            stageButton.setClickable(false);
            stageButton.setAlpha(0.5f);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 이전 스테이지 애니메이션 삭제하기
        currentStageButton.clearAnimation();

        // 현재 스테이지 애니메이션 표시
        stageNumber = sharedPreferences.getInt("stageNumber", 1);
        currentStageButton = stageButtons.get(stageNumber - 1);
        currentStageButton.startAnimation(scaleAnimation);

        // 현재 스테이지까지 버튼 활성화
        for (int i = 0; i < stageNumber; i++) {
            Button stageButton = stageButtons.get(i);
            stageButton.setClickable(true);
            stageButton.setAlpha(1.0f);
        }
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleText = toolbar.findViewById(R.id.titleText);
        Button closeButton = toolbar.findViewById(R.id.closeButton);

        titleText.setText("스테이지");
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createAdMob() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}