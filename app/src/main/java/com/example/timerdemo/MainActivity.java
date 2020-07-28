package com.example.timerdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView textView;
    Button button;
    CountDownTimer timer;
    boolean timerIsActive = false;
    int maxTime;

    @SuppressLint("DefaultLocale")
    public String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }

    public void resetTimer(){
        timer.cancel();
        seekBar.setProgress(maxTime);
        seekBar.setEnabled(true);
        button.setText(R.string.startButton_text);
        timerIsActive=false;
    }

    public void startStopTimer(View view){
        if(timerIsActive){
            resetTimer();
        }
        else {
            seekBar.setEnabled(false);
            button.setText(R.string.stopButton_text);
            timer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long msUntilEnd) {
                    seekBar.setProgress((int) msUntilEnd / 1000);
                    textView.setText(secondsToString((int) msUntilEnd / 1000));
                }

                @Override
                public void onFinish() {
                    MediaPlayer media = MediaPlayer.create(getApplicationContext(), R.raw.partyhorn);
                    media.start();
                    resetTimer();
                }
            };
            timer.start();
            timerIsActive=true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.counterTextView);
        button = findViewById(R.id.startStopButton);
        maxTime = 300; //5min
        seekBar.setMax(maxTime);
        seekBar.setProgress(maxTime);
        textView.setText(secondsToString(maxTime));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(secondsToString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}