package com.example.sufian.livelocal;

/**
 * Created by Sufian on 10/1/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        StartAnimations();

        Thread timerThread = new Thread(){
            public void run(){

                getDatafromAPI();
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    public void getDatafromAPI(){

        WebAPICommunication.getAPIToken();
        WebAPICommunication.getCTStats();
        WebAPICommunication.getSeasonsTopTenIntro();
        WebAPICommunication.getSeasonsTopTen();
        WebAPICommunication.getEventList();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.splashAnim);
        l.clearAnimation();
        l.startAnimation(anim);
    }
}
