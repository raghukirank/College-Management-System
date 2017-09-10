package com.biet.collegemanagementsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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


public class LoginActivity extends Activity {

    EditText user, pass;
    Button log;
            TextView sign;
    ProgressDialog pDialog;
    private static final String LOGIN_URL = "http://www.inspireitlabs.com/CollegemanagementSystem/facultyLogin.php";

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    JSONArray response;

    String depar,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);


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
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }

    class AttemptLogin extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(LoginActivity.this);
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


                response= json.getJSONArray("facultydetails");

                // looping through All Products
                for (int i = 0; i < response.length(); i++) {
                    JSONObject c = response.getJSONObject(i);


                     depar=c.getString("depart");
                    status=c.getString("status");

                    SharedPreferences share= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

                    SharedPreferences.Editor edit=share.edit();

                    edit.putString("depart",depar);

                    edit.commit();

                }
                // json success tag
                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pDialog.dismiss();
            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
            if(status.equals("Activated")) {
                Intent i = new Intent(LoginActivity.this, FacultyScreen.class);
                startActivity(i);
            }
            else
            {
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("Approval Pending")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                LoginActivity.this.finish();
                            }
                        })

                        .show();
            }
        }
    }
}