package com.example.sufian.livelocal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class EventActivity extends AppCompatActivity {
    private EventListAdapter adapter;
    private String token;
    private String[] eventIdArray;
    private String[] eventAddress;
    private String[] eventDateArray;
    private String[] eventTitleArray;
    private String[] eventLatArray;
    private String[] eventLngArray;
    private static String eventAddr;
    private static String eventDetail;
    private static String eventDate;
    private static String eventTitle;
    private static String eventLat;
    private static String eventLng;
    private TextView backbtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventIdArray = new String[10];
        eventAddress = new String[10];
        eventDateArray = new String[10];
        eventTitleArray = new String[10];
        eventLatArray = new String[10];
        eventLngArray = new String[10];

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventAddr = eventAddress[position];
                eventDetail = eventIdArray[position];
                eventDate = eventDateArray[position];
                eventTitle = eventTitleArray[position];
                eventLat = eventLatArray[position];
                eventLng = eventLngArray[position];
                //Toast.makeText(getApplicationContext(), "Position Number: " + position + " id: " + eventIdArray[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ArrayList<Event> eventsArray = new ArrayList<Event>();
        adapter = new EventListAdapter(this, eventsArray);
        populateAdapter(adapter);
        lv.setAdapter(adapter);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        backbtn = (TextView) findViewById(R.id.eventBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static String getEventAddr(){
        return eventAddr;
    }

    public static String getEventDetail(){
        return eventDetail;
    }

    public static String getEventDate(){
        return eventDate;
    }

    public static String getEventTitle(){
        return eventTitle;
    }

    public static String getEventLat(){
        return eventLat;
    }

    public static String getEventLng(){
        return eventLng;
    }

    public void populateAdapter(EventListAdapter adapter) {

        HashMap hashMap = WebAPICommunication.geteventListHashMap();
        JSONArray eventsArray = (JSONArray) hashMap.get("eventsArray");
        eventDateArray = (String[]) hashMap.get("eventDateArray");
        eventTitleArray = (String[]) hashMap.get("eventTitleArray");
        eventAddress = (String []) hashMap.get("eventAddress");
        eventIdArray = (String[]) hashMap.get("eventIdArray");
        eventLatArray = (String []) hashMap.get("eventLatArray");
        eventLngArray = (String[]) hashMap.get("eventLngArray");

        for (int i = 0; i < eventsArray.length(); i++) {

            String[] separated = eventDateArray[i].split(" ");
            String singleEventDate = separated[0];
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat formats = new SimpleDateFormat("MMMM dd, yyyy");
            Date dt;
            String formatedDate;
            try {
                dt = format.parse(singleEventDate);
                formatedDate = formats.format(dt);
                adapter.add(new Event(eventTitleArray[i], formatedDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

}
