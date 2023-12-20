package com.miniai.facealiveness.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.miniai.facealiveness.R;
import com.miniai.facealiveness.util.BitmapHolder;

public class HomeActivity extends AppCompatActivity {

    Button buttonCapture,buttonprivicypolicy,buttonAbout,buttonAttribute;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CardView buttonCapture = findViewById(R.id.buttonCapture);
        CardView buttonAttribute = findViewById(R.id.buttonAttribute);
        CardView buttonprivicypolicy = findViewById(R.id.buttonprivicypolicy);
        CardView buttonAbout = findViewById(R.id.buttonAbout);

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(mainIntent);
            }
        });

        buttonAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(HomeActivity.this, SingleImageActivity.class);
                startActivity(mainIntent);

            }
        });
        buttonprivicypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(HomeActivity.this, PrivacyPolicyActivity.class);
                startActivity(mainIntent);

            }
        });
        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(HomeActivity.this, CameraActivity.class);
                startActivity(mainIntent);

            }
        });
        // Check if camera permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, initialize the camera
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();

            } else {
                // Camera permission denied, handle accordingly (e.g., show a message)
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BitmapHolder.getInstance().setCapturedBitmap(null);
    }
}