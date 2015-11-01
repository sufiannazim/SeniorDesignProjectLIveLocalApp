package com.example.sufian.livelocal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class HomeFragment extends Fragment{

    protected String tokenstr;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        try {
            /*
            Replace the string in the apiMethod with the API you want to access. You can use system/get_token/, user/auth, etc
             */
            String apiMethod = "system/get_token";
            JSONObject tokenObj = new APIRequest().execute( "http://www.buyctgrown.com/api/" + apiMethod ).get();
            //System.out.println( tokenObj.getString("token").toString() );
            tokenstr = tokenObj.getString("token").toString();

            String apiMethod1 = "ct/stats";
            JSONObject tokenObj1 = new APIRequest1().execute( "http://www.buyctgrown.com/api/" + apiMethod1 ).get();

            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(tokenObj1.getString("message").toString());



        } catch ( ExecutionException e1 ){
            System.out.println( "ExecutionException" );
        } catch ( InterruptedException e2 ){
            System.out.println( "InterruptedException" );
        } catch ( JSONException e3 ){
            System.out.println( "JSONException" );
        }
        return view;

    }

    class APIRequest extends AsyncTask<String, Void, JSONObject>{
        private Exception exception;

        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL( params[0] );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                /*
                This is where the request body will go.
                If you go to the GitHub links in the API documentation PDF, in each one you will see sections called "Request"
                Example: https://gist.github.com/Anatoliy-Gerasimov/8b15ca31c717474f258d
                Copy them below into the parameters object.
                 */
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

    class APIRequest1 extends AsyncTask<String, Void, JSONObject>{
        private Exception exception;

        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL( params[0] );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                /*
                This is where the request body will go.
                If you go to the GitHub links in the API documentation PDF, in each one you will see sections called "Request"
                Example: https://gist.github.com/Anatoliy-Gerasimov/8b15ca31c717474f258d
                Copy them below into the parameters object.
                 */
                JSONObject parameters = new JSONObject();
                parameters.put( "token", tokenstr );

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
