package com.example.hadi.coachenhancer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hadi on 1/27/2017.
 */

public class LoginFragment extends Fragment {
    Button   loginBtn;
    TextView emailTV;
    TextView passwordTV;
    String resultsignin = "Alarm!!";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_login,container,false);
        loginBtn= (Button) view.findViewById(R.id.confirmsigninbtn);
        loginBtn.setEnabled(true);

        emailTV = (TextView) view.findViewById(R.id.enteremailsignin);
        passwordTV = (TextView) view.findViewById(R.id.passwordfirst_textview);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getActivity(), "Please wait...",Toast.LENGTH_SHORT).show();
                loginBtn.setEnabled(false);
                String emString = emailTV.getText().toString();
                String passString = passwordTV.getText().toString();
                resultsignin = SignStatus( null, emString, passString);

                Log.e("FeedBack",emString +  passString);
                CheckSignIn(resultsignin,view);
            }
        });

        return view;
    }

    public String SignStatus( String name, String email, String password){
        String response = "";
        String requestURL ="";

        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;
        try {
            // Post JSON
            JSONObject jsonParam = new JSONObject();

            // POST data Either to connect or signin
            URL url;
            if(name == null){
                // Here we sign in
//                requestURL = "http://10.0.2.2/api/v1/user/signin";
                requestURL = "http://138.68.174.195/index.php/api/v1/user/signin";

                url = new URL(requestURL);
                jsonParam.put("email", email);
                jsonParam.put("password", password);
            }else{
                // Here we sign up
//                requestURL="http://10.0.2.2/api/v1/user";
                requestURL="http://138.68.174.195/index.php/api/v1/user";

                url = new URL(requestURL);
                jsonParam.put("name", name);
                jsonParam.put("email", email);
                jsonParam.put("password", password);
            }
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(1500);
            urlConnection.setConnectTimeout(1500);

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());

            writer.flush();
            writer.close();
            os.close();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            response = sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if(reader != null){
                    reader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        loginBtn.setEnabled(true);
        return  response;
    }

    private void CheckSignIn(String resultJSON,View view){
        String status = null;
        String msgmsg = null;
        String data   = null;

        try {

            JSONObject jsonParam = new JSONObject(resultJSON);

            status = jsonParam.getString("status");
            Log.d("status", status);

            msgmsg = jsonParam.getString("msg");
            Log.d("msgmsg", msgmsg);

            data   = jsonParam.getString("data");
            Log.d("data"  , data);

            if(status.equals("0") || msgmsg.equals("fail")||data.equals("fail")){
                ErrorSignIn();
            }else if(status.equals("1") && msgmsg.equals("Success")){
                if( !data.isEmpty()){
                    WelcomeSignIn(view);
                }else{
                    ErrorSignIn();
                }
            }else {
                ErrorSignIn();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            loginBtn.setEnabled(true);
        }

    }

    private void WelcomeSignIn(View view){
        Log.e("SuccessSignIn", "Success: you get true data");
        MainActivity mAct = (MainActivity)getActivity();
        mAct.SetButtonsActivate(true);
        GoToSignOut(view);
    }
    private void ErrorSignIn(){
        Log.e("ErrorSignIn", "Error: you get Wrong data");
        String result = "Error: you get Wrong data";
        Toast.makeText(getActivity(), result,Toast.LENGTH_SHORT).show();

    }

    private void GoToSignOut(View view){
        Log.e("SuccessSignIn", "Success: you connected successfully");
        MainActivity mAct = (MainActivity) getActivity();
        mAct.replaceFragmentSignOut(view);
    }

}
