package com.biet.collegemanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FacultyScreen extends ActionBarActivity {

    Button takeaatendence,view,changepass,find;
    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray response;
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product = "http://www.inspireitlabs.com/CollegemanagementSystem/getstudentDetails.php";

    ArrayList<String> aa=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_screen);

        takeaatendence=(Button)findViewById(R.id.takeatten);
        takeaatendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FacultyScreen.this,StudentsData.class);
                startActivity(i);
            }
        });

        find=(Button)findViewById(R.id.fstu);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FacultyScreen.this,FINDSTUDENTVIEW.class);
                startActivity(i);
            }
        });

        view=(Button)findViewById(R.id.viewatten);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FacultyScreen.this,Viewattendence.class);
                startActivity(i);
            }
        });

        changepass=(Button)findViewById(R.id.cp);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FacultyScreen.this,Changepassowrd.class);
                startActivity(i);
            }
        });


    }
    class CreateNewProduct extends AsyncTask<String, String, String> {



        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(FacultyScreen.this);
            pDialog.setMessage("Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


    protected String doInBackground(String... args) {
        try {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("",""));

            // getting JSON Object
            // Note that create product url accepts POST method
//				JSONObject json = jsonParser.makeHttpRequest(url_create_product,
//						"POST", params);

            JSONObject jsonone = jsonParser.makeHttpRequest(url_create_product,
                    "GET", params);
            // check log cat fro response
            //	Log.d("Create Response", json.toString());

            // check for success tag


            // failed to create product

            response= jsonone.getJSONArray("studentdetails");

            // looping through All Products
            for (int i = 0; i < response.length(); i++) {
                JSONObject c = response.getJSONObject(i);

                String id=c.getString("rollno");
                String name=c.getString("sname");
                String mail=c.getString("username");
                String gender=c.getString("branch");

                aa.add(id+"\n"+name+"\n"+mail+"\n"+gender);


                Log.v("id", "" + aa);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done

        ArrayAdapter<String> jsondata = new ArrayAdapter<String>(FacultyScreen.this, android.R.layout.simple_list_item_1, aa);


        pDialog.dismiss();


    }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(FacultyScreen.this,Home.class);
        startActivity(i);

    }

}


