package com.example.mad_lab_6_submission;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.integration.android.IntentIntegrator;
import java.io.IOException;

//  Sources used for help
//  https://stackoverflow.com/questions/16080181/qr-code-reading-with-camera-android Using QR
//  https://github.com/zxing/zxing/wiki/Scanning-Via-Intent Open Scan Intent
//  https://stackoverflow.com/questions/69134922/google-chrome-browser-in-android-12-emulator-doesnt-load-any-webpages-internet/78309469 Be able to open chrome

public class MainActivity extends AppCompatActivity {
    //  Initialize EditTexts
    EditText etTitle = null;
    EditText etAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnScan), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //  Assign EditTexts
        etTitle = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
    }
    //  Source of code for Opening URL
    //  https://stackoverflow.com/questions/65547828/java-open-a-url-from-a-string
    public void GoToSite(View v) throws IOException {
        String url = etAddress.getText().toString(); // Get EditText
        Intent i2=new Intent(Intent.ACTION_VIEW, Uri.parse(url)); // Create intent with url
        startActivity(i2); // Start Activity
    }

    public void ScanQR(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this); // Pass current Activity to intent integrator
        integrator.initiateScan(); // Start Scan
    }

    @Override // This Method is called after Scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode,resultCode,intent);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            Toast.makeText(this, "Scan Successful", Toast.LENGTH_LONG).show(); // Success Message
            //  Split Result into two values and get said values
            String[] scanArray = scanResult.getContents().split(",");
            String titleResult = scanArray[0].split(":")[1];
            String addressResult = scanArray[1].split(":")[1] + ":" + scanArray[1].split(":")[2];
            // Set Text to trimmed values
            etTitle.setText(titleResult.substring(2,titleResult.length()-1));
            etAddress.setText(addressResult.substring(2,addressResult.length()-2));
        }
        else
        {
            Toast.makeText(this, "Scan Unsuccessful", Toast.LENGTH_LONG).show(); // Bad Message
        }

    }

}