package com.example.hadi.coachenhancer;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
 * Created by Hadi on 1/26/2017.
 */

public class SignupFragment extends Fragment {
    //    private static final String TAG = LogoFragment.class.getSimpleName();

    Button   signupBtn;

    TextView firstNameTV;
    TextView lastNameTV;

    TextView emailTV;
    TextView passwordFirstTV;
    TextView passwordSecondTV;

    String resultsignin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        signupBtn= (Button) view.findViewById(R.id.confirmsignupbtn);

        firstNameTV = (TextView) view.findViewById(R.id.firstname_textenter);
        lastNameTV = (TextView) view.findViewById(R.id.lastname_textenter);

        emailTV = (TextView) view.findViewById(R.id.emailentersignup);

        passwordFirstTV = (TextView)  view.findViewById(R.id.passwordfirstsignup);
        passwordSecondTV = (TextView) view.findViewById(R.id.passwordsecondsignup);

        signupBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getActivity(), "Please wait...",Toast.LENGTH_LONG).show();

                signupBtn.setEnabled(false);
                // fill data on method
                String finalName =  firstNameTV.getText().toString() + " " +
                                    lastNameTV .getText().toString();
//
                String emString = emailTV.getText().toString();
//
                String p1 = passwordFirstTV .getText().toString();
                String p2 = passwordSecondTV.getText().toString();

                // Check errors
                if(!p1.equals(p2)){
                    sendError("Please enter identical password");
                    Log.e("Passes", p1 + " , " + p2);
                    return;
                }
//                String passString = passwordFirstTV.getText().toString();
                resultsignin = SignStatus(finalName, emString , p1);
                CheckSignUp(resultsignin,v);
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
//                requestURL = "http://10.0.2.2/api/v1/user/signin";
                requestURL = "http://138.68.174.195/index.php/api/v1/user/signin";

                url = new URL(requestURL);
                jsonParam.put("email", email);
                jsonParam.put("password", password);
            }else{
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
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);

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
            signupBtn.setEnabled(true);

        }
        return  response;
    }

    public void sendError(String _error){
        Log.e("SignupError","Error: " + _error);
    }
    public void CheckSignUp(String resultJSON, View view){
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
                sendError(data);
            }else if(status.equals("1") && msgmsg.equals("Success")){
                if( !data.isEmpty()){
                    GoToSignIn(view);
                }else{
                    sendError(data);
                }
            }else {
                sendError(data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void GoToSignIn(View view){
        Log.e("SuccessSignUp", "Success: you made a new user");
        MainActivity mAct = (MainActivity)getActivity();
        mAct.replaceFragmentLogin(view);
    }
}
