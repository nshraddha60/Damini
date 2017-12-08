package shraddha.com.daminisportsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button collegeRegistration, studentRegistration;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        collegeRegistration = (Button) findViewById(R.id.show_college_registration_button);
        studentRegistration = (Button) findViewById(R.id.show_student_registration_button);

        collegeRegistration.setOnClickListener(this);
        studentRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show_college_registration_button:
                intent = new Intent(this, GetCollegeRegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.show_student_registration_button:
                break;
        }

    }
}
