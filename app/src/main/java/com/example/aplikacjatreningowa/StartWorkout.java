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
import android.widget.ImageView;
import android.widget.TextView;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class StartWorkout extends Fragment {

    GifImageView workoutGif;
    TextView nameOfExercise,descriptionOfExercise,timer;
    private long START_TIME_IN_MILLIS = 35000;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private String[] exercisesAbs= {"Leg Raises","Russian Twist","Plank"};
    private String[] descriptionAbs = {"Półóż sie na plecach, umieść ręce na wysokości bioder dla większej stabilności. Następnie unieś wyprostowane nogi z podłogi do pionu. Powoli opuść nogi i powtórz ćwiczenie."
            ,"Usiądź na podłodze z ugiętymi kolanami, jak do brzuszków. Stopy powinny byc razem a ręce wyprostowane i złączone. Te ćwiczenie wzmacnia mięsnie brzucha dzięki skretowi mięsni."
            ,"W pozycji wyjsciowej do pompki, ciężarem ciała opartym na przedramionach łokciach i palcach stóp. Wzmacnia mięsnie brzucha, plecy i ramiona."};
    private static final long[] timeOfAbs = {35000,45000,65000};
    private int i = -1;
    private int[] gifListAbs = new int[]{R.drawable.legraises,R.drawable.russiantwist,R.drawable.plank};

    private String[] exercisesChest= {"Diamond Push Up","Pull Up","Bench Press"};
    private String[] descriptionChest = {"Dłonie należy ustawić nieco szerzej niż linia obręczy barkowej. Palce w klasycznej wersji powinny być skierowane równolegle do ciała, stopy złączone."
            ,"W zwisie na drążku (drążek trzymany nachwytem), ramiona szeroko rozstawione, bierzemy wdech i podciągamy się w taki sposób, aby górna część klatki znalazła się prawie na wysokości drążka."
            ,"Utrzymując prawidłową pozycję wyjściową, wykonaj wdech i powolnym ruchem opuść sztangę do środkowej części klatki piersiowej, uginając ramiona w łokciach."};
    private static final long[] timeOfChest = {35000,90000,60000};
    private int[] gifListChest = new int[]{R.drawable.diamondpushup,R.drawable.pullup,R.drawable.chest};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_startworkout, container, false);
        nameOfExercise = v.findViewById(R.id.nameOfExercise);
        descriptionOfExercise = v.findViewById(R.id.descriptionOfExercise);
        timer = v.findViewById(R.id.timer);
        workoutGif = v.findViewById(R.id.workoutGif);

        String selectedButton = getArguments().getString("exercise");

        if(selectedButton.equals("abs")) {
            i = -1;
            workoutGif.setImageResource(R.drawable.crunches);
            startTimerAbs();
        }
        else
        {
            i=0;
            nameOfExercise.setText(exercisesChest[0]);
            descriptionOfExercise.setText(descriptionChest[0]);
            workoutGif.setImageResource(gifListChest[0]);
            startTimerChest();
        }

        return v;
    }

    private void startTimerAbs(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                i++;
                if(i==3) {
                    i=-1;
                    StyleableToast.makeText(getActivity(),"Ćwiczenie ukończone",R.style.toastBlue).show();
                    PlanFragment planFragment = new PlanFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,planFragment, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    countDownTimer.cancel();
                }
                else{
                    nameOfExercise.setText(exercisesAbs[i]);
                    descriptionOfExercise.setText(descriptionAbs[i]);
                    workoutGif.setImageResource(gifListAbs[i]);
                    timeLeftInMillis = timeOfAbs[i];
                    timer.setText("00:00");
                    startTimerAbs();
                }
            }

        }.start();
    }

    private void startTimerChest(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                i++;
                if(i==3) {
                    StyleableToast.makeText(getActivity(),"Ćwiczenie ukończone",R.style.toastBlue).show();
                    PlanFragment planFragment = new PlanFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,planFragment, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    countDownTimer.cancel();
                }
                else{
                    nameOfExercise.setText(exercisesChest[i]);
                    descriptionOfExercise.setText(descriptionChest[i]);
                    workoutGif.setImageResource(gifListChest[i]);
                    timeLeftInMillis = timeOfChest[i];
                    timer.setText("00:00");
                    startTimerChest();
                }
            }

        }.start();
    }

    private void updateCountDownText(){
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeToUpdate = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        timer.setText(timeToUpdate);
    }

}