package com.example.sufian.livelocal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class TrackStatsActivity extends AppCompatActivity {

    private TextView trackStatsFirstPart;
    private TextView trackStatsData;
    private TextView trackStatsLastPart;
    private ShareButton shareButton;
    private ShareDialog shareDialog;
    private String token;
    private SessionManager session;
    private String sid;
    private static int statData;
    private Button trackBtn;
    private TextView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_stats);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        sid = session.getUserDetails();

        trackStatsFirstPart = (TextView) findViewById((R.id.textViewMessageFirstPart));
        trackStatsData = (TextView) findViewById((R.id.textViewMessageData));
        trackStatsLastPart = (TextView) findViewById((R.id.textViewMessageLastPart));

        //shareButton = (ShareButton) findViewById(R.id.share_btnTwo);

        //shareDialog = (ShareDialog) findViewById(R.id.share_btnTwo);
        /*
        shareDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                shareDialog = new ShareDialog(TrackStatsActivity.this);
                ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                        .putString("og:type", "books.book")
                        .putString("og:title", "A Game of Thrones")
                        .putString("og:description","In the frozen wastes to the north of Winterfell, sinister and supernatural forces are mustering.")
                        .putString("books:isbn", "0-553-57340-3").build();

                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("books.reads").putObject("book", object)
                        .build();
                ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                        .setPreviewPropertyName("book")
                        .setAction(action)
                        .build();
                shareDialog.show(content);

            }
            }
        });*/


        try {
            //gets the token
            token = WebAPICommunication.getToken();
            String apiMethod = "ct/dashboard";
            JSONObject tokenObj = new getStatRequest().execute("http://www.buyctgrown.com/api/" + apiMethod).get();
            JSONObject dataObj = tokenObj.getJSONObject("sidebar_data");
            JSONObject statOjb = dataObj.getJSONObject("first_row");
            String stat = statOjb.getString("data");
            String trimNumber = stat.substring(1);
            //Toast.makeText(getApplicationContext(), "Trim: " + trimNumber, Toast.LENGTH_SHORT).show();
            statData = Integer.parseInt(trimNumber);
            trackStatsFirstPart.setText("Congratulations! You have spent\n");
            trackStatsData.setText(stat);
            trackStatsLastPart.setText("on Connecticut Grown products since you took the pledge.");

        } catch (ExecutionException e1) {
            System.out.println("ExecutionException");
        } catch (InterruptedException e2) {
            System.out.println("InterruptedException");
        } catch (JSONException e3) {
            System.out.println("JSONException");
        }

        trackBtn = (Button) findViewById(R.id.trackBtn);
        trackBtn.setOnClickListener(trackButtonClickListener);

        backbtn = (TextView) findViewById(R.id.trackStatsBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }


    private View.OnClickListener trackButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(), TrackingCalculatorActivity.class);
            startActivity(intent);
        }
    };

    public static int getStatData(){
        return statData;
    }

    class getStatRequest extends AsyncTask<String, Void, JSONObject> {
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
