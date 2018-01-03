package shraddha.com.daminisportsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class SuryanamaskarActivity extends AppCompatActivity {

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suryanamaskar);

        pdfView = findViewById(R.id.suryanamaskar_pdf);
        pdfView.fromAsset("suryanamaskar.pdf").load();
    }
}
