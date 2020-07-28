package com.example.timerdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

    public String secondsToString(int pTime) {
        String time = String.format("%02d:%02d", pTime / 60, pTime % 60);
        return time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SeekBar seekbar = findViewById(R.id.seekBar);
        final TextView textView = findViewById(R.id.counterTextView);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.partyhorn);
        final Button button = findViewById(R.id.startStopButton);
        final int maxTime = 300; //5minutes
        seekbar.setMax(maxTime);
        seekbar.setProgress(maxTime); //set default progress to max time
        textView.setText(secondsToString(maxTime));
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String minutes = secondsToString(seekBar.getProgress());
                textView.setText(minutes);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                button.setFocusable(View.NOT_FOCUSABLE);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                button.setFocusable(View.FOCUSABLE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = seekbar.getProgress();
                CountDownTimer timer = new CountDownTimer(progress*1000,1000) {
                    @Override
                    public void onTick(long msUntilEnd) {
                        int secondsLeft = (int)msUntilEnd/1000;
                        seekbar.setProgress(secondsLeft);
                    }

                    @Override
                    public void onFinish() {
                        mediaPlayer.start();
                    }
                };
                timer.start();
            }
        });
//        final Handler handle = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                Log.i("info","A second passed");
//                handle.postDelayed(this,1000);
//            }
//        };
//        handle.post(run);
    }


}