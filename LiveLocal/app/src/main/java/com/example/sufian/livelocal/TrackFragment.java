package com.example.sufian.livelocal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class TrackFragment extends Fragment {

    SessionManager session;
    private Button submitbtn;
    private EditText amount;
    private int amountSpent;
    protected String token;
    private String requestStatus;
    private String sid;
    private TextView options;

    public TrackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        session = new SessionManager(getContext());
        //HashMap hashMap = session.getUserDetails();
        sid = session.getUserDetails();
        options = (TextView) view.findViewById(R.id.textViewOptions);
        options.setOnClickListener(optionsClickListener);
        amount = (EditText) view.findViewById(R.id.editTextAmount);
        submitbtn = (Button) view.findViewById(R.id.submitBtn);
        submitbtn.setOnClickListener(mButtonClickListener);

        if(!session.isLoggedIn()){
            amount.setEnabled(false);
            submitbtn.setEnabled(false);
        } else {
            amount.setEnabled(true);
            submitbtn.setEnabled(true);
        }


        return view;
    }

    private View.OnClickListener optionsClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getContext(), QuestionOptionsActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getContext(), TrackStatsActivity.class);
            startActivity(intent);
            /*
            try {
                amountSpent = Integer.parseInt(amount.getText().toString());
                //gets the token
                token = WebAPICommunication.getToken();

                String apiMethod = "ct/report/answer";
                JSONObject tokenObj = new submitAmountRequest().execute("http://www.buyctgrown.com/api/" + apiMethod).get();
                requestStatus = tokenObj.getString("status");

            } catch (ExecutionException e1) {
                Toast.makeText(getContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
                System.out.println("ExecutionException");
            } catch (InterruptedException e2) {
                Toast.makeText(getContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
                System.out.println("InterruptedException");
            } catch (JSONException e3) {
                Toast.makeText(getContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
                System.out.println("JSONException");
            }
            if (requestStatus.equals("ok") ){
                Toast.makeText(getContext(), "Request Sucessfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), TrackStatsActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
            }*/
        }
    };

    class submitAmountRequest extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;

        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                JSONObject parameters = new JSONObject();
                parameters.put("token", token);
                parameters.put("sid", sid);
                parameters.put("source_local_this_week", "Y");
                parameters.put("source_local_farms", "test");
                parameters.put("purchase_this_week", amountSpent);
                parameters.put("source_local_why_not", "SUPPLY");

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

                wr.writeBytes(parameters.toString());
                wr.flush();
                wr.close();

                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    result.append(line);
                }

                JSONObject obj = new JSONObject(result.toString());
                return obj;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
    }
}
