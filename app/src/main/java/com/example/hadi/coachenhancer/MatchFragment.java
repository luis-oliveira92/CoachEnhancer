package com.example.hadi.coachenhancer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hadi on 1/26/2017.
 */

public class MatchFragment extends Fragment {
    //    private static final String TAG = LogoFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match,container,false);
        return view;
    }
}
