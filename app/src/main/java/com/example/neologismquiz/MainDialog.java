package com.example.neologismquiz;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MainDialog extends Dialog {
    private final Activity activity;
    private Button positiveButton, cancelButton;
    private TextView message;

    public MainDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        positiveButton = findViewById(R.id.positiveButton);
        cancelButton = findViewById(R.id.cancelButton);
        message = findViewById(R.id.message);

        positiveButton.setText("취소다룡");
        cancelButton.setText("종료다룡");
        message.setText("앱을 종료할거다룡?");

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
