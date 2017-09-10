package com.biet.collegemanagementsystem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class FacultyActivation extends ActionBarActivity {


    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray response;
    private static final String TAG_SUCCESS = "success";

    private static String url_getfacultydetails = "http://www.inspireitlabs.com/CollegemanagementSystem/getfacultyDetails.php";

    private static String url_status = "http://www.inspireitlabs.com/CollegemanagementSystem/activation.php";


    ArrayList<String> idArray = new ArrayList<String>();

    ArrayList<String> detailsArray = new ArrayList<String>();

    ArrayList<String> fidlist = new ArrayList<String>();
    ArrayList<String> fnameList = new ArrayList<String>();
    ArrayList<String> fqualificationlist = new ArrayList<String>();
    ArrayList<String> fdepartlist = new ArrayList<String>();
//    ArrayList<String> fcourselist = new ArrayList<String>();
//    ArrayList<String> fbranchlist = new ArrayList<String>();

    ListView list;

    String selectedFaculty, statusString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_activation);


        list = (ListView) findViewById(R.id.statuslist);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedFaculty = fidlist.get(position).toString();
                //Dial();

                new Activation().execute();
            }
        });


        new Myfacultydetails().execute();
    }

    public class Myfacultydetails extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(FacultyActivation.this);
            pDialog.setMessage("Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {

            try {

                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("",""));

                // getting JSON Object
                // Note that create product url accepts POST method
//				JSONObject json = jsonParser.makeHttpRequest(url_create_product,
//						"POST", params);

                JSONObject jsonone = jsonParser.makeHttpRequest(url_getfacultydetails,
                        "GET", params);
                // check log cat fro response
                //	Log.d("Create Response", json.toString());

                // check for success tag


                // failed to create product

                response = jsonone.getJSONArray("studentdetails");

                // looping through All Products
                for (int i = 0; i < response.length(); i++) {
                    JSONObject c = response.getJSONObject(i);

                    String id = c.getString("fid");
                    String name = c.getString("fname");
                    String qua = c.getString("qualification");
                    String department = c.getString("depart");
//                    String course = c.getString("course");
//                    String branch = c.getString("branch");

//
//                    idArray.add(id);
//                    detailsArray.add("Name:  " + name + "Qualification:  " + qua + "\nDepartment: " + department
//                            + "\nCourse: " + course + "\nBranch:  " + branch);


                    fidlist.add(id);
                    fnameList.add(name);
                    fqualificationlist.add(qua);
                    fdepartlist.add(department);
//                    fcourselist.add(course);
//                    fbranchlist.add(branch);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (fidlist.size() > 0) {
                //    ArrayAdapter<String> jsondata = new ArrayAdapter<String>(StudentsData.this, android.R.layout.simple_list_item_1, aa);

                list.setAdapter(new Mydatadisplay());
            }

            //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(FacultyActivation.this, android.R.layout.simple_list_item_1, detailsArray);

            //  list.setAdapter(adapter);
            pDialog.dismiss();
        }
    }

    public class Activation extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(FacultyActivation.this);
            pDialog.setMessage("Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Fid", selectedFaculty));

            // getting JSON Object
            // Note that create product url accepts POST method
//				JSONObject json = jsonParser.makeHttpRequest(url_create_product,
//						"POST", params);

            JSONObject jsonone = jsonParser.makeHttpRequest(url_status,
                    "POST", params);
            // check log cat fro response
            //	Log.d("Create Response", json.toString());
            return null;
        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done


//            ArrayAdapter<String> jsondata = new ArrayAdapter<String>(FINDSTUDENTVIEW.this, android.R.layout.simple_list_item_1, aa);
//
//            list.setAdapter(jsondata);

           // result.setText(currentstudent);

            pDialog.dismiss();


        }

    }
//    public void Dial() {
//        Dialog d = new Dialog(FacultyActivation.this);
//        d.setContentView(R.layout.dialog);
//        d.setTitle("     Approval");
//        Button present = (Button) d.findViewById(R.id.present);
//        present.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             // statusString = "P";
//
//                new Activation().execute();
//                Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        Button absent = (Button) d.findViewById(R.id.absent);
//        absent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  statusString = "A";
//                new Activation().execute();
//                Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        d.show();
//
//    }


    public class Mydatadisplay extends BaseAdapter {

        @Override
        public int getCount() {
            return fidlist.size();
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

            convertView = getLayoutInflater().inflate(R.layout.facdisplayng, null);

//            Button p = (Button) convertView.findViewById(R.id.pre);
//            p.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    View parentRow = (View) v.getParent();
//
//                    ListView listView = (ListView) parentRow.getParent();
//
//                    final int position = listView.getPositionForView(parentRow);
//                    selectedFaculty = fidlist.get(position).toString();
//                    // statusString = "P";
//                    new Activation().execute();
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
                    selectedFaculty = fidlist.get(position).toString();
                    // statusString = "P";
                    new Activation().execute();

                    }

                }
            });
            TextView rollText = (TextView) convertView.findViewById(R.id.Vroll);
            TextView nameText = (TextView) convertView.findViewById(R.id.Vname);
            TextView branchText = (TextView) convertView.findViewById(R.id.Vbranch);
            TextView sectionText = (TextView) convertView.findViewById(R.id.Vsection);


            rollText.setText("ID" + "\n" +fidlist.get(position));
            nameText.setText("Name" + "\n" + fnameList.get(position));
            branchText.setText("Qualification" + "\n" + fqualificationlist.get(position));
            sectionText.setText("Department" + "\n" + fdepartlist.get(position));



            return convertView;
        }
    }
}

