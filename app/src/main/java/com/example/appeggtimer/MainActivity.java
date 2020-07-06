package com.example.appeggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView countDownTextView;
    SeekBar timerSeekBar;
    Boolean counterActive = false;
    Button timerStart;
    CountDownTimer countDownTimer;

    public void buttonClicked(View view){

        if (counterActive){
            resetTimer();

        }else{
            counterActive = true;
            timerSeekBar.setEnabled(false);
            timerSeekBar.setVisibility(View.GONE);
            timerStart.setText("Stop!");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int)millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    Log.i("Finished", "Timer all done");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airsound);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void resetTimer() {
        counterActive = false;
        timerSeekBar.setEnabled(true);
        timerSeekBar.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
        timerSeekBar.setProgress(30);
        timerStart.setText("Start!!");
    }

    public void updateTimer(int progress){
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);
        String minutesString = minutes<10?"0"+Integer.toString(minutes) : Integer.toString(minutes);
        String secondString = seconds<10?"0"+Integer.toString(seconds) : Integer.toString(seconds);
        countDownTextView.setText(minutesString + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        countDownTextView = findViewById(R.id.countDownTextView);
        timerStart = findViewById(R.id.timerStart);
        timerStart.setText("Start!!");

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setKeyProgressIncrement(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
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