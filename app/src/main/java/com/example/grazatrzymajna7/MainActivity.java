package com.example.grazatrzymajna7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.os.Looper;

public class MainActivity extends AppCompatActivity {
    Button stopButton;
    Button startButton;
    TextView numberDisplay;
    TextView trafieniaDisplay;
    TextView alert;

    int changeTime = 500;

    boolean gameInProgress = false;
    int liczbaTrafien = 0;

    void CheckForWin(){
        stopButton.setEnabled(false);

        int currentNum = Integer.parseInt(numberDisplay.getText().toString());
        if (currentNum == 7){
            liczbaTrafien += 1;
            changeTime -= changeTime - 50 >= 200 ? 50 : 0;
            trafieniaDisplay.setText("Trafienia: " + liczbaTrafien);
            alert.setText("Trafienie!");
        }else{
            alert.setText("Pudło! Zatrzymano na " + currentNum);
        }

        alert.setVisibility(View.VISIBLE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            alert.setVisibility(View.INVISIBLE);
        }, 1000);

        gameInProgress = false;
        startButton.setEnabled(true);
    }

    void StartGame(){
        gameInProgress = true;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        stopButton = findViewById(R.id.stopButton);
        startButton = findViewById(R.id.startButton);
        numberDisplay = findViewById(R.id.numberDisplay);
        trafieniaDisplay = findViewById(R.id.trafieniaDisplay);
        alert = findViewById(R.id.alert);

        stopButton.setOnClickListener(v -> CheckForWin());
        startButton.setOnClickListener(v -> StartGame());

        final Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (gameInProgress){
                    int currentNum = Integer.parseInt(numberDisplay.getText().toString());
                    int nextNum = currentNum + 1 <= 9 ? currentNum + 1 : 0;
                    numberDisplay.setText(String.valueOf(nextNum));
                }
                handler.postDelayed(this, changeTime);
            }
        };

        handler.postDelayed(runnable, changeTime);
    }
}