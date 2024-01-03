package com.example.neologismquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StageActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.title);

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
    }
}