package com.example.hadi.coachenhancer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
    }

    public void replaceFragmentLogo(View view){
        LogoFragment logoFragment = new LogoFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,logoFragment,"logoFragment");
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceFragmentTeam(View view){
        TeamFragment teamFragment = new TeamFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,teamFragment,"teamFragment");
        transaction.commit();
    }

    public void replaceFragmentManager(View view){
        ManagerFragment managerFragment = new ManagerFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,managerFragment,"managerFragment");
        transaction.commit();
    }

    public void replaceFragmentMatch(View view) {
        MatchFragment matchFragment = new MatchFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,matchFragment,"matchFragment");
        transaction.commit();
    }

    public void replaceFragmentChat(View view) {
        ChatFragment chatFragment = new ChatFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,chatFragment,"chatFragment");
        transaction.commit();
    }

    public void replaceFragmentSetting(View view) {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,settingsFragment,"settingsFragment");
        transaction.commit();
    }

    public void replaceFragmentSignup(View view){
        SignupFragment signupFragment = new SignupFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.sinoutontainer,signupFragment,"signupFragment");
        transaction.commit();
    }

    public void replaceFragmentLogin(View view){
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.sinoutontainer,loginFragment,"loginFragment");
        transaction.commit();
    }
}
