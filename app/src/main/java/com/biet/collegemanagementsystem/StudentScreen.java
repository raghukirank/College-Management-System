package com.biet.collegemanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudentScreen extends ActionBarActivity {

    Button satten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_screen);

        satten = (Button) findViewById(R.id.vatten);
        satten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StudentScreen.this, StudentAttendanceScreen.class);
                startActivity(i);


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(StudentScreen.this,Home.class);
        startActivity(i);

    }
}


