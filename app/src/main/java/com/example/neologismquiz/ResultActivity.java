package com.example.neologismquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ResultActivity extends AppCompatActivity {
    private final int TOTAL_STAGE = 10;
    private SharedPreferences sharedPreferences;
    private Intent intent;
    private int stageNumber;
    private TextView resultText, mentText;
    private ImageView resultImage;
    private Button positiveButton, cancelButton;
    private OnBackPressedCallback onBackPressedCallback;
    private OnBackPressedDispatcher onBackPressedDispatcher;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = findViewById(R.id.resultText);
        mentText = findViewById(R.id.mentText);
        resultImage = findViewById(R.id.resultImage);
        positiveButton = findViewById(R.id.positiveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // 광고
        createAdMob();

        // 현재 스테이지 번호 가져오기
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        stageNumber = sharedPreferences.getInt("stageNumber", 1);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stageNumber = sharedPreferences.getInt("stageNumber", 1);

                intent = new Intent(ResultActivity.this, QuizActivity.class);
                intent.putExtra("stageNumber", stageNumber);
                startActivity(intent);
                finish();
            }
        });

        // 현재 스테이지 점수 가져오기
        intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        resultText.append(score + "점");
        if (score == ScoreType.ZERO.getValue()) {
            resultImage.setImageResource(R.drawable.boss);
            mentText.append("껄껄껄 부장님!!!");
        } else if (score == ScoreType.TWENTY.getValue()) {
            resultImage.setImageResource(R.drawable.second);
            mentText.append("껄껄껄 팀장님!!!");
        } else if (score == ScoreType.FORTY.getValue()) {
            resultImage.setImageResource(R.drawable.men);
            mentText.append("껄껄껄 과장님!!!");
        } else if (score == ScoreType.SIXTY.getValue()) {
            resultImage.setImageResource(R.drawable.women);
            mentText.append("껄껄껄 선배님!!!");
        } else if (score == ScoreType.EIGHTY.getValue()) {
            resultImage.setImageResource(R.drawable.children);
            mentText.append("껄껄껄 친구야!!!");
        } else if (score == ScoreType.HUNDRED.getValue()) {
            if (stageNumber == TOTAL_STAGE) {
                cancelButton.setVisibility(View.INVISIBLE);
            } else {
                // 다음 스테이지 번호 저장
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("stageNumber", stageNumber + 1);
                editor.commit();

                cancelButton.setText("다음으룡");
            }
            resultImage.setImageResource(R.drawable.pixel_dragon);
            mentText.append("신조어 마스터???");
        }

        // 애니메이션
        Animation tingtingAnimation = AnimationUtils.loadAnimation(this, R.anim.tingting);
        resultImage.startAnimation(tingtingAnimation);
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