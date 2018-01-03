package shraddha.com.daminisportsapp;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;


public class EventActivity extends AppCompatActivity {

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        pdfView = findViewById(R.id.pdfViewer);
        pdfView.fromAsset("schedule2018.pdf").load();
    }
}
