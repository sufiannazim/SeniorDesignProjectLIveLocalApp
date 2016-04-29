package com.example.sufian.livelocal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class TrailsActivity extends AppCompatActivity {
    private TrailsListAdapter adapter;
    private TextView backbtn;
    private TextView removeView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv = (ListView) findViewById(R.id.trailListView);

        final ArrayList<Trail> trailsArray = new ArrayList<Trail>();
        adapter = new TrailsListAdapter(this, trailsArray);
        this.populateAdapter(adapter);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if( (trailsArray.get(position)).establishments.length() > 0 ){
                System.out.println((trailsArray.get(position)).establishments.length());
                Intent i = new Intent(TrailsActivity.this, EstablishmentsListActivity.class);
                Bundle b = new Bundle();
                b.putString("establishments", (trailsArray.get(position).establishments).toString());
                i.putExtras(b);
                startActivity(i);
            } else {
                Toast.makeText(TrailsActivity.this, "No establishments at this trail", Toast.LENGTH_SHORT).show();
            }
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        removeView = (TextView) findViewById(R.id.dismisstrails);
        removeView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void populateAdapter( TrailsListAdapter adapter ){
        try {
            HashMap hashMap = WebAPICommunication.getTrailsHashMap();
            Trail[] trailsArray = (Trail[]) hashMap.get("trails");
            for(int i=0; i < trailsArray.length; i++){
                Trail tElem = trailsArray[i];
                if( tElem != null ){
                    adapter.add( tElem );
                }
            }
        } catch ( Exception e1 ){
            System.out.println( "ExecutionException: " + e1 );
        }
    }
}