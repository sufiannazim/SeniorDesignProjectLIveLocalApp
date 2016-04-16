package com.example.sufian.livelocal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EstablishmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Intent intent = getIntent();
        String jsonObjStr = intent.getStringExtra( "establishment" );

        try{
            JSONObject meta = new JSONObject( jsonObjStr );

            TextView nameView = (TextView) findViewById(R.id.locationName);
            nameView.append(meta.getString("name"));

            TextView descView = (TextView) findViewById(R.id.locationDesc);
            String desc = emptyIfNull(stripHTML(meta.getString("field_establishment_description")));
            descView.setText(desc);

            TextView phoneView = (TextView) findViewById(R.id.locationPhone);
            final String phoneNum = emptyIfNull( meta.getString("field_establishment_phone") );
            phoneView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoneNum));
                    startActivity(intent);
                }
            });
            phoneView.append(phoneNum);

            TextView websiteView = (TextView) findViewById(R.id.locationWebsite);
            final String website = emptyIfNull( meta.getString("field_establishment_website") );
            websiteView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                    startActivity(browserIntent);
                }
            });
            websiteView.append( website );

            TextView infoNameView = (TextView) findViewById(R.id.locationInfoName);
            infoNameView.setText(meta.getString("name"));

            JSONArray esLocationsArr = meta.getJSONArray("field_establishment_locations");
            JSONObject esLocation = esLocationsArr.getJSONObject(0);
            JSONObject esLocationZip = esLocation.getJSONObject("field_location_zip");

            TextView infoStreetView = (TextView) findViewById(R.id.locationInfoStreet);
            infoStreetView.setText(esLocation.getString("field_location_street") );

            TextView infoCityStateZipView = (TextView) findViewById(R.id.locationInfoCityStateZip);
            infoCityStateZipView.setText( esLocation.getString("field_location_city") + " " + esLocationZip.getString("field_zip_state") + " " + esLocationZip.getString("name") );

            TextView infoDatesView = (TextView) findViewById(R.id.locationInfoDates);
            infoDatesView.setText( Html.fromHtml(esLocation.getString("field_location_operational_notes")).toString() );

            JSONArray esProductsArray = meta.getJSONArray("field_establishment_products");
            TextView productsView = (TextView) findViewById(R.id.locationProducts);
            StringBuilder productsBuilder = new StringBuilder();
            for(int i=0; i < esProductsArray.length(); i++){
                JSONObject productInfo = esProductsArray.getJSONObject(i);
                productsBuilder.append(productInfo.getString("name"));
                if( i != (esProductsArray.length()-1) ){
                    productsBuilder.append(", ");
                }
            }
            productsView.setText(productsBuilder.toString());

            JSONArray esFeaturesArray = meta.getJSONArray("field_establishment_features");
            JSONObject esFeaturesObj = esFeaturesArray.getJSONObject(0);
           // TextView featuresView = (TextView) findViewById(R.id.locationFeatures);
            //featuresView.setText( esFeaturesObj.getString("name") );
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private String stripHTML( String html ){
        return Html.fromHtml(html).toString();
    }

    private String emptyIfNull( String content ){
        if( "null" == content  ){
            return "";
        }
        return content;
    }
}