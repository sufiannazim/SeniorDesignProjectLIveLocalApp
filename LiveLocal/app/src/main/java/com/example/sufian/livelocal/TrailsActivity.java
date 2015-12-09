package com.example.sufian.livelocal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TrailsActivity extends AppCompatActivity {
    private TrailsListAdapter adapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv = (ListView) findViewById(R.id.trailListView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        ArrayList<Trail> trailsArray = new ArrayList<Trail>();
        adapter = new TrailsListAdapter(this, trailsArray);
        this.populateAdapter(adapter);
        lv.setAdapter(adapter);
    }

    public void populateAdapter( TrailsListAdapter adapter ){
        try {
            JSONObject tokenObj = new TokenRequest().execute("http://www.buyctgrown.com/api/system/get_token").get();
            token = tokenObj.getString("token").toString();


            JSONObject trailsObj = new TrailRequest().execute("http://www.buyctgrown.com/api/node/trail/list").get();
            JSONArray trailNodes = trailsObj.getJSONArray("nodes");

            for (int i=0; i< trailNodes.length(); i++){
                JSONObject singleTrailNode = trailNodes.getJSONObject(i);
                String trailName = singleTrailNode.getString("name");

                JSONArray trailRegionArray = singleTrailNode.getJSONArray("field_trail_region_town_h");
                JSONObject trailRegionObj = trailRegionArray.getJSONObject(0);
                String trailRegion = trailRegionObj.getString("name");

                ArrayList<String> trailCategories = new ArrayList<String>();
                JSONArray categoriesArray = singleTrailNode.getJSONArray("field_trail_categories");
                for( int j=0; j < categoriesArray.length(); j++ ){
                    JSONObject catObj = categoriesArray.getJSONObject(j);
                    String catName = catObj.getString("name");
                    trailCategories.add(catName);
                }
                ArrayList<String> trailSeasons = new ArrayList<String>();
                JSONArray seasonsArray = singleTrailNode.getJSONArray("field_trail_season");
                for( int k=0; k < seasonsArray.length(); k++ ){
                    JSONObject seasonObj = seasonsArray.getJSONObject(k);
                    String seasonName = seasonObj.getString("name");
                    trailSeasons.add(seasonName);
                }
                adapter.add( new Trail( trailName, trailRegion, trailCategories, trailSeasons ) );
            }
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
    class TrailRequest extends AsyncTask<String, Void, JSONObject> {
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
                parameters.put( "limit", "5" );

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
