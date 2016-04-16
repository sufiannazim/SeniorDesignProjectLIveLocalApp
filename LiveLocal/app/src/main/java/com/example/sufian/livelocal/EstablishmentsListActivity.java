package com.example.sufian.livelocal;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class EstablishmentsListActivity extends AppCompatActivity {
    private EstablishmentsListAdapter adapter;
    private ListView lv;
    private boolean flag = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishments_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.establishmentsListView);

        Intent intent = getIntent();
        String jsonArrayStr = intent.getStringExtra("establishments");

        final ArrayList<Establishment> establishmentsArray = new ArrayList<Establishment>();
        adapter = new EstablishmentsListAdapter(this, establishmentsArray);
        this.populateAdapter(adapter, jsonArrayStr);
        lv.setAdapter(adapter);

        TextView flagText = (TextView) findViewById(R.id.noEstabFlag);
        if( flag == false ){
            flagText.setText( "No trails available" );
        } else {
            flagText.setVisibility(View.GONE);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if( (establishmentsArray.get(position).meta).length() > 0 ){
                    Intent i = new Intent(EstablishmentsListActivity.this, EstablishmentActivity.class);
                    Bundle b = new Bundle();
                    b.putString("establishment", (establishmentsArray.get(position).meta).toString());
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });
    }

    public void populateAdapter( EstablishmentsListAdapter adapter, String jsonArrayString ){
        try {
            JSONArray establishments = new JSONArray(jsonArrayString);
            if( establishments.length() > 0 ){

            }
            for (int i=0; i< establishments.length(); i++){
                JSONObject obj = establishments.getJSONObject(i);
                if( obj.length() < 1 ){
                    flag = true;
                    continue;
                }
                JSONObject establishmentMeta = obj.getJSONObject("field_trail_col_establishment");
                String name = establishmentMeta.getString("name");
                if( !(establishmentMeta.isNull("name")) ){
                    flag = true;
                }
                JSONArray esLocationsArr = establishmentMeta.getJSONArray("field_establishment_locations");
                JSONObject esLocation = esLocationsArr.getJSONObject(0);
                JSONObject esLocationZip = esLocation.getJSONObject("field_location_zip");

                String street = esLocation.getString("field_location_street");
                String cityState = esLocation.getString("field_location_city") + " " + esLocationZip.getString("field_zip_state") + " " + esLocationZip.getString("name");
                String phone = establishmentMeta.getString("field_establishment_phone");

                String schedule = emptyIfNull(obj.getString("field_trail_hours"));
                adapter.add(new Establishment(name, street, cityState, phone, schedule, establishmentMeta));
            }
        } catch ( JSONException e) {
            e.printStackTrace();
        } catch ( NullPointerException e ){
            //System.out.println("### NULL ###");
        }
    }

    private String emptyIfNull( String content ){
        if( "null" == content || content == null  ){
            return "";
        }
        return content;
    }
}
