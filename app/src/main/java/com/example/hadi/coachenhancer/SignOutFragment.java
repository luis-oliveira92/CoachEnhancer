package com.example.hadi.coachenhancer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Hadi on 2/2/2017.
 */

public class SignOutFragment extends Fragment{
    Button signoutBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signout,container,false);

        signoutBtn= (Button) view.findViewById(R.id.signoutbtnfragment);
        signoutBtn.setEnabled(true);

        signoutBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                SignOutNow(view);
            }
        });

        return view;
    }

    public void SignOutNow(View view){
        Log.e("SuccessSignout", "Success: you signout");

        MainActivity mAct = (MainActivity)getActivity();
        mAct.replaceFragmentLogin(view);
        MainActivity.isSignedIn =false;
        mAct.SetButtonsActivate(false);

        Intent i = getActivity().getPackageManager()
                .getLaunchIntentForPackage( getActivity().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
