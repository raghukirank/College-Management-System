package com.biet.collegemanagementsystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class AdminScreen extends ActionBarActivity {

    Button facultyms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);


        facultyms=(Button)findViewById(R.id.facultymaster);
        facultyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AdminScreen.this,FacultyActivation.class);
                startActivity(in);
            }
        });

    }



}
