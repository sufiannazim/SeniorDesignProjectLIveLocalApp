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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventDetailActivity extends AppCompatActivity {

    private TextView txtViewTitle;
    private TextView txtViewDetail;
    private TextView txtViewWebLink;
    private TextView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtViewTitle = (TextView) findViewById(R.id.textViewTitle);
        txtViewDetail = (TextView) findViewById(R.id.textViewDetail);
        txtViewWebLink = (TextView) findViewById(R.id.textViewWebLink);

        txtViewTitle.setText(EventActivity.getEventTitle());

        String startTime = EventActivity.getEventDate();
        StringTokenizer token = new StringTokenizer(startTime);
        String date = token.nextToken();
        String time = token.nextToken();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat formats = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");

        Date dtD;
        Date dt;
        try {
            dtD = format.parse(date);
            dt = sdf.parse(time);
            txtViewDetail.setText(formats.format(dtD) + " " +  sdfs.format(dt) + "\n");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //txtViewDetail.setText(time + "\n");
        txtViewDetail.append("\n");
        txtViewDetail.append(EventActivity.getEventAddr() + "\n");
        txtViewDetail.append("______________________________________");
        txtViewDetail.append("\n");
        txtViewDetail.append("\n");
        //txtViewDetail.append(EventActivity.getEventDetail());
        //txtViewDetail.append("\n");
        //txtViewDetail.append("\n");
        //txtViewDetail.setMovementMethod(LinkMovementMethod.getInstance());


        List<String> extractedUrls = extractUrls(EventActivity.getEventDetail());

        String webLink = "";
        for (final String url : extractedUrls)
        {
            //System.out.println(url);
            webLink = url;
        }

        String detail = EventActivity.getEventDetail();
        String detailWithoutUrls = detail.replace(webLink, "");
        txtViewDetail.append(detailWithoutUrls);
        final String finalLink = webLink;
        txtViewWebLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalLink));
                startActivity(browserIntent);
            }
        });
        txtViewWebLink.append(webLink);

        backbtn = (TextView) findViewById(R.id.eventDetailBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * Returns a list with all links contained in the input
     */
    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.map:
                String latitude = EventActivity.getEventLat();
                String longitude = EventActivity.getEventLng();
                String address = EventActivity.getEventAddr();
                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
