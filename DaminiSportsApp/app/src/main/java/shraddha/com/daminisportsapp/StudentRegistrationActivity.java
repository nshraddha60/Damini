package shraddha.com.daminisportsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.shraddha.validator.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class StudentRegistrationActivity extends AppCompatActivity{
    Button submitButton;
    ListView indoorListView, outdoorListView, athleticsListView;
    EditText studentName,mobileNumber, studentEmail;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        Log.i("oncreate", "in onCreate...");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        studentName = (EditText) findViewById(R.id.student_name_input);
        mobileNumber = (EditText) findViewById(R.id.mobile_no_input);
        studentEmail = (EditText) findViewById(R.id.student_email_input);

        studentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validator.hasText(studentName);
            }
        });
        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validator.hasText(mobileNumber);
            }
        });
        studentEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Validator.hasText(studentEmail);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submitButton = (Button) findViewById(R.id.submit_button);


        collegeNames = (Spinner) findViewById(R.id.college_spinner);
        bloodGroups = (Spinner) findViewById(R.id.blood_group_spinner);

        indoorListView = (ListView) findViewById(R.id.indoor_games_list);
        outdoorListView = (ListView) findViewById(R.id.outdoor_games_list);
        athleticsListView = (ListView) findViewById(R.id.athletics_list);

        final String[] indoorSports = getResources().getStringArray(R.array.indoor_sports_list);
        final String[] outdoorSports = getResources().getStringArray(R.array.outdoor_sports_list);
        final String[] athletics = getResources().getStringArray(R.array.athletics);

        indoorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,indoorSports);
        indoorListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        indoorListView.setAdapter(indoorAdapter);

        outdoorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,outdoorSports);
        outdoorListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        outdoorListView.setAdapter(outdoorAdapter);

        athleticsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,athletics);
        athleticsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        athleticsListView.setAdapter(athleticsAdapter);


        String[] colleges = getResources().getStringArray(R.array.colleges_list);
        final String[] bloodGroup = getResources().getStringArray(R.array.blood_groups);

        collegeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,colleges);
        collegeNames.setAdapter(collegeAdapter);

        bloodGroupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bloodGroup);
        bloodGroups.setAdapter(bloodGroupAdapter);

        selectedIndoorItems = new ArrayList<String>();
        selectedOutdoorItems = new ArrayList<>();
        selectedAthleticsItems = new ArrayList<String>();


        outdoorListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String checkedValue = (String) outdoorListView.getItemAtPosition(position);
                if (selectedOutdoorItems.contains(checkedValue)){
                    selectedOutdoorItems.remove(checkedValue);
                }
                else {
                    selectedOutdoorItems.add(checkedValue);
                }
            }

        });

        indoorListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String checkedValue = (String) indoorListView.getItemAtPosition(position);
                if (selectedIndoorItems.contains(checkedValue)){
                    selectedIndoorItems.remove(checkedValue);
                }
                else {
                    selectedIndoorItems.add(checkedValue);
                }
            }

        });

        athleticsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String checkedValue = (String) athleticsListView.getItemAtPosition(position);
                if (selectedAthleticsItems.contains(checkedValue)){
                    selectedAthleticsItems.remove(checkedValue);
                }
                else {
                    selectedAthleticsItems.add(checkedValue);
                }
            }
        });




        collegeNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                itemCollegeName = adapter.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bloodGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                itemBloodGroup = adapter.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()){
                    try {
                        submitForm();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(StudentRegistrationActivity.this, "This form contains error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(){
        studName = studentName.getText().toString();

        collegeName= collegeNames.getSelectedItem().toString();

        mob= mobileNumber.getText().toString();

        email = studentEmail.getText().toString();

        bg = bloodGroups.getSelectedItem().toString();

        for (String indoorEvents : selectedIndoorItems) {
            indoorGames += indoorEvents  + ","+"\t";
        }
        for (String outdoorEvents : selectedOutdoorItems) {
            outdoorGames += outdoorEvents + ","+"\t";
        }

        for (String athleticEvents : selectedAthleticsItems) {
            athleticsGames += athleticEvents + ","+"\t";
        }


    }

    private void submitForm() throws ProtocolException {
        getData();
        String params = null;

            params = "sn="+ studName+"&cn"+itemCollegeName+"&mob="+mob+"&email="+email+"&bg="+itemBloodGroup+"&ing="+indoorGames+"&outg="+outdoorGames+"&ath="+athleticsGames;


        URL obj = null;
        try {
            obj = new URL("http://damini.bnca.ac.in/registration.php");
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

        // For POST only - START
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
        // For POST only - END

        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
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
            System.out.println(response.toString());
            if(response.toString().contains("reg_success")){
                Toast.makeText(this, "You have registered successfully", Toast.LENGTH_SHORT).show();
                intent = new Intent(StudentRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        } else {
            System.out.println("POST request not worked");
        }
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validator.hasText(studentName)) ret = false;
        if (!Validator.isEmailAddress(studentEmail, true)) ret = false;
        if (!Validator.isPhoneNumber(mobileNumber, false)) ret = false;

        return ret;
    }
}
