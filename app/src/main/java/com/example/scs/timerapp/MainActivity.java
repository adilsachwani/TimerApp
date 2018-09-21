package com.example.scs.timerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    ImageView eggImg, controlImg;
    TextView timeText;
    CountDownTimer countDownTimer;
    Boolean timerIsActive = false;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        eggImg = (ImageView) findViewById(R.id.egg);
        controlImg = (ImageView) findViewById(R.id.control);
        timeText = (TextView) findViewById(R.id.time);

        seekBar.setMax(600);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        controlImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timerIsActive){
                    controlImg.setImageResource(R.drawable.play);
                    seekBar.setEnabled(true);
                    timeText.setText("0:00");
                    seekBar.setProgress(0);
                    countDownTimer.cancel();
                    mediaPlayer.stop();
                    timerIsActive = false;
                }
                else{
                    controlImg.setImageResource(R.drawable.stop);
                    seekBar.setEnabled(false);
                    timerIsActive = true;

                    countDownTimer = new CountDownTimer(seekBar.getProgress()*1000 + 100,1000){

                        @Override
                        public void onTick(long millisUntilFinished) {
                            setTime( ((int) millisUntilFinished)/1000 );
                        }

                        @Override
                        public void onFinish() {
                            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.hen);
                            mediaPlayer.start();
                            controlImg.setImageResource(R.drawable.play);
                            seekBar.setEnabled(true);
                            timeText.setText("0:00");
                            seekBar.setProgress(0);
                            countDownTimer.cancel();
                            timerIsActive = false;
                        }
                    }.start();
                }
            }
        });
    }

    public void setTime(int progress){
        int mins = (int) progress/60;
        int secs = (int) progress - mins*60;
        String strSecs = Integer.toString(secs);

        if(secs < 10){
            strSecs = "0" + secs;
        }

        timeText.setText(mins + ":" + strSecs);
    }

}