package shraddha.com.daminisportsapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

   // private static final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS =0;
    Button registerButton, loginButton;
    final CharSequence[] options ={"College Registration", "Student Registration"};
    final CharSequence[] options1 = {"College Login", "Student Login"};
    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        registerButton = (Button) findViewById(R.id.register_button);
        loginButton = (Button) findViewById(R.id.edit_button);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.schedule:
                intent = new Intent(this, EventActivity.class);
                startActivity(intent);
                break;
            case R.id.draws:
                intent = new Intent(this, DrawsActivity.class);
                startActivity(intent);
                break;
            case R.id.photos:
                intent = new Intent(this, PhotosActivity.class);
                startActivity(intent);
                break;
            case R.id.awards:
                intent = new Intent(this, AwardsActivity.class);
                startActivity(intent);
                break;
            case R.id.admin_login:
                intent = new Intent(this, AdminLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.about_damini:
                intent = new Intent(this, AboutDaminiActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_button){
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Choose your type");
            alertBuilder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int name) {
                    if (options[name] == options[0]){
                        Log.i("College registration", "College Registration");
                        intent = new Intent(getApplicationContext(), CollegeRegistrationActivity.class);
                        startActivity(intent);
                        finish();

                    }else if (options[name] == options[1]){
                        Log.i("Student registration", "Student Registration");
                        intent = new Intent(getApplicationContext(), StudentRegistrationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }
        else if(view.getId() == R.id.edit_button){
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Choose your type");
            alertBuilder.setItems(options1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int name) {
                    if (options1[name] == options1[0]){
                        Log.i("College Login", "College Login");
                        intent = new Intent(getApplicationContext(), CollegeLoginActivity.class);
                        startActivity(intent);

                    }else if (options1[name] == options1[1]){
                        Log.i("Student Login", "Student Login");
                        intent = new Intent(getApplicationContext(), LoginActivityStudent.class);
                        startActivity(intent);

                    }
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }
    }
}
