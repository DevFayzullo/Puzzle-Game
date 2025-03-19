package com.example.puzzle;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    TextView result_time, result_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        result_time = findViewById(R.id.result_time);
        result_step = findViewById(R.id.result_step);

        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        String step = intent.getStringExtra("step");

        result_time.setText(time);
        result_step.setText(step);
    }
}