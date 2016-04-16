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
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeasonsTop10 extends AppCompatActivity {
    private String token;
    private TextView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons_top10);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView seasonTop10View = (TextView) findViewById(R.id.seasonsTop10View);
        HashMap hashMap = WebAPICommunication.getseasonTopTenHashMap();
        seasonTop10View.setText(Html.fromHtml("<font color='#795548'>" + hashMap.get("seasonsTop10Body") + "</font>"));
        seasonTop10View.setMovementMethod(new ScrollingMovementMethod());
        seasonTop10View.setMovementMethod(LinkMovementMethod.getInstance());

        backbtn = (TextView) findViewById(R.id.seasonTopTenBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

}
