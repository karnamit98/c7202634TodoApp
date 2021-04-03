package com.tbcmad.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences=getApplicationContext().getSharedPreferences("patinetpref",0);
                Boolean authentication = preferences.getBoolean("authentication",false);
                if(authentication)
                {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },6000);

        mTextView = findViewById(R.id.mTextView);

        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                long timer = millisUntilFinished / 1000;
               //mTextView.setText("" + millisUntilFinished / 1000);
                if(timer == 5) {mTextView.setText("Starting App In\n"+"*****"+timer+"*****\n"); }
                else if(timer == 4) {mTextView.setText("Starting App In\n"+"****"+timer+"****\n"); }
                else if(timer == 3) {mTextView.setText("Starting App In\n"+"***"+timer+"***\n"); }
                else if(timer == 2) {mTextView.setText("Starting App In\n"+"**"+timer+"**\n"); }
                else if(timer == 1) {mTextView.setText("Starting App In\n*"+timer+"*\n"); }
                else {mTextView.setText("Starting App..."); }


            }

            public void onFinish() {
                mTextView.setText("Starting App...");
            }

        }.start();

    }
}