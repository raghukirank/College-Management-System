package com.biet.collegemanagementsystem;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudentAttendanceScreen extends ActionBarActivity {
    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray response;
    private static final String TAG_SUCCESS = "success";

    private static String url_create_attendence = "http://www.inspireitlabs.com/CollegemanagementSystem/attendenceDetails.php";

    ArrayList<String> aa = new ArrayList<String>();


    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_screen);

        list = (ListView) findViewById(R.id.listattendence);


        new CreateNewProduct().execute();

    }


    class CreateNewProduct extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(StudentAttendanceScreen.this);
            pDialog.setMessage("Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            try {

                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("",""));

                // getting JSON Object
                // Note that create product url accepts POST method
//				JSONObject json = jsonParser.makeHttpRequest(url_create_product,
//						"POST", params);

                JSONObject jsonone = jsonParser.makeHttpRequest(url_create_attendence,
                        "GET", params);
                // check log cat fro response
                //	Log.d("Create Response", json.toString());

                // check for success tag


                // failed to create product

                response = jsonone.getJSONArray("attendencedetails");

                // looping through All Products
                for (int i = 0; i < response.length(); i++) {
                    JSONObject c = response.getJSONObject(i);

                    String id = c.getString("rollno");
                    String name = c.getString("status");
                    String bran = c.getString("datetime");


                    SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(StudentAttendanceScreen.this);

                    String data = share.getString("rollno", null);

                    // if(data.contains(id)) {

                    int dt=Integer.parseInt(data);

                    int ids=Integer.parseInt(id);

                    if(dt==ids) {

                        aa.add("Roll No   " + id + "\n" + "Attendance   " + name + "\n" + "Date   " + bran);

                    }


                    //   Log.v("id", "" + aa);
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
            // dismiss the dialog once done


            ArrayAdapter<String> jsondata = new ArrayAdapter<String>(StudentAttendanceScreen.this, android.R.layout.simple_list_item_1, aa);

            list.setAdapter(jsondata);


            pDialog.dismiss();


        }


    }

}




