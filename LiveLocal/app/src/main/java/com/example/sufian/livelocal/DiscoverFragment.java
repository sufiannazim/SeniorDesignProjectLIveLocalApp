package com.example.sufian.livelocal;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DiscoverFragment extends Fragment {
    private List<String> menu;
    TextView STTtxtView;
    private String token;
    private String topten;
    private TextView seasonTopTen;
    private TextView event;
    private TextView trails;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        seasonTopTen = (TextView) rootView.findViewById((R.id.textViewSeasonTopTen));
        seasonTopTen.setOnClickListener(seasonTopTenButtonClickListener);

        event = (TextView) rootView.findViewById((R.id.textViewEvent));
        event.setOnClickListener(eventButtonClickListener);
        event.setText(Html.fromHtml("<font color='#e64a19'>Events</font>"));
        event.append("\n");
        event.append(Html.fromHtml("<font color='#795548'>Upcoming Events</font>"));

        trails = (TextView) rootView.findViewById((R.id.textViewTrails));
        trails.setOnClickListener(trailsButtonClickListener);
        trails.setText(Html.fromHtml("<font color='#e64a19'>Trails</font>"));
        trails.append("\n");
        String trailsStr = "Connecticut is brimming with amazing places to experience local food and agriculture. For all of you who seek adventure, here is a compiled list of trails based on Connecticut's diverse and exciting food culture and agricultural history.";
        trails.append(Html.fromHtml("<font color='#795548'>"+trailsStr+"</font>"));

        try {
            JSONObject tokenObj = new TokenRequest().execute("http://www.buyctgrown.com/api/system/get_token").get();
            token = tokenObj.getString("token").toString();

            JSONObject seasonsObj = new SeasonRequest().execute("http://www.buyctgrown.com/api/node/top_10_item/list").get();
            JSONArray top10Array = seasonsObj.getJSONArray("nodes");
            JSONObject top10Obj = top10Array.getJSONObject(0);

            String seasonsTop10Body = top10Obj.getString("field_top_10_teaser");
            String temp = Html.fromHtml(seasonsTop10Body).toString();
            topten = "\n" + temp;

        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
        seasonTopTen.setText(Html.fromHtml("<font color='#e64a19'>Season's Top Ten</font>"));
        seasonTopTen.append("\n");
        seasonTopTen.append(Html.fromHtml("<font color='#795548'>"+ topten +"</font>"));

        return rootView;
    }

    private View.OnClickListener seasonTopTenButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SeasonsTop10.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener eventButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), EventActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener trailsButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    };

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
