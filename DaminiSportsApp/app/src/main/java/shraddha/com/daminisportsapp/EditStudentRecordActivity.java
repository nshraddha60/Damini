package shraddha.com.daminisportsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static shraddha.com.daminisportsapp.LoginActivityStudent.CONNECTION_TIMEOUT;
import static shraddha.com.daminisportsapp.LoginActivityStudent.READ_TIMEOUT;

public class EditStudentRecordActivity extends AppCompatActivity {

    Button saveChangesButton;
    ListView editIndoorListView, editOutdoorListView, editAthleticsListView;
    EditText editStudentName,editMobileNumber, editStudentEmail;
    String studName,collegeName,mob,email,bg;
    String indoorGames = "",outdoorGames ="",athleticsGames="";
    ArrayAdapter<String> indoorAdapter, outdoorAdapter,athleticsAdapter;


    Spinner collegeNames;
    Spinner bloodGroups;
    String itemCollegeName, itemBloodGroup;
    SpinnerAdapter collegeAdapter, bloodGroupAdapter;
    Intent intent;
    ArrayList<String> selectedIndoorItems;
    ArrayList<String> selectedOutdoorItems;
    ArrayList<String> selectedAthleticsItems;
    String tempIntent;
    HashMap<String, String> resultHashMap = new HashMap<>();
    String finalJsonObject;
    HttpURLConnection httpURLConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_record);


        Log.i("oncreate", "in onCreate...");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        editStudentName =  findViewById(R.id.student_name);
        editMobileNumber = findViewById(R.id.student_mobile_no);
        editStudentEmail = findViewById(R.id.student_email_edit);

        saveChangesButton = findViewById(R.id.save_changes_button);

        collegeNames = findViewById(R.id.college_name_spinner);
        bloodGroups = findViewById(R.id.blood_group_spinner);

        editIndoorListView = findViewById(R.id.edit_indoor_list);
        editOutdoorListView = findViewById(R.id.edit_outdoor_list);
        editAthleticsListView = findViewById(R.id.edit_athletics_list);

        tempIntent = getIntent().getStringExtra("ListViewValue");

        httpWebCall(tempIntent);



        getData();

    }

    void getData(){
        final String[] indoorSports = getResources().getStringArray(R.array.indoor_sports_list);
        final String[] outdoorSports = getResources().getStringArray(R.array.outdoor_sports_list);
        final String[] athletics = getResources().getStringArray(R.array.athletics);

        indoorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,indoorSports);
        editIndoorListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        editIndoorListView.setAdapter(indoorAdapter);

        outdoorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,outdoorSports);
        editOutdoorListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        editOutdoorListView.setAdapter(outdoorAdapter);

        athleticsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,athletics);
        editAthleticsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        editAthleticsListView.setAdapter(athleticsAdapter);


        String[] colleges = getResources().getStringArray(R.array.colleges_list);
        final String[] bloodGroup = getResources().getStringArray(R.array.blood_groups);

        collegeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,colleges);
        collegeNames.setAdapter(collegeAdapter);

        bloodGroupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bloodGroup);
        bloodGroups.setAdapter(bloodGroupAdapter);
    }

    public void httpWebCall(final String listViewClickedItem){
        class HttpCallFunction extends AsyncTask<String, Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(" http://b00f45ac.ngrok.io/damini/studentlogin.php");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setReadTimeout(READ_TIMEOUT);
                    httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                    httpURLConnection.setRequestMethod("POST");

                    //  httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    String studentName = "name=" + strings[0];
                    String collegeName = "college="+strings[1];
                    String mobileNo = "mobile="+strings[2];
                    String email = "email="+strings[3];
                    String bloodGroup = "bg="+strings[4];
                    OutputStream os = null;
                    try {
                        os = httpURLConnection.getOutputStream();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        os.write(email.getBytes());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        os.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        os.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int responseCode = 0;
                try {
                    responseCode = httpURLConnection.getResponseCode();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("POST Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = null;
                    try {
                        in = new BufferedReader(new InputStreamReader(
                                httpURLConnection.getInputStream()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    try {
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        in.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    // print result
                    return  response.toString();
                }

                return "unsuccessful";

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                finalJsonObject = s;
                new GetHttpResponse(EditStudentRecordActivity.this).execute();

            }
        }

    }
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>{

        public Context context;

        public GetHttpResponse(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (finalJsonObject != null){
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(finalJsonObject);
                    JSONObject jsonObject;

                    for (int i=0 ; i<jsonArray.length() ; i++){
                        jsonObject = jsonArray.getJSONObject(i);

                        studName = jsonObject.getString("name").toString();
                        collegeName = jsonObject.getJSONObject("college name").toString();
                        mob = jsonObject.getString("mobile").toString();
                        email= jsonObject.getString("email").toString();
                       // bg = jsonObject.getString("blood group").toString();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            editStudentName.setText(studName);
            collegeNames.setSelection((Integer) collegeAdapter.getItem(Integer.parseInt(collegeName)));
            editStudentEmail.setText(email);
            editMobileNumber.setText(mob);
        }
    }
}
