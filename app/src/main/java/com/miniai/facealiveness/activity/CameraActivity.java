package com.miniai.facealiveness.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.miniai.facealiveness.R;
import com.miniai.facealiveness.util.BitmapHolder;

import java.io.IOException;

public class CameraActivity extends Activity {
    private Camera mCamera;
    private SurfaceView mCameraPreview;
    private Button mCaptureButton;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCameraPreview = findViewById(R.id.camera_preview);
        mCaptureButton = findViewById(R.id.btn_capture);

        // Check if camera permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Camera permission is already granted
            initializeCamera();
        }

        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture the image and pass it to the next activity
                mCamera.takePicture(null, null, mPictureCallback);
            }
        });
    }

    private void initializeCamera() {
        // Open the front camera by specifying the camera ID
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mCamera.setDisplayOrientation(90);
        mCameraPreview.getHolder().addCallback(new CameraSurfaceCallback());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, initialize the camera
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                initializeCamera();
            } else {
                // Camera permission denied, handle accordingly (e.g., show a message)
            }
        }
    }

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // Convert the captured image data to a Bitmap
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 270; // Rotate 90 degrees clockwise
                    break;
                case Surface.ROTATION_90:
                    degrees = 0;   // No rotation
                    break;
                case Surface.ROTATION_180:
                    degrees = 90;  // Rotate 90 degrees counterclockwise
                    break;
                case Surface.ROTATION_270:
                    degrees = 180; // Rotate 180 degrees
                    break;
            }

            // Rotate the capturedBitmap to the correct orientation
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            Bitmap capturedBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap rotatedBitmap = Bitmap.createBitmap(capturedBitmap, 0, 0, capturedBitmap.getWidth(), capturedBitmap.getHeight(), matrix, true);

            BitmapHolder.getInstance().setCapturedBitmap(rotatedBitmap);

            // Start the next activity and pass the captured bitmap
            Intent intent = new Intent(CameraActivity.this, SingleImageActivity.class);
            intent.putExtra("captured_bitmap", "capturedBitmap");
            startActivity(intent);

            // Release the camera and finish this activity
            mCamera.release();
            finish();
        }
    };

    private class CameraSurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // Handle surface changes if needed
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // Release the camera when the surface is destroyed
            mCamera.release();
        }
    }
}
