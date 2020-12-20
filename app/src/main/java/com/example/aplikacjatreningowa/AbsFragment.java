package com.example.aplikacjatreningowa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AbsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_abs,container,false);

        Button btn =  v.findViewById(R.id.startWorkout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartWorkout startWorkout = new StartWorkout();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,startWorkout, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }

}
