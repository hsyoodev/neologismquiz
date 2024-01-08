package com.example.neologismquiz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class QuizCloseDialog extends Dialog {
    private final Activity activity;
    private Button positiveButton, cancelButton;
    private TextView message;

    public QuizCloseDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 현재 스테이지 번호 가져오기
        Intent intent = activity.getIntent();
        int stageNumber = intent.getIntExtra("stageNumber", 1);

        positiveButton = findViewById(R.id.positiveButton);
        cancelButton = findViewById(R.id.cancelButton);
        message = findViewById(R.id.messageText);

        positiveButton.setText("계속할룡");
        cancelButton.setText("포기할룡");
        message.setText("Stage " + stageNumber + " 을 포기할거다룡?");
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                activity.finish();
            }
        });
    }
}
