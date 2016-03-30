package com.example.sufian.livelocal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.WindowManager;
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
    private Boolean cond = false;

    public TrackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        session = new SessionManager(getContext());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //HashMap hashMap = session.getUserDetails();
        sid = session.getUserDetails();
        amount = (EditText) view.findViewById(R.id.editTextAmount);
        submitbtn = (Button) view.findViewById(R.id.submitBtn);
        submitbtn.setOnClickListener(mButtonClickListener);

        if (!session.isLoggedIn()) {
            amount.setEnabled(false);
            submitbtn.setEnabled(false);
        } else {
            amount.setEnabled(true);
            submitbtn.setEnabled(true);
        }
        return view;
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                amountSpent = Integer.parseInt(amount.getText().toString());
                cond = true;
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException");
            }
            if (cond == true) {
                try {
                    //amountSpent = Integer.parseInt(amount.getText().toString());
                    token = WebAPICommunication.getToken();
                    String apiMethodTwo = "ct/report/answer";
                    JSONObject tokenObjTwo = new submitAmountRequest().execute("http://www.buyctgrown.com/api/" + apiMethodTwo).get();
                    String requestStatusTwo = tokenObjTwo.getString("status");

                    if (requestStatusTwo.contains("ok")) {
                        //Toast.makeText(getContext(), "Request Sucessfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), TrackStatsActivity.class);
                        startActivity(intent);
                    }
                } catch (ExecutionException e1) {
                    System.out.println("ExecutionException");
                } catch (InterruptedException e2) {
                    System.out.println("InterruptedException");
                } catch (JSONException e3) {
                    System.out.println("JSONException");
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Error");
                builder.setMessage("Please enter the amount you spent on local groceries this week!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    }
                });
                alert.show();
            }
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
                    parameters.put("amount", amountSpent);
                    parameters.put("sources", "");

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

        class getQuestions extends AsyncTask<String, Void, JSONObject> {
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

