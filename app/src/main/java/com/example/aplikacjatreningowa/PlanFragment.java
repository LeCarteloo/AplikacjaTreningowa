package com.example.aplikacjatreningowa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlanFragment extends Fragment {
    TextView abs,pushUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan,container,false);

        abs = (TextView) v.findViewById(R.id.absText);
        pushUp = (TextView) v.findViewById(R.id.pushUpText);



        abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbsFragment nextFragment = new AbsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,nextFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        pushUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChestFragment nextFragment = new ChestFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,nextFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }
}
