package com.example.hadi.coachenhancer;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Hadi on 2/3/2017.
 */

public class ConnectivityChecker {
    Context context;

    public ConnectivityChecker (Context context){
        this.context = context;
    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                                                   context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null){
                if(networkInfo.getState()== NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }

        return false;
    }
}
