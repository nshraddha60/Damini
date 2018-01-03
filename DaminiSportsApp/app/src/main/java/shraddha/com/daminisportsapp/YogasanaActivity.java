package shraddha.com.daminisportsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class YogasanaActivity extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yogasana);

        pdfView = findViewById(R.id.yogasana_pdf);
        pdfView.fromAsset("yogasana.pdf").load();
    }
}
