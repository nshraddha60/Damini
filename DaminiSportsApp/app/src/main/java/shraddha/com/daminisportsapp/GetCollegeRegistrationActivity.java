package shraddha.com.daminisportsapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetCollegeRegistrationActivity extends AppCompatActivity {

    ListView registeredCollegeList;
    String result = null;
    InputStream inputStream = null;
    String[] collegeNames;
   // String [] collegeInfo = {"teacherName", "collegeName", "email", "mobileNo", "indoorGames", "outdoorGames"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_college_registration);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        registeredCollegeList = (ListView) findViewById(R.id.list_colleges);
        getUrlConnection("http://10.0.2.2/damini/getcollegeinfo.php");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GetCollegeRegistrationActivity.this, android.R.layout.simple_list_item_1, collegeNames);
        registeredCollegeList.setAdapter(arrayAdapter);
    }

    private void getUrlConnection(final String urlWebService){
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    String json;
                    while ((json = bufferedReader.readLine()) != null){
                        stringBuilder.append(json + "\n");
                    }
                    inputStream.close();
                    result = stringBuilder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


        try {


            //String str="[{\"teacher_name\":\"abcd\"},{\"teacher_name\":\"wxyz\"}]";
            JSONArray jsonArray = new JSONArray(result);

            collegeNames = new String[jsonArray.length()];

            for (int id=0 ; id<jsonArray.length() ; id++){

                JSONObject jsonObject = jsonArray.getJSONObject(id);
                Log.i("ssdf","");
                collegeNames[id] = jsonObject.getString("teacher_name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
