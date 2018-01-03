package shraddha.com.daminisportsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RulesActivity extends AppCompatActivity {

    Button yogasanaButton, suryanamskarButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        yogasanaButton = findViewById(R.id.rules_yogasana_button);
        suryanamskarButton = findViewById(R.id.rules_suryanamaskar_button);

        yogasanaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(RulesActivity.this, YogasanaActivity.class);
                startActivity(intent);
            }
        });

        suryanamskarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(RulesActivity.this, SuryanamaskarActivity.class);
                startActivity(intent);
            }
        });
    }
}
