package com.biet.collegemanagementsystem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudentsData extends ActionBarActivity {

    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray response;
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product = "http://www.inspireitlabs.com/CollegemanagementSystem/getstudentDetails.php";

    private static String url_create_attendence = "http://www.inspireitlabs.com/CollegemanagementSystem/studentAttendence.php";


    ArrayList<String> rollList = new ArrayList<String>();
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> branchList = new ArrayList<String>();
    ArrayList<String> sectionList = new ArrayList<String>();
    ArrayList<String> yearList = new ArrayList<String>();


    ListView list;

    String rollString, statusString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_data);


        list = (ListView) findViewById(R.id.list);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                rollString = rollList.get(position).toString();
              // Dial();
                Toast.makeText(getApplicationContext(), rollString, Toast.LENGTH_SHORT).show();
                // statusString = "P";

              //  new Takeattendence().execute();

            }
        });

        new CreateNewProduct().execute();
    }

    /**
     * Background Async Task to Create new product
     */
    class CreateNewProduct extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(StudentsData.this);
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

                JSONObject jsonone = jsonParser.makeHttpRequest(url_create_product,
                        "GET", params);
                // check log cat fro response
                //	Log.d("Create Response", json.toString());

                // check for success tag


                // failed to create product

                response = jsonone.getJSONArray("studentdetails");

                // looping through All Products
                for (int i = 0; i < response.length(); i++) {
                    JSONObject c = response.getJSONObject(i);

                    String id = c.getString("rollno");
                    String name = c.getString("sname");
                    String bran = c.getString("branch");
                    String sec = c.getString("section");
                    String ye = c.getString("year");


                    SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(StudentsData.this);

                    String data = share.getString("depart", null);

                    if (data.equals(bran)) {

                        rollList.add(id);
                        nameList.add(name);
                        branchList.add(bran);
                        sectionList.add(sec);
                        yearList.add(ye);

                        //  aa.add(id + "\n" + name + "\n" + bran + "\n" + sec + "\n" + ye);
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


            if (rollList.size() > 0) {
                //    ArrayAdapter<String> jsondata = new ArrayAdapter<String>(StudentsData.this, android.R.layout.simple_list_item_1, aa);

                list.setAdapter(new Mydatadisplay());
            }

            pDialog.dismiss();


        }

    }


    public class Mydatadisplay extends BaseAdapter {

        @Override
        public int getCount() {
            return rollList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.datadisplaying, null);


//            Button b=(Button)convertView.findViewById(R.id.pre);
//            b.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    View parentRow = (View) v.getParent();
//
//                    ListView listView = (ListView) parentRow.getParent();
//
//                    final int position = listView.getPositionForView(parentRow);
//
//                    rollString = rollList.get(position).toString();
//                    statusString = "P";
//                    new Takeattendence().execute();
//
//
//                    Toast.makeText(StudentsData.this,"Clicked",Toast.LENGTH_LONG).show();
//                }
//            });
//
//
//            Button bb=(Button)convertView.findViewById(R.id.abs);
//            bb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    View parentRow = (View) v.getParent();
//
//                    ListView listView = (ListView) parentRow.getParent();
//
//                    final int position = listView.getPositionForView(parentRow);
//
//                    rollString = rollList.get(position).toString();
//                    statusString = "A";
//                    new Takeattendence().execute();
//
//
//                    Toast.makeText(StudentsData.this,"Clicked",Toast.LENGTH_LONG).show();
//                }
//            });

            CheckBox c=(CheckBox)convertView.findViewById(R.id.pre);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
//                        Toast.makeText(FacultyActivation.this,
//                                "Bro, try Android :)", Toast.LENGTH_LONG).show();
                        View parentRow = (View) v.getParent();

                    ListView listView = (ListView) parentRow.getParent();

                    final int position = listView.getPositionForView(parentRow);

                    rollString = rollList.get(position).toString();
                    statusString = "A";
                    new Takeattendence().execute();

                    }

                }
            });

            TextView rollText = (TextView) convertView.findViewById(R.id.Vroll);
            TextView nameText = (TextView) convertView.findViewById(R.id.Vname);
            TextView branchText = (TextView) convertView.findViewById(R.id.Vbranch);
            TextView sectionText = (TextView) convertView.findViewById(R.id.Vsection);
            TextView yearText = (TextView) convertView.findViewById(R.id.Vyear);

            rollText.setText("Roll No" + "\n" + rollList.get(position));
            nameText.setText("Name" + "\n" + nameList.get(position));
            branchText.setText("Branch" + "\n" + branchList.get(position));
            sectionText.setText("Section" + "\n" + sectionList.get(position));
            yearText.setText("Year" + "\n" + yearList.get(position));


            return convertView;
        }
    }


    public class Takeattendence extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(StudentsData.this);
            pDialog.setMessage("Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("rollno", rollString));
            params.add(new BasicNameValuePair("status", statusString));


            // getting JSON Object
            // Note that create product url accepts POST method


            JSONObject jsonone = jsonParser.makeHttpRequest(url_create_attendence,
                    "POST", params);
            // check log cat fro response
            //	Log.d("Create Response", json.toString());


            return null;
        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done


            pDialog.dismiss();


        }
    }

    //
    public void Dial() {
        Dialog d = new Dialog(StudentsData.this);
        d.setContentView(R.layout.dialog);
        d.setTitle("     Take Attendance");
        Button present = (Button) d.findViewById(R.id.present);
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusString = "P";
                new Takeattendence().execute();

                Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_LONG).show();
            }
        });

        Button absent = (Button) d.findViewById(R.id.absent);
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusString = "A";
                new Takeattendence().execute();
                Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_LONG).show();
            }
        });

        d.show();

    }
}
