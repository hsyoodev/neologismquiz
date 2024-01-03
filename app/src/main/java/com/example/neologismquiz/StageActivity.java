package com.example.neologismquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class StageActivity extends AppCompatActivity {
    Button stage1, stage2, stage3, stage4, stage5, stage6, stage7, stage8, stage9, stage10;
    List<Button> stages = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);

        title.setText("스테이지");
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.close) {
                    intent = new Intent(StageActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        // stage
        stage1 = findViewById(R.id.stage1);
        stage2 = findViewById(R.id.stage2);
        stage3 = findViewById(R.id.stage3);
        stage4 = findViewById(R.id.stage4);
        stage5 = findViewById(R.id.stage5);
        stage6 = findViewById(R.id.stage6);
        stage7 = findViewById(R.id.stage7);
        stage8 = findViewById(R.id.stage8);
        stage9 = findViewById(R.id.stage9);
        stage10 = findViewById(R.id.stage10);

        stages.add(stage1);
        stages.add(stage2);
        stages.add(stage3);
        stages.add(stage4);
        stages.add(stage5);
        stages.add(stage6);
        stages.add(stage7);
        stages.add(stage8);
        stages.add(stage9);
        stages.add(stage10);

        for (int i = 0; i < stages.size(); i++) {
            final int stageNumber = i + 1;
            Button stage = stages.get(i);
            stage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(StageActivity.this, QuizActivity.class);
                    intent.putExtra("stageNumber", stageNumber);
                    startActivity(intent);
                }
            });
        }

        // 현재 스테이지
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        int stageNumber = sharedPreferences.getInt("stageNumber", 1);
        Animation scaleAnimation = AnimationUtils.loadAnimation(StageActivity.this, R.anim.scale);
        Button currentStage = stages.get(stageNumber - 1);
        currentStage.startAnimation(scaleAnimation);

        // 다음 스테이지는 진행 불가
        for (int i = stageNumber; i < stages.size(); i++) {
            Button stage = stages.get(i);
            stage.setClickable(false);
            stage.setAlpha(0.5f);
        }
    }
}