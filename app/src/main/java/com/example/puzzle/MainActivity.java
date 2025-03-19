package com.example.puzzle;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> sonlar = new ArrayList<>();

    Button[][] buttons = new Button[4][4];
    RelativeLayout relativeLayout;
    AlertDialog.Builder builder;

    TextView step, showTime;
    Timer timer;
    int time = 0;
    int count_step = 0;
    int winner = 0;

    int emptyI = 3;
    int emptyJ = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        setContentView(R.layout.activity_main);
        step = findViewById(R.id.show_step);
        showTime = findViewById(R.id.show_time);

        loadNumbers();
        loadButtons();
        loadDataToButtons();
    }
    public void loadNumbers(){
        for (int i=1; i<=15; i++){
            sonlar.add(i);
        }
        Collections.shuffle(sonlar);
        Log.d("test", "loadNumbers: "+sonlar);
    }

    public void loadButtons(){
        relativeLayout = findViewById(R.id.relative);
        for (int i=0; i<relativeLayout.getChildCount(); i++){
            buttons[i/4][i%4] = (Button) relativeLayout.getChildAt(i);
        }
    }

    public void loadDataToButtons(){
        for (int i=0; i<relativeLayout.getChildCount()-1; i++){
            buttons[i/4][i%4].setText(String.valueOf(sonlar.get(i)));
            buttons[i/4][i%4].setBackgroundResource(R.drawable.active_btn);
        }
        buttons[emptyI][emptyJ].setText("");
        buttons[emptyI][emptyJ].setBackgroundResource(R.drawable.noactive_btn);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showTime.setText(countTimer(time));
                    }
                });
            }
        }, 1000, 1000);
    }

    public String countTimer(int secound){
        int hour = secound/3600;
        int minute = (secound%3600)/60;
        int second = secound%60;

        @SuppressLint("DefaultLocale") String timeFormat = String.format("%02d:%02d:%02d", hour, minute, second);
        return timeFormat;
    }

    public void onClickBtn(View view) {
        int  pressedI = Integer.parseInt(view.getTag().toString())/10;
        int  pressedJ = Integer.parseInt(view.getTag().toString())%10;
        String getPressedText = (String) buttons[pressedI][pressedJ].getText();

        if ((Math.abs(pressedI-emptyI)==0 && Math.abs(pressedJ-emptyJ)==1) || (Math.abs(pressedI-emptyI)==1 && Math.abs(pressedJ-emptyJ)==0)){
            buttons[emptyI][emptyJ].setText(getPressedText);
            buttons[emptyI][emptyJ].setBackgroundResource(R.drawable.active_btn);
            buttons[pressedI][pressedJ].setText("");
            buttons[pressedI][pressedJ].setBackgroundResource(R.drawable.noactive_btn);

            emptyI = pressedI;
            emptyJ = pressedJ;

            count_step++;
            step.setText(String.valueOf(count_step));

            if (emptyI == 3 && emptyJ == 3){
                for (int i=0; i<15; i++){
                    if (buttons[i/4][i%4].getText().toString().equals((i+1)+"")){
                       winner++;
                    }
                }
                Log.d("test", "onClickBtn: "+winner);
                
                if (winner == 15){
                    String time = showTime.getText().toString();
                    String step = this.step.getText().toString();

                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("time", time);
                    intent.putExtra("step", step);
                    startActivity(intent);
                }
                winner = 0;
            }
        }
    }

    public void restartGame(View view) {
        time = 0;
        timer.cancel();
        count_step = 0;
        step.setText(String.valueOf(count_step));
        emptyI = 3;
        emptyJ = 3;
        sonlar.clear();
        loadNumbers();
        loadButtons();
        loadDataToButtons();
    }

    public void exitGame(View view) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to end the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}