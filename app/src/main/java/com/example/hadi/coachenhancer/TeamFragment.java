package com.example.hadi.coachenhancer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hadi.coachenhancer.R.layout.fragment_team;

/**
 * Created by Hadi on 1/26/2017.
 */

public class TeamFragment extends Fragment {
    Button addNewTeamBtn;
    TextView addNewTeamName;

    LinearLayout linearLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(fragment_team,container,false);
        addNewTeamBtn = (Button) view.findViewById(R.id.addteamic);
        linearLayout = (LinearLayout) view.findViewById(R.id.listteamview);
        addNewTeamName = (TextView) view.findViewById(R.id.teamnameedit);

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addNewTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addNewTeamName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "At least enter on character ...",Toast.LENGTH_SHORT).show();
                    return;
                }
                Button btn = new Button(getActivity());
                btn.setText(addNewTeamName.getText().toString());
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(btn);
            }
        });


    }
}
