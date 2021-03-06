package shraddha.com.daminisportsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CollegeLoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText emailInput;
    Intent intent;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        loginButton = findViewById(R.id.login_button);
        emailInput = findViewById(R.id.email_input);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailInput.getText().toString();

                if (email.trim().isEmpty() == false){
                    new CollegeLoginActivity.AsyncLogin().execute(email);
                }else {
                    Toast.makeText(CollegeLoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private class AsyncLogin extends AsyncTask<String, String , String> {

        ProgressDialog progressDialog = new ProgressDialog(CollegeLoginActivity.this);
        HttpURLConnection httpURLConnection;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://10.0.2.2/damini/collegelogin.php");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(READ_TIMEOUT);
                httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                String email = "email="+strings[0];



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
            progressDialog.dismiss();
            if (s.equals("true")){
                intent = new Intent(CollegeLoginActivity.this , EditStudentRecordActivity.class);
                intent.putExtra("UserName",emailInput.getText().toString().trim());
                startActivity(intent);
            }

        }
    }

}
