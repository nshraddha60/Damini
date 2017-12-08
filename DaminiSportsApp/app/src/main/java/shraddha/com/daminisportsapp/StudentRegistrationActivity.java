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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StudentRegistrationActivity extends AppCompatActivity{
    Button submitButton;
    ListView indoorListView, outdoorListView, athleticsListView;
    EditText studentName,mobileNumber, studentEmail;
    String studName,collegeName,mob,email,bg;
    String indoorGames,outdoorGames,athleticsGames;
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
                    submitForm();;
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


        Log.i("inserted","Data is inserted...");
        Toast.makeText(this, "You have registered successfully", Toast.LENGTH_SHORT).show();
        intent = new Intent(StudentRegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    private void submitForm(){
        getData();
       String dataString = "sn=\"" +studName+"\"&cn=\""+itemCollegeName+"\"&mob="+mob+"&email=\""+email+"\"&bg=\""+itemBloodGroup+"\"&ing=\""+indoorGames+"\"&outg=\""+outdoorGames+"\"&ath=\""+athleticsGames+"\"";

        try{


            URL url = new URL("http://damini.bnca.ac.in/registration.php?"+dataString);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            InputStream in = urlConnection.getInputStream();


            InputStreamReader isw = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isw);
            String data = null;
            while ((data = reader.readLine())!= null){
                Log.i("Line",data);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
