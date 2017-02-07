package com.example.hadi.coachenhancer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Hadi on 1/26/2017.
 */

public class ManagerFragment extends Fragment {
//    private static final String TAG = LogoFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manager,container,false);
        ImageView nextActivity = (ImageView) view.findViewById(R.id.editorivteam);
        nextActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getActivity(),EditActvitiy.class);
                startActivity(intent);
//                Log.d("Error","I am here!!");
            }});
        return view;
    }


}
