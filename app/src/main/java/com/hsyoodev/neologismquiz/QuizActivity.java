package com.hsyoodev.neologismquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayDeque;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private final int TOTAL_ROUND_NUMBER = 5;
    private final int SCORE_BY_ONE = 20;
    private Intent intent;
    private int stageNumber;
    private QuizCloseDialog quizCloseDialog;
    private TextView roundText, questionText, answerText;
    private Button oButton, xButton;
    private DBHelper dbHelper;
    private ArrayDeque<QuizDto> quizDtos;
    private QuizDto quizDto;
    private String randomAnswer;
    private int roundNumber = 1;
    private int score = 0;
    private OnBackPressedCallback onBackPressedCallback;
    private OnBackPressedDispatcher onBackPressedDispatcher;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        roundText = findViewById(R.id.roundText);
        questionText = findViewById(R.id.questionText);
        answerText = findViewById(R.id.answerText);
        oButton = findViewById(R.id.oButton);
        xButton = findViewById(R.id.xButton);

        // OX
        oButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOX(true);
            }
        });
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOX(false);
            }
        });

        // 현재 스테이지 번호 가져오기
        intent = getIntent();
        stageNumber = intent.getIntExtra("stageNumber", 1);

        // 광고
        createAdMob();

        // 커스텀 툴바
        createToolbar();

        // 퀴즈 생성
        dbHelper = new DBHelper(this);
        quizDtos = dbHelper.findByStageNumber(stageNumber);
        dbHelper.close();
        createQuiz();

        // 애니메이션
        startQuizAnimation();

        // 커스텀 대화상자
        quizCloseDialog = new QuizCloseDialog(this);

        // 뒤로가기
        onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                quizCloseDialog.show();
            }
        };
        onBackPressedDispatcher.addCallback(onBackPressedCallback);
    }

    private void onClickOX(boolean isO) {
        if (isAnswer(isO)) {
            score += SCORE_BY_ONE;
        }

        if (roundNumber > TOTAL_ROUND_NUMBER) {
            intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        } else {
            createQuiz();
            startQuizAnimation();
        }
    }

    private void createQuiz() {
        quizDto = quizDtos.poll();
        String[] randomAnswers = {quizDto.getAnswer(), quizDto.getWrong()};
        Random random = new Random();
        int randomIndex = random.nextInt(randomAnswers.length);
        randomAnswer = randomAnswers[randomIndex];

        roundText.setText("ROUND " + roundNumber++);
        questionText.setText(quizDto.getQuestion());
        answerText.setText(randomAnswer);
    }

    private void startQuizAnimation() {
        Animation quizTextScaleAnimation = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.quiz_text_scale);
        answerText.startAnimation(quizTextScaleAnimation);
    }

    private boolean isAnswer(boolean isO) {
        return quizDto.getAnswer().equals(randomAnswer) == isO;
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleText = toolbar.findViewById(R.id.titleText);
        Button closeButton = toolbar.findViewById(R.id.closeButton);

        titleText.setText("Stage " + stageNumber);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizCloseDialog.show();
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