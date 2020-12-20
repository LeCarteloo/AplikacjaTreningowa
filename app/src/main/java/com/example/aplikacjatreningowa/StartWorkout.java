package com.example.aplikacjatreningowa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import pl.droidsonroids.gif.GifImageView;

public class StartWorkout extends Fragment {

    GifImageView workoutGif;
    TextView nameOfExercise,descriptionOfExercise,timer;
    private static final long START_TIME_IN_MILLIS = 50000;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_startworkout, container, false);

        nameOfExercise = v.findViewById(R.id.nameOfExercise);
        descriptionOfExercise = v.findViewById(R.id.descriptionOfExercise);
        timer = v.findViewById(R.id.timer);
        workoutGif = v.findViewById(R.id.workoutGif);

        startTimer();

        return v;
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                StyleableToast.makeText(getActivity(),"Czas upłynał",R.style.toastBlue).show();
            }
        }.start();
        isTimerRunning = true;
    }

    private void updateCountDownText(){
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeToUpdate = "0" + String.valueOf(minutes) + ":" + String.valueOf(seconds);

        timer.setText(timeToUpdate);
    }

}