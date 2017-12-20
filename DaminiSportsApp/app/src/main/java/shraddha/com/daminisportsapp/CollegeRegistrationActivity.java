package shraddha.com.daminisportsapp;

import android.content.Intent;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class CollegeRegistrationActivity extends AppCompatActivity {

    Button submitButton;
    EditText teacherName, teacherEmail, teacherMobile;
    Spinner collegeSpinner;
    SpinnerAdapter collegeAdapter;
    ListView indoorListView, outdoorListView, athleticsListView;
    ArrayAdapter<String> indoorAdapter, outdoorAdapter, athleticsAdapter;
    Intent intent;
    String sportsTeacherName, itemCollegeName,mob,email;
    String indoorGames="",outdoorGames="", athleticsGames="";


    ArrayList<String> selectedIndoorItems;
    ArrayList<String> selectedOutdoorItems;
    ArrayList<String> selectedAthleticsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_registration);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        teacherName = (EditText) findViewById(R.id.teacher_name_input);
        teacherEmail = (EditText) findViewById(R.id.teacher_email_input);
        teacherMobile = (EditText) findViewById(R.id.mobile_no_input);

        teacherName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validator.hasText(teacherName);
            }
        });

        teacherEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validator.hasText(teacherEmail);
            }
        });

        teacherMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validator.hasText(teacherMobile);
            }
        });
        collegeSpinner = (Spinner) findViewById(R.id.college_spinner);

        submitButton = (Button) findViewById(R.id.submit_button);



        indoorListView = (ListView) findViewById(R.id.indoor_games_list);
        outdoorListView = (ListView) findViewById(R.id.outdoor_games_list);
        athleticsListView = (ListView) findViewById(R.id.athletics_list);

        String[] indoorSports = getResources().getStringArray(R.array.indoor_sports_list);
        String[] outdoorSports = getResources().getStringArray(R.array.outdoor_sports_list);
        String[] athletics = getResources().getStringArray(R.array.athletics);

        indoorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,indoorSports);
        indoorListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        indoorListView.setAdapter(indoorAdapter);

        outdoorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,outdoorSports);
        outdoorListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        outdoorListView.setAdapter(outdoorAdapter);

        athleticsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,athletics);
        athleticsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        athleticsListView.setAdapter(athleticsAdapter);


        String[] collegeNames = getResources().getStringArray(R.array.colleges_list);

        collegeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,collegeNames);
        collegeSpinner.setAdapter(collegeAdapter);


        selectedIndoorItems = new ArrayList<String>();
        selectedOutdoorItems = new ArrayList<String>();
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



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()){
                    try {
                        submitform();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(CollegeRegistrationActivity.this, "This form contains error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void getData(){
        sportsTeacherName = teacherName.getText().toString();
        itemCollegeName = collegeSpinner.getSelectedItem().toString();
        mob = teacherMobile.getText().toString();
        email = teacherEmail.getText().toString();

        for (String indoorEvents : selectedIndoorItems) {
            indoorGames += indoorEvents  + ","+"\t";
        }
        for (String outdoorEvents : selectedOutdoorItems) {
            outdoorGames += outdoorEvents +","+ "\t";
        }

        for (String athleticEvents : selectedAthleticsItems) {
            athleticsGames += athleticEvents + "\t";
        }

            Log.i("inserted", "Data is inserted...");
            Toast.makeText(this, "You have registered successfully", Toast.LENGTH_SHORT).show();
            intent = new Intent(CollegeRegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

    }

    private void submitform() throws ProtocolException{
        getData();
        String params = null;

        params = "tn="+ sportsTeacherName+"&cn="+itemCollegeName+"&mob="+mob+"&email="+email+"&ing="+indoorGames+"&outg="+outdoorGames+"&ath="+athleticsGames;


        URL obj = null;
        try {
            obj = new URL("http://damini.bnca.ac.in/collegeregistration.php");
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
        //con.setConnectTimeout(1500000);

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
                intent = new Intent(CollegeRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        } else {
            System.out.println("POST request not worked");
        }
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validator.hasText(teacherName)) ret = false;
        if (!Validator.isEmailAddress(teacherEmail, true)) ret = false;
        if (!Validator.isPhoneNumber(teacherMobile, false)) ret = false;

        return ret;
    }
}


