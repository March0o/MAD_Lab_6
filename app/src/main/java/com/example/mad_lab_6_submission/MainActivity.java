package com.example.mad_lab_6_submission;

import android.app.Activity;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;
//  Sources used for help
//  https://stackoverflow.com/questions/16080181/qr-code-reading-with-camera-android
//  https://github.com/zxing/zxing/wiki/Scanning-Via-Intent


public class MainActivity extends AppCompatActivity {

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
        etTitle = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
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
            // "title":"AtuSLigo","address":"http:"
            Toast.makeText(this, "Scan Successful", Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, "Scan Unsuccessful", Toast.LENGTH_LONG).show();
        }

    }

}