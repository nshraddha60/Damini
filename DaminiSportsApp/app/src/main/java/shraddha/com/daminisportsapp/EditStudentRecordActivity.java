package shraddha.com.daminisportsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.shraddha.validator.Validator;

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

import static shraddha.com.daminisportsapp.LoginActivityStudent.CONNECTION_TIMEOUT;
import static shraddha.com.daminisportsapp.LoginActivityStudent.READ_TIMEOUT;

public class EditStudentRecordActivity extends AppCompatActivity {

    Button saveChangesButton;
    ListView editIndoorListView, editOutdoorListView, editAthleticsListView;
    EditText editStudentName,editMobileNumber, editStudentEmail;
    String studName,collegeName,mob,email,bg;
    ArrayAdapter<String> indoorAdapter, outdoorAdapter,athleticsAdapter;


    Spinner collegeNames;
    Spinner bloodGroups;
    SpinnerAdapter collegeAdapter, bloodGroupAdapter;
    HttpURLConnection httpURLConnection;
    String currentUseremail;
    ArrayList<String> selectedIndoorItems;
    ArrayList<String> selectedOutdoorItems;
    ArrayList<String> selectedAthleticsItems;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_record);

        currentUseremail = getIntent().getExtras().getString("UserName",null);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        editStudentName =  findViewById(R.id.teacher_name);
        editMobileNumber = findViewById(R.id.teacher_mobile_no);
        editStudentEmail = findViewById(R.id.teacher_email_edit);

        saveChangesButton = findViewById(R.id.save_changes_button);

        collegeNames = findViewById(R.id.college_name_spinner);
        bloodGroups = findViewById(R.id.blood_group_spinner);

        editIndoorListView = findViewById(R.id.edit_indoor_list);
        editOutdoorListView = findViewById(R.id.edit_outdoor_list);
        editAthleticsListView = findViewById(R.id.edit_athletics_list);
        if(currentUseremail != null){
            new HttpCallFunction().execute(currentUseremail);
        }
        getData();

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()){
                    try {
                        submitForm();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(EditStudentRecordActivity.this, "This form contains error.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void getData(){
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

        selectedIndoorItems = new ArrayList<String>();
        selectedOutdoorItems = new ArrayList<>();
        selectedAthleticsItems = new ArrayList<String>();

    }


        class HttpCallFunction extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL("http://damini.bnca.ac.in/studentinfo.php");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setReadTimeout(READ_TIMEOUT);
                    httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);
                    email = "email="+strings[0];

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
            protected void onPostExecute(String studentInfo) {
                super.onPostExecute(studentInfo);
                try {
                    JSONObject studentInfoObj = new JSONObject(studentInfo);
                    editStudentName.setText(studentInfoObj.getString("student_name"));
                    editMobileNumber.setText(studentInfoObj.getString("mobile_no"));
                    editStudentEmail.setText(studentInfoObj.getString("email"));
                    String [] tempArrayOfBolodGroups =  getResources().getStringArray(R.array.blood_groups);
                   for (int i =0; i< tempArrayOfBolodGroups.length; i++){
                       if(tempArrayOfBolodGroups[i].contains(studentInfoObj.getString("blood_grp"))){
                           bloodGroups.setSelection(i);
                           break;
                       }
                   }

                    String [] tempArrayOfColleges =  getResources().getStringArray(R.array.colleges_list);
                    for (int i =0; i< tempArrayOfColleges.length; i++){
                        if(tempArrayOfColleges[i].contains(studentInfoObj.getString("college_name"))){
                            collegeNames.setSelection(i);
                            break;
                        }
                    }

                    JSONArray indoorGames = studentInfoObj.getJSONArray("indoor_games");
                    final String[] indoorGamesAllList= getResources().getStringArray(R.array.indoor_sports_list);
                    if(indoorGames.length() >0){
                        for(int i=0;i< indoorGames.length();i++){
                                for( int j=0; j< indoorGamesAllList.length; j++){
                                    if(indoorGames.getString(i).equals(indoorGamesAllList[j])){
                                        editIndoorListView.setItemChecked(j, true);
                                        break;
                                    }
                                }


                        }
                    }

                    JSONArray outdoorGames = studentInfoObj.getJSONArray("outdoor_games");
                    final String[] outdoorGamesAllList= getResources().getStringArray(R.array.outdoor_sports_list);
                    if(outdoorGames.length() >0){
                        for(int i=0;i< outdoorGames.length();i++){
                            for( int j=0; j< outdoorGamesAllList.length; j++){
                                if(outdoorGames.getString(i).equals(outdoorGamesAllList[j])){
                                    editOutdoorListView.setItemChecked(j, true);
                                    break;
                                }
                            }


                        }
                    }

                    JSONArray athleticGames = studentInfoObj.getJSONArray("athletic_games");
                    final String[] athleticGamesAllList= getResources().getStringArray(R.array.athletics);
                    if(athleticGames.length() >0){
                        for(int i=0;i< athleticGames.length();i++){
                            for( int j=0; j< athleticGamesAllList.length; j++){
                                if(athleticGames.getString(i).equals(athleticGamesAllList[j])){
                                    editAthleticsListView.setItemChecked(j, true);
                                    break;
                                }
                            }


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    public String getEditedData() {
        studName = editStudentName.getText().toString();

        collegeName = collegeNames.getSelectedItem().toString();

        mob = editMobileNumber.getText().toString();

        email = editStudentEmail.getText().toString();

        bg = bloodGroups.getSelectedItem().toString();

        JSONObject studentRecordUpdateObject = new JSONObject();

        try {
            studentRecordUpdateObject.put("student_name", studName);
            studentRecordUpdateObject.put("college_name", collegeName);
            studentRecordUpdateObject.put("email", email);
            studentRecordUpdateObject.put("mobile_number", mob);
            studentRecordUpdateObject.put("blood_group", bg);

            JSONArray indoorGamesArray = new JSONArray();

            for (int i = 0; i < selectedIndoorItems.size(); i++) {
                indoorGamesArray.put(selectedIndoorItems.get(i));
            }

            studentRecordUpdateObject.put("indoor_games", indoorGamesArray);

            JSONArray outdoorGamesArray = new JSONArray();

            for (int i = 0; i < selectedOutdoorItems.size(); i++) {
                outdoorGamesArray.put(selectedOutdoorItems.get(i));
            }

            studentRecordUpdateObject.put("outdoor_games", outdoorGamesArray);

            JSONArray athleticsGamesArray = new JSONArray();

            for (int i = 0; i < selectedAthleticsItems.size(); i++) {
                athleticsGamesArray.put(selectedAthleticsItems.get(i));
            }

            studentRecordUpdateObject.put("athletic_games", athleticsGamesArray);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentRecordUpdateObject.toString();

    }

    private void submitForm() throws ProtocolException {

        String params = "college_reg_param="+getEditedData();
        URL obj = null;
        try {
            obj = new URL(" http://damini.bnca.ac.in/updatestudentdata.php");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        OutputStream os = null;
        try {
            os = con.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            os.write(params.getBytes());
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

        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
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
            if (response.toString().contains("reg_success")) {
                Toast.makeText(this, "You have registered successfully.", Toast.LENGTH_SHORT).show();
                intent = new Intent(EditStudentRecordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
            }


        } else {
            System.out.println("POST request not worked");
        }
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validator.hasText(editStudentName)) ret = false;
        if (!Validator.isEmailAddress(editStudentEmail, true)) ret = false;
        if (!Validator.isPhoneNumber(editMobileNumber, false)) ret = false;

        return ret;
    }
}
