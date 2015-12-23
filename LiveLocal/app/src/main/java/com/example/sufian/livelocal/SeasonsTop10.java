package com.example.sufian.livelocal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeasonsTop10 extends AppCompatActivity {
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons_top10);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView seasonTop10View = (TextView) findViewById(R.id.seasonsTop10View);

        try {
            JSONObject tokenObj = new TokenRequest().execute("http://www.buyctgrown.com/api/system/get_token").get();
            token = tokenObj.getString("token").toString();

            JSONObject seasonsObj = new SeasonRequest().execute("http://www.buyctgrown.com/api/node/top_10_item/list").get();
            JSONArray top10Array = seasonsObj.getJSONArray("nodes");
            JSONObject top10Obj = top10Array.getJSONObject(0);

            String seasonsTop10Body = top10Obj.getString("field_top_10_body");
            seasonTop10View.setText(Html.fromHtml("<font color='#795548'>" + seasonsTop10Body + "</font>"));
            seasonTop10View.setMovementMethod(new ScrollingMovementMethod());
            seasonTop10View.setMovementMethod(LinkMovementMethod.getInstance());
        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
    }

    class TokenRequest extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;

        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL( params[0] );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                JSONObject parameters = new JSONObject();
                parameters.put( "hash", "274ffe280ad2956ea85f35986958095d" );
                parameters.put( "seed", "10" );

                DataOutputStream wr = new DataOutputStream( connection.getOutputStream() );

                wr.writeBytes( parameters.toString() );

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
            } catch (Exception e){
                this.exception = e;
                return null;
            }
        }
    }

    class SeasonRequest extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;

        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL( params[0] );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                JSONObject parameters = new JSONObject();
                parameters.put( "token", token );

                DataOutputStream wr = new DataOutputStream( connection.getOutputStream() );

                wr.writeBytes( parameters.toString() );

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
            } catch (Exception e){
                this.exception = e;
                return null;
            }
        }
    }
}
