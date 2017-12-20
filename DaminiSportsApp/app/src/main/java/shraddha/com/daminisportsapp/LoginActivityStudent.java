package shraddha.com.daminisportsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 30/10/2017.
 */

public class LoginActivityStudent extends AppCompatActivity {

    Button loginButton;
    EditText emailInput;
    Intent intent;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        loginButton = findViewById(R.id.login_button);
        emailInput = findViewById(R.id.email_input);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailInput.getText().toString();

                if (email.equalsIgnoreCase("true")){


                    new AsyncLogin().execute(email);
                }else if (email.equalsIgnoreCase("false")){
                    Toast.makeText(LoginActivityStudent.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }else if (email.equalsIgnoreCase("exception") || email.equalsIgnoreCase("unsuccessfull")){
                    Toast.makeText(LoginActivityStudent.this, "Oops!! Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    private class AsyncLogin extends AsyncTask<String, String , String> {

        ProgressDialog progressDialog = new ProgressDialog(LoginActivityStudent.this);
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
                URL url = new URL("http://11f2a900.ngrok.io/damini/studentlogin.php");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(READ_TIMEOUT);
                httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("email",  strings[0]);
                String query = builder.build().getEncodedQuery();

                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(out, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                out.close();
                httpURLConnection.connect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                int response_code = httpURLConnection.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                httpURLConnection.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("String from Doin", s);
            if (s.equals("successful")){
                intent = new Intent(LoginActivityStudent.this , EditStudentRecordActivity.class);
                startActivity(intent);
            }

        }
    }

}
