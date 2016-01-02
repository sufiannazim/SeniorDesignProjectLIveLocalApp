package com.example.sufian.livelocal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.widget.ShareButton;

public class TrackingCalculatorActivity extends AppCompatActivity {

    private TextView trackStatsFirstPart;
    private TextView trackStatsData;
    private TextView trackStatsLastPart;
    private EditText amountEntered;
    private int amountSpent;
    private Button calcButton;
    private TextView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        trackStatsFirstPart = (TextView) findViewById((R.id.textViewCalcFirstPart));
        trackStatsData = (TextView) findViewById((R.id.textViewCalcData));
        trackStatsLastPart = (TextView) findViewById((R.id.textViewCalcLastPart));

        amountSpent = TrackStatsActivity.getStatData();

        trackStatsFirstPart.setText("This week you have spent:");
        trackStatsData.setText("$"+amountSpent);
        trackStatsLastPart.setText("Enter approximately how much you spend on all groceries and gardening products each week");

        amountEntered = (EditText) findViewById(R.id.editTextCalcAmount);

        calcButton = (Button) findViewById(R.id.trackBtnCalc);
        calcButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int amount = Integer.parseInt(amountEntered.getText().toString());
                if((amount/10) == amountSpent){
                    Toast.makeText(getApplicationContext(), "You have spent 10%", Toast.LENGTH_SHORT).show();

                } else if((amount/10) < amountSpent){
                    Toast.makeText(getApplicationContext(), "beyond the goal", Toast.LENGTH_SHORT).show();

                } else if((amount/10) > amountSpent){
                    Toast.makeText(getApplicationContext(), "Have not reached the goal", Toast.LENGTH_SHORT).show();

                }
            }
        });

        backbtn = (TextView) findViewById(R.id.trackCalcBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }
}
