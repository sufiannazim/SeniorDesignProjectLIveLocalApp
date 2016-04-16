package com.example.sufian.livelocal;

import android.os.AsyncTask;
import android.os.SystemClock;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sufian on 12/29/2015.
 */
public class WebAPICommunication {

    private static String token;
    private static JSONObject parameters = new JSONObject();

    private static HashMap ctStats = new HashMap();
    private static HashMap seasonTopTenIntro = new HashMap();
    private static HashMap seasonTopTen = new HashMap();
    private static HashMap eventList = new HashMap();
    private static HashMap trailsHashMap = new HashMap();

    private static String[] eventIdArray = new String[10];
    private static String[] eventAddress = new String[10];
    private static String[] eventDateArray = new String[10];
    private static String[] eventTitleArray = new String[10];
    private static String[] eventLatArray = new String[10];
    private static String[] eventLngArray = new String[10];

    public static String  getToken(){
        return token;
    }
    public static HashMap getCtStatsHashMap(){
        return ctStats;
    }
    public static HashMap getseasonTopTenIntroHashMap(){
        return seasonTopTenIntro;
    }
    public static HashMap getseasonTopTenHashMap(){
        return seasonTopTen;
    }
    public static HashMap geteventListHashMap(){
        return eventList;
    }
    public static HashMap getTrailsHashMap(){
        return trailsHashMap;
    }

    public static String getAPIToken() {

        try {
            String apiMethod = "system/get_token";
            JSONObject tokenObj = new APITokenRequest().execute( "http://www.buyctgrown.com/api/" + apiMethod ).get();
            token = tokenObj.getString("token").toString();

        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
        return token;
    }

    public static HashMap getCTStats(){

        try {
            parameters.put( "token", token);
            String apiMethod1 = "ct/stats";
            JSONObject tokenObj = new APIDataRequest().execute( "http://www.buyctgrown.com/api/" + apiMethod1 ).get();
            JSONObject index = tokenObj.getJSONObject("raw_data");
            String people = index.getString("people").toString();
            String businesses = index.getString("businesses").toString();
            String locally = index.getString("locally").toString();
            ctStats.put("people", people);
            ctStats.put("businesses", businesses);
            ctStats.put("locally", locally);
        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
        return ctStats;
    }

    public static HashMap getSeasonsTopTenIntro(){

        try {
            parameters.put( "token", token);
            JSONObject seasonsObj = new APIDataRequest().execute("http://www.buyctgrown.com/api/node/top_10_item/list").get();
            JSONArray top10Array = seasonsObj.getJSONArray("nodes");
            JSONObject top10Obj = top10Array.getJSONObject(0);
            String seasonsTop10Intro = top10Obj.getString("field_top_10_teaser");
            seasonTopTenIntro.put("seasonsTop10Intro", seasonsTop10Intro);
        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
        return seasonTopTenIntro;
    }

    public static HashMap getSeasonsTopTen(){

        try {
            parameters.put( "token", token);
            parameters.put( "limit", "10");
            JSONObject seasonsObj = new APIDataRequest().execute("http://www.buyctgrown.com/api/node/top_10_item/list").get();
            JSONArray top10Array = seasonsObj.getJSONArray("nodes");
            JSONObject top10Obj = top10Array.getJSONObject(0);
            String seasonsTop10Body = top10Obj.getString("field_top_10_body");
            seasonTopTen.put("seasonsTop10Body", seasonsTop10Body);
        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
        return seasonTopTen;
    }

    public static HashMap getEventList(){

        try {
            parameters.put( "token", token );
            //parameters.put( "limit", "10" );
            String Date = "";
            final Calendar cal = Calendar.getInstance();
            int dd = cal.get(Calendar.DAY_OF_MONTH);
            int mm = cal.get(Calendar.MONTH);
            int yy = cal.get(Calendar.YEAR);
            Date = "" + yy + "-" + (mm+1) + "-" + dd;
            parameters.put( "start_date" , Date);

            JSONObject eventsObj = new APIDataRequest().execute("http://www.buyctgrown.com/api/event/list").get();
            JSONArray eventsArray = eventsObj.getJSONArray("events");

            JSONArray sortedEventsArray = new JSONArray();

            final List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < eventsArray.length(); i++) {
                jsonValues.add(eventsArray.getJSONObject(i));
            }
            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                private static final String KEY_NAME = "field_event_date";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA = new String();
                    String valB = new String();

                    try {
                        JSONObject ObjA =  a.getJSONObject(KEY_NAME);
                        JSONObject ObjB =  b.getJSONObject(KEY_NAME);
                        valA = ObjA.getString("value");
                        valB = ObjB.getString("value");
                    } catch (JSONException e) {
                        //do something
                    }

                    return valA.compareTo(valB);
                }
            });

            for (int i = 0; i < eventsArray.length(); i++) {
                sortedEventsArray.put(jsonValues.get(i));
            }

            eventsArray = sortedEventsArray;

            for (int i=0; i< eventsArray.length(); i++){
                JSONObject singleEventObj = eventsArray.getJSONObject(i);
                String body = singleEventObj.getString("field_event_body");
                eventIdArray[i] = body.toString();

                String st = singleEventObj.getString("field_event_street_1");
                String city = singleEventObj.getString("field_event_city");
                String state = singleEventObj.getString("field_event_state");
                String address = st + "\n" + city + ", " + state;
                eventAddress[i] = address;

                String singleEventName = singleEventObj.getString("name");
                eventTitleArray[i] = singleEventName;

                String singleEventLat = singleEventObj.getString("field_event_lat");
                eventLatArray[i] = singleEventLat;

                String singleEventLng = singleEventObj.getString("field_event_lng");
                eventLngArray[i] = singleEventLng;

                JSONObject singleEventDateObj = singleEventObj.getJSONObject("field_event_date");
                String singleEventTimestamp = singleEventDateObj.getString("value");
                eventDateArray[i] = singleEventTimestamp;
            }
            eventList.put("eventsArray", eventsArray);
            eventList.put("eventIdArray", eventIdArray);
            eventList.put("eventAddress", eventAddress);
            eventList.put("eventTitleArray", eventTitleArray);
            eventList.put("eventLatArray", eventLatArray);
            eventList.put("eventLngArray", eventLngArray);
            eventList.put("eventDateArray", eventDateArray);

        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
        return eventList;
    }

    public static HashMap getTrails(){

        try {
            parameters.put( "token", token);
            JSONObject trailsObj = new APIDataRequest().execute("http://www.buyctgrown.com/api/node/trail/list").get();
            JSONArray trailNodes = trailsObj.getJSONArray("nodes");
            Trail[] filteredTrails = new Trail[trailNodes.length()];

            for (int i=0; i< trailNodes.length(); i++){
                JSONObject singleTrailNode = trailNodes.getJSONObject(i);
                String trailName = singleTrailNode.getString("name");

                JSONArray establishmentsArray = singleTrailNode.getJSONArray("field_trail_establishments_c");

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

                filteredTrails[i] = new Trail( trailName, trailRegion, trailCategories, trailSeasons, establishmentsArray );
            }
            trailsHashMap.put("trails", filteredTrails);
        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
        return trailsHashMap;
    }

    static class APITokenRequest extends AsyncTask<String, Void, JSONObject> {
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

    static class APIDataRequest extends AsyncTask<String, Void, JSONObject>{
        private Exception exception;

        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL( params[0] );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                DataOutputStream wr = new DataOutputStream( connection.getOutputStream() );

                wr.writeBytes(parameters.toString() );
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