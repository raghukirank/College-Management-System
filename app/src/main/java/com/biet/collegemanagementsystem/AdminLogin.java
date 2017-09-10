package com.biet.collegemanagementsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AdminLogin extends ActionBarActivity {

    EditText user, pass;
    Button log;
    ProgressDialog pDialog;

    TextView sign;
    private static final String LOGIN_URL = "http://www.inspireitlabs.com/CollegemanagementSystem/adminlogin.php";
    JSONParser jsonParser = new JSONParser();
    JSONArray response;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        log = (Button) findViewById(R.id.btnLogin);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AttemptLogin().execute();

            }
        });

        sign = (TextView) findViewById(R.id.btnLinkToRegisterScreen);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLogin.this, AdminSignup.class);
                startActivity(i);
            }
        });

    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(AdminLogin.this);
            pDialog.setMessage("Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag

                response = json.getJSONArray("admindetails");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent i = new Intent(AdminLogin.this, AdminScreen.class);
            startActivity(i);
            pDialog.dismiss();
        }
    }
}