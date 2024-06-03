package com.example.trigo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragmentt extends Fragment {
    private static final String ARG_POSITION = "position";

    public static Fragmentt newInstance(int position) {
        Fragmentt fragment = new Fragmentt();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int position = getArguments().getInt(ARG_POSITION);

        int layoutId;
        switch (position) {
            case 1:
                layoutId = R.layout.sin;
                break;
            case 2:
                layoutId = R.layout.cos;
                break;
            case 3:
                layoutId = R.layout.tg;
                break;
            case 4:
                layoutId = R.layout.ctg;
                break;
            case 5:
                layoutId = R.layout.generalitati;
                break;
            default:
                layoutId = R.layout.generalitati;
                break;
        }
        View rootView = inflater.inflate(layoutId, container, false);
        return rootView;
    }
}
