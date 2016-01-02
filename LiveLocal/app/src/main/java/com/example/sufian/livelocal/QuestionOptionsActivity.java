package com.example.sufian.livelocal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class QuestionOptionsActivity extends AppCompatActivity {

    private TextView questions;
    private String token;
    private SessionManager session;
    private String sid;
    private TextView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        sid = session.getUserDetails();
        Toast.makeText(getApplicationContext(), sid, Toast.LENGTH_SHORT).show();

        questions = (TextView) findViewById(R.id.textViewQuestionOptions);

        try {
            //gets the token
            token = WebAPICommunication.getToken();
            String apiMethod = "ct/report/question";
            JSONObject tokenObj = new QuestionsRequest().execute("http://www.buyctgrown.com/api/" + apiMethod).get();
            JSONObject questionObj = tokenObj.getJSONObject("question");
            JSONObject sourceLoclOjb = questionObj.getJSONObject("source_local_this_week");
            String question = sourceLoclOjb.getString("question");

            questions.setText(question);

        } catch (ExecutionException e1) {
            System.out.println("ExecutionException");
        } catch (InterruptedException e2) {
            System.out.println("InterruptedException");
        } catch (JSONException e3) {
            System.out.println("JSONException");
        }

        backbtn = (TextView) findViewById(R.id.questionOptionBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    class QuestionsRequest extends AsyncTask<String, Void, JSONObject> {
        private Exception exception;

        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                JSONObject parameters = new JSONObject();
                parameters.put("token", token);
                parameters.put("sid", sid);

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

                wr.writeBytes(parameters.toString());
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
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
    }

}
