package com.biet.collegemanagementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class RegisterActivity extends Activity {

    EditText name, address, dob, sex, qualifi, id, password, cpassword, depart, courses, batches;
    Button register,login;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://www.inspireitlabs.com/CollegemanagementSystem/facultySignup.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    ProgressDialog  pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        dob = (EditText) findViewById(R.id.dob);
        sex = (EditText) findViewById(R.id.sex);
        qualifi = (EditText) findViewById(R.id.qualifications);
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.confrimpass);
        depart = (EditText) findViewById(R.id.department);
        courses = (EditText) findViewById(R.id.courses);
        batches = (EditText) findViewById(R.id.batches);


        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
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
            pDialog = new ProgressDialog(RegisterActivity.this);
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
            String _name = name.getText().toString();
            String _address = address.getText().toString();
            String _dob = dob.getText().toString();
            String _sex = sex.getText().toString();
            String _qualifi = qualifi.getText().toString();
            String _id = id.getText().toString();
            String _password = password.getText().toString();
            String _cpassword = cpassword.getText().toString();
            String _depart = depart.getText().toString();
            String _course = courses.getText().toString();
            String _batches = batches.getText().toString();

//            if (!_name.isEmpty() && !_address.isEmpty() && !_dob.isEmpty() && !_sex.isEmpty() && !_qualifi.isEmpty() && !_id.isEmpty()
//                    && !_password.isEmpty() && !_cpassword.isEmpty() && !_depart.isEmpty() && _course.isEmpty() && !_batches.isEmpty()) {
//                //  registerUser(name, email, password);
//            } else {
//                Toast.makeText(getApplicationContext(),
//                        "Please enter your details!", Toast.LENGTH_LONG)
//                        .show();
//            }
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Name", _name));
                params.add(new BasicNameValuePair("Address", _address));
                params.add(new BasicNameValuePair("DOB", _dob));
                params.add(new BasicNameValuePair("Sex", _sex));
                params.add(new BasicNameValuePair("Qualifications", _qualifi));
                params.add(new BasicNameValuePair("Username", _id));
                params.add(new BasicNameValuePair("Password", _password));
                params.add(new BasicNameValuePair("Confrimpassword", _cpassword));
                params.add(new BasicNameValuePair("Department", _depart));
                params.add(new BasicNameValuePair("Courses", _course));
                params.add(new BasicNameValuePair("Branchestaken", _batches));


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
          //  if (file_url != null) {
                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
         //       Toast.makeText(RegisterActivity.this, file_url, Toast.LENGTH_LONG).show();
         //   }

        }

    }

}
