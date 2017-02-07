package com.example.hadi.coachenhancer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public FragmentManager manager;
    public static boolean isSignedIn = false;
    Button teamBtn,manageBtn,matchBtn,chatBtn,settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        boolean message = intent.getBooleanExtra("isSignedIn",false);
        SetButtonsActivate(message);
        manager = getFragmentManager();

        (new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        Thread.sleep(1000);
                        Log.d("SleepTime", "local Thread sleeping");
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {

                            @Override
                            public void run()
                            {
                                CheckConnectivity(); // this action have to be in UI thread
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }finally {
                        CheckConnectivity();
                    }
            }
        })).start();


        // Set logo as a default page
        LogoFragment logoFragment = new LogoFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,logoFragment,"logoFragment");
        transaction.commit();
    }

    public void SetButtonsActivate(boolean _bool){

        teamBtn = (Button) findViewById(R.id.teambtnmain);
        teamBtn.setEnabled(_bool);

        manageBtn = (Button) findViewById(R.id.managebtnmain);
        manageBtn.setEnabled(_bool);

        matchBtn = (Button) findViewById(R.id.matchbtnmain);
        matchBtn.setEnabled(_bool);

        chatBtn = (Button) findViewById(R.id.chatbtnmain);
        chatBtn.setEnabled(_bool);

        settingsBtn = (Button) findViewById(R.id.settingsbtnmain);
        settingsBtn.setEnabled(_bool);
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
        if(!isSignedIn){
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.sinoutontainer,loginFragment,"loginFragment");
            transaction.commit();
        }
        else{
            Log.e("Sign","singout executed");
            replaceFragmentSignOut(view);
        }
    }

    public void replaceFragmentSignOut(View view){
        SignOutFragment signOutFragment = new SignOutFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.sinoutontainer,signOutFragment,"signOutFragment");
        transaction.commit();
        isSignedIn =true;
    }

    public void CheckConnectivity(){

        if(!isSignedIn)
            return;
        SignOutFragment fragment = (SignOutFragment)manager.findFragmentByTag("signOutFragment");

        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null){
                if(networkInfo.getState()== NetworkInfo.State.CONNECTED){
                    return;
                }
            }else{
                fragment.SignOutNow(fragment.getView());
            }
        }
        fragment.SignOutNow(fragment.getView());
    }
}
