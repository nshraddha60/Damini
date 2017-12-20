package shraddha.com.daminisportsapp;

import android.content.Intent;
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

import java.util.ArrayList;

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
}
