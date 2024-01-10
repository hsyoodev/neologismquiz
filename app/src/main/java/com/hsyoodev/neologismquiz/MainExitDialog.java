package com.hsyoodev.neologismquiz;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MainExitDialog extends Dialog {
    private final Activity activity;
    private Window window;
    private Button positiveButton, cancelButton;
    private TextView messageText;

    public MainExitDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        // 커스텀 대화상자 윈도우 배경색 지정
        window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        positiveButton = findViewById(R.id.positiveButton);
        cancelButton = findViewById(R.id.cancelButton);
        messageText = findViewById(R.id.messageText);

        positiveButton.setText("취소다룡");
        cancelButton.setText("종료다룡");
        messageText.setText("앱을 종료할거다룡?");

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
