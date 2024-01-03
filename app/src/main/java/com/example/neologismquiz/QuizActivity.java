package com.example.neologismquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class QuizActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // StageActivity 가 보내온 데이터 받기
        intent = getIntent();
        int stageNumber = intent.getIntExtra("stageNumber", 1);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);

        title.setText("Stage " + stageNumber);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.close) {
//                    intent = new Intent(QuizActivity.this, StageActivity.class);
//                    startActivity(intent);
                }
                return false;
            }
        });
    }
}