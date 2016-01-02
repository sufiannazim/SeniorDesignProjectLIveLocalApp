package com.example.sufian.livelocal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.ArrayMap;
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
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
        TextView textView = (TextView) view.findViewById(R.id.textView);
        HashMap map = WebAPICommunication.getCtStatsHashMap();
        double peoplePD = Double.parseDouble(map.get("people").toString());
        DecimalFormat peopleformatter = new DecimalFormat("#,###");
        String peopleF = peopleformatter.format(peoplePD);

        double locallyPD = Double.parseDouble(map.get("locally").toString());
        DecimalFormat locallyformatter = new DecimalFormat("#,###,###");
        String locallyF = locallyformatter.format(locallyPD);
        textView.setText(Html.fromHtml("<font color='#e64a19'>" + peopleF + "</font>"));
        textView.append(" PEOPLE and ");
        textView.append(Html.fromHtml("<font color='#e64a19'>" + map.get("businesses").toString() + "</font>"));
        textView.append(" BUSINESSES SPENT ");
        textView.append(Html.fromHtml("<font color='#e64a19'>" + "$" + locallyF + "</font>"));
        textView.append(" LOCALLY since August 2013");

        return view;
    }
}
