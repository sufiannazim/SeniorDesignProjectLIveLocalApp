package com.example.sufian.livelocal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.os.AsyncTask;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    protected String token;
    private String username;
    private String password;
    private String loginStatus = "";
    private String userName;
    private String sid;
    private boolean checker;
    private SessionManager session;
    private static boolean hideBtn = true;
    private TextView backbtn;
    private TextView forgetPasswordbtn;
    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        session = new SessionManager(getApplicationContext());

        usernameText = (EditText) findViewById(R.id.editTextUsername);
        passwordText = (EditText) findViewById(R.id.editTextPassword);

        usernameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    usernameText.setTextColor(Color.BLACK);
                else
                    usernameText.setTextColor(Color.GRAY);
            }
        });

        final Button login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameText.getText() != null && passwordText.getText() != null) {
                    try {
                        //gets username and password entered in textbox
                        username = usernameText.getText().toString();
                        password = passwordText.getText().toString();
                        //gets the token
                        token = WebAPICommunication.getToken();

                        String apiMethod1 = "user/auth";
                        JSONObject tokenObj1 = new LoginRequest().execute("http://www.buyctgrown.com/api/" + apiMethod1).get();
                        loginStatus = tokenObj1.getString("status");

                        JSONObject userObj = tokenObj1.getJSONObject("user");
                        userName = userObj.getString("name");
                        sid = userObj.getString("sid");
                        //gets status of login.  Will be equal to "ok" on successful login

                    } catch (ExecutionException e1) {
                        System.out.println("ExecutionException");
                    } catch (InterruptedException e2) {
                        System.out.println("InterruptedException");
                    } catch (JSONException e3) {
                        System.out.println("JSONException");
                    }
                    if (loginStatus.equals("ok")) {
                        hideBtn = false;
                        session.createLoginSession(userName, sid);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Username/Password, please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Username/Password cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backbtn = (TextView) findViewById(R.id.loginBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgetPasswordbtn = (TextView) findViewById(R.id.forgetPasswordbtn);
        forgetPasswordbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String url = "http://www.buyctgrown.com/user/password";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }
     public static boolean gethideBtn() {
         return hideBtn;
     }

    public static void setHideBtn(boolean cond){
        hideBtn = cond;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.register:
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class LoginRequest extends AsyncTask<String, Void, JSONObject>{
        private Exception exception;

        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL( params[0] );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                JSONObject parameters = new JSONObject();
                parameters.put("token", token);
                parameters.put("username", username);
                parameters.put("password", password);

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
