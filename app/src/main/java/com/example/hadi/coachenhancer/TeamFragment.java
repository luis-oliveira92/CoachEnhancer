package com.example.hadi.coachenhancer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.hadi.coachenhancer.R.layout.fragment_team;

/**
 * Created by Hadi on 1/26/2017.
 */

public class TeamFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(fragment_team,container,false);

        return view;
    }
}
