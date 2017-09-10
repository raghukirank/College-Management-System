package com.biet.collegemanagementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudentSignup extends ActionBarActivity {

    private EditText roll,namee,user,pass,branch,sec,year;
    private Button mRegister,mlogin;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login script

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/register.php";

    //testing on Emulator:
    private static final String LOGIN_URL = "http://www.inspireitlabs.com/CollegemanagementSystem/studentSignup.php";

    //testing from a real server:
    //private static final String LOGIN_URL = "http://www.yourdomain.com/webservice/register.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentsignup);

        roll = (EditText) findViewById(R.id.rollno);
        namee = (EditText) findViewById(R.id.name);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        branch = (EditText) findViewById(R.id.branch);
        sec = (EditText) findViewById(R.id.sec);
        year = (EditText) findViewById(R.id.semis);




        mRegister = (Button) findViewById(R.id.btnRegister);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateUser().execute();
            }
        });




    }




    class CreateUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StudentSignup.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String rolll = roll.getText().toString();
            String _nameee = namee.getText().toString();
            String _username = user.getText().toString();
            String _password = pass.getText().toString();
            String bran = branch.getText().toString();
            String sect = sec.getText().toString();
            String yearr = user.getText().toString();



            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("rollno", rolll));
                params.add(new BasicNameValuePair("name", _nameee));
                params.add(new BasicNameValuePair("username", _username));
                params.add(new BasicNameValuePair("password", _password));
                params.add(new BasicNameValuePair("branch", bran));
                params.add(new BasicNameValuePair("section", sect));
                params.add(new BasicNameValuePair("year", yearr));



                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(StudentSignup.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}
