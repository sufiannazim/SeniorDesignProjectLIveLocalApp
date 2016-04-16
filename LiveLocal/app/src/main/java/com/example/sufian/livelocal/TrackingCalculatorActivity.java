package com.example.sufian.livelocal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
    private boolean cond = false;

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
                int amount = 0;
                try {
                    amount = Integer.parseInt(amountEntered.getText().toString());
                    cond = true;
                } catch (NumberFormatException e) {
                    System.out.println("NumberFormatException");
                }

                if (cond == true) {
                    //int amount = Integer.parseInt(amountEntered.getText().toString());
                    if ((amount / 10) == amountSpent) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Congratulations!");
                        builder.setMessage("You have spent 10% on local products this week!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                            }
                        });
                        alert.show();

                    } else if ((amount / 10) < amountSpent) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Congratulations");
                        builder.setMessage("You have spent more than 10% on local products this week!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                            }
                        });
                        alert.show();

                    } else if ((amount / 10) > amountSpent) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Buy local!");
                        builder.setMessage("You need to spend $" + ((amount / 10) - amountSpent) + " more to reach your 10% goal! Keep up the good work!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                            }
                        });
                        alert.show();

                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Invalid Input");
                    builder.setMessage("Please enter a valid number")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                        }
                    });
                    alert.show();
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
