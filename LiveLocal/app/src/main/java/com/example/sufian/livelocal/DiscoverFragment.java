package com.example.sufian.livelocal;

import android.content.Intent;
<<<<<<< HEAD
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
=======
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
>>>>>>> origin/master
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

<<<<<<< HEAD
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
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> origin/master

public class DiscoverFragment extends Fragment {
    private List<String> menu;
    TextView STTtxtView;
    private String token;
    private String topten;

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
        ListView lv = (ListView) rootView.findViewById(R.id.discoverListView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = menu.get(position);
<<<<<<< HEAD
                Intent intent;
                if( item.equals("Events") ){
                    intent = new Intent(getActivity(), EventActivity.class);
                    startActivity(intent);
                } else if (item.equals(topten)) {
                    intent = new Intent(getActivity(), SeasonsTop10.class);
                    startActivity(intent);
                } else if (item.equals("Trails")){
                    Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                /*
                switch (item){
                    case "Events":
                        intent = new Intent(getActivity(), EventActivity.class);
                        startActivity(intent);
                        break;
                    case "Season's Top Ten":
                        intent = new Intent(getActivity(), SeasonsTop10.class);
                        startActivity(intent);
                        break;
                    case "Trails":
                        Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        break;
                }*/
            }
        });

        try {
            JSONObject tokenObj = new TokenRequest().execute("http://www.buyctgrown.com/api/system/get_token").get();
            token = tokenObj.getString("token").toString();

            JSONObject seasonsObj = new SeasonRequest().execute("http://www.buyctgrown.com/api/node/top_10_item/list").get();
            JSONArray top10Array = seasonsObj.getJSONArray("nodes");
            JSONObject top10Obj = top10Array.getJSONObject(0);

            String seasonsTop10Body = top10Obj.getString("field_top_10_teaser");
            String temp = Html.fromHtml(seasonsTop10Body).toString();
            topten = "Season's Top Ten \n" + temp;

        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }

        menu = new ArrayList<String>();
        menu.add(topten);
=======
                
                if( item.equals("Events") ){
                    Intent intent = new Intent(getActivity(), EventActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                }
            }
        });

        menu = new ArrayList<String>();
        menu.add("Season's Top 10");
>>>>>>> origin/master
        menu.add("Events");
        menu.add("Trails");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
<<<<<<< HEAD
                R.layout.listviewitem, menu);
        lv.setAdapter(adapter);
        //lv.setMinimumHeight(20);

        return rootView;
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
=======
                android.R.layout.simple_list_item_1, menu);
        lv.setAdapter(adapter);

        return rootView;
>>>>>>> origin/master
    }

}
