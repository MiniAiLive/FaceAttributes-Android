package com.miniai.facealiveness.activity;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.miniai.Adapter.FaceattributeAdapter;
import com.miniai.face.AgeInfo;
import com.miniai.face.GenderInfo;
import com.miniai.facealiveness.R;
import com.miniai.facealiveness.model.DrawInfo;
import com.miniai.facealiveness.model.FaceAtteributeModle;
import com.miniai.facealiveness.util.ConfigUtil;
import com.miniai.facealiveness.util.DrawHelper;
import com.miniai.facealiveness.util.camera.CameraHelper;
import com.miniai.facealiveness.util.camera.CameraListener;
import com.miniai.facealiveness.util.face.RecognizeColor;
import com.miniai.facealiveness.util.utilhelper;
import com.miniai.facealiveness.widget.FaceRectView;
import com.miniai.face.ErrorInfo;
import com.miniai.face.FaceEngine;
import com.miniai.face.FaceInfo;
import com.miniai.face.LivenessInfo;
import com.miniai.face.enums.DetectMode;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FaceAlivenessActivity extends BaseActivity implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "FaceAttrPreviewActivity";
    private CameraHelper cameraHelper;
    private DrawHelper drawHelper;
    private Camera.Size previewSize;
    private Integer rgbCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private FaceEngine faceEngine;
    private int afCode = -1;
    private boolean isCameraFront = false;
    Button switchCameraButton;
    FrameLayout lv_cameraview;
    RecyclerView rv_faceattribute;
    ArrayList<FaceAtteributeModle> faceAtteributeModleArrayList;
    private boolean isFaceDetected = false;
    private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;
    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;
    private FaceRectView faceRectView;

    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_attr_preview);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(attributes);
        }*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv_cameraview=findViewById(R.id.lv_cameraview);
        rv_faceattribute=findViewById(R.id.rv_faceattribute);
        faceAtteributeModleArrayList=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        rv_faceattribute.setLayoutManager(layoutManager);
   /*

        // Set the title (app name) for the toolbar
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));*/
       // switchCameraButton = findViewById(R.id.switchCameraButton);
        // Activity启动后就锁定为启动时的方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        previewView = findViewById(R.id.texture_preview);
        faceRectView = findViewById(R.id.face_rect_view);
        //在布局结束后才做初始化操作
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);

        /*switchCameraButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (cameraHelper != null) {
                    isCameraFront = !isCameraFront;

                    // Update the button text based on the camera state
                    if (isCameraFront) {
                        switchCameraButton.setText("Switch Back Camera");
                    } else {
                        switchCameraButton.setText("Switch Front Camera");
                    }
                    boolean success = cameraHelper.switchCamera();
                    if (!success) {
                        showToast(getString(R.string.switch_camera_failed));
                    } else {
                        showLongToast(getString(R.string.notice_change_detect_degree));
                    }
                }
            }
        });*/


    }

    private void initEngine() {
        faceEngine = new FaceEngine();
        afCode = faceEngine.init(this, DetectMode.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, 20, FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS);
        Log.i(TAG, "initEngine:  init: " + afCode);
        if (afCode != ErrorInfo.MOK) {
            showToast( getString(R.string.init_failed, afCode));
        }
    }

    private void unInitEngine() {

        if (afCode == 0) {
            afCode = faceEngine.unInit();
            Log.i(TAG, "unInitEngine: " + afCode);
        }
    }


    @Override
    protected void onDestroy() {
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        unInitEngine();
        super.onDestroy();
    }

    private void initCamera() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Log.i(TAG, "onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                        , cameraId, isMirror, false, false);
            }


            @Override
            public void onPreview(byte[] nv21, Camera camera) {
                final long startTimestamp = System.nanoTime();

                float inferenceTimeCost = (System.nanoTime() - startTimestamp) / 1000000.0f;
                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                List<FaceInfo> faceInfoList = new ArrayList<>();
                long start = System.currentTimeMillis();
                int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
                inferenceTimeCost = (System.nanoTime() - startTimestamp) / 1000000.0f;
                Log.e("onPreview", "detectFaces Time:" + inferenceTimeCost);
                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {

                    int processCode = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
                    if (processCode != ErrorInfo.MOK) {
                        return;
                    }
                    List<LivenessInfo> faceLivenessInfoList = new ArrayList<>();
                    int livenessCode = faceEngine.getLiveness(faceLivenessInfoList);
                    //年龄信息结果
                    List<AgeInfo> ageInfoList = new ArrayList<>();
                    //性别信息结果
                    List<GenderInfo> genderInfoList = new ArrayList<>();
                    //人脸三维角度结果
                    //   List<Face3DAngle> face3DAngleList = new ArrayList<>();
                    //活体检测结果
                  //获取年龄、性别、三维角度、活体结果
                    int ageCode = faceEngine.getAge(ageInfoList);
                    int genderCode = faceEngine.getGender(genderInfoList);
                    //  int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);

                    if (livenessCode == ErrorInfo.MOK)
                    {
                        List<DrawInfo> drawInfoList = new ArrayList<>();
//                    for (int i = 0; i < faceInfoList.size(); i++) {
                  /*  for (int i = 0; i < faceLivenessInfoList.size(); i++) {
                        drawInfoList.add(new DrawInfo(drawHelper.adjustRect(faceInfoList.get(i).getRect()), faceLivenessInfoList.get(i).getLiveness(), RecognizeColor.COLOR_UNKNOWN, null));

                    }*/

                        if (faceInfoList != null && faceLivenessInfoList != null) {
                            int minSize = Math.min(faceInfoList.size(), faceLivenessInfoList.size());
                            for (int i = 0; i < minSize; i++) {
                                cameraHelper.stop();
                             //   drawInfoList.add(new DrawInfo(drawHelper.adjustRect(faceInfoList.get(i).getRect()), faceLivenessInfoList.get(i).getLiveness(), RecognizeColor.COLOR_UNKNOWN, null));
                             //   Bitmap croppedFace=null;

                                FaceInfo firstFace = faceInfoList.get(i);
                                Bitmap croppedFace = captureImageFromNV21(nv21, firstFace.getRect());
                                /*if (mBitmap != null) {
                                    int left = Math.max(0, firstFace.getRect().left);
                                    int top = Math.max(0, firstFace.getRect().top);
                                    int width1 = Math.min(mBitmap.getWidth() - left, firstFace.getRect().width());
                                    int height1 = Math.min(mBitmap.getHeight() - top, firstFace.getRect().height());

                                    if (width1 > 0 && height1 > 0) {
                                        croppedFace = Bitmap.createBitmap(mBitmap, left, top, width1, height1);
                                        // Use the croppedFace for further processing
                                    } else {
                                        // Handle the case where the cropping rectangle is outside the bounds
                                    }
                                } else {
                                    // Handle the case where mBitmap is null
                                }*/


                                System.out.println("the size of info= onloop "+faceLivenessInfoList.get(i).getLiveness() );
                                FaceAtteributeModle faceAtteributeModle=new FaceAtteributeModle(ageInfoList.get(i).getAge(),faceLivenessInfoList.get(i).getLiveness()
                                        ,genderInfoList.get(i).getGender(), utilhelper.facequality(croppedFace),utilhelper.calculateLuminance(croppedFace),croppedFace);
                                faceAtteributeModleArrayList.add(faceAtteributeModle);

                            }
                            isFaceDetected = true;

                            lv_cameraview.setVisibility(View.GONE);
                            rv_faceattribute.setVisibility(View.VISIBLE);
                            rv_faceattribute.setAdapter(new FaceattributeAdapter(faceAtteributeModleArrayList,getApplicationContext()));



                            // Handle any remaining elements in the lists if they have different sizes
                            for (int i = minSize; i < faceInfoList.size(); i++) {
                                // Handle remaining elements in faceInfoList
                            }

                            for (int i = minSize; i < faceLivenessInfoList.size(); i++) {
                                // Handle remaining elements in faceLivenessInfoList
                            }
                            drawHelper.draw(faceRectView, drawInfoList);
                            // Rest of your code...
                        }


                    }
                } else {
                    return;
                }
            /*    Log.e("onPreview", "process Time:" + inferenceTimeCost);

                List<AgeInfo> ageInfoList = new ArrayList<>();
                List<GenderInfo> genderInfoList = new ArrayList<>();
//                List<Face3DAngle> face3DAngleList = new ArrayList<>();
                List<LivenessInfo> faceLivenessInfoList = new ArrayList<>();
                int ageCode = faceEngine.getAge(ageInfoList);
                int genderCode = faceEngine.getGender(genderInfoList);
//                int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
                int livenessCode = faceEngine.getLiveness(faceLivenessInfoList);

                // 有其中一个的错误码不为ErrorInfo.MOK，return
                if ((ageCode | genderCode | *//*face3DAngleCode |*//* livenessCode) != ErrorInfo.MOK) {
                    return;
                }
                if (faceRectView != null && drawHelper != null) {
                    List<DrawInfo> drawInfoList = new ArrayList<>();
//                    for (int i = 0; i < faceInfoList.size(); i++) {
                  *//*  for (int i = 0; i < faceLivenessInfoList.size(); i++) {
                        drawInfoList.add(new DrawInfo(drawHelper.adjustRect(faceInfoList.get(i).getRect()), faceLivenessInfoList.get(i).getLiveness(), RecognizeColor.COLOR_UNKNOWN, null));

                    }*//*

                    if (faceInfoList != null && faceLivenessInfoList != null) {
                        int minSize = Math.min(faceInfoList.size(), faceLivenessInfoList.size());

                        for (int i = 0; i < minSize; i++) {
                            drawInfoList.add(new DrawInfo(drawHelper.adjustRect(faceInfoList.get(i).getRect()), faceLivenessInfoList.get(i).getLiveness(), RecognizeColor.COLOR_UNKNOWN, null));
                        }

                        // Handle any remaining elements in the lists if they have different sizes
                        for (int i = minSize; i < faceInfoList.size(); i++) {
                            // Handle remaining elements in faceInfoList
                        }

                        for (int i = minSize; i < faceLivenessInfoList.size(); i++) {
                            // Handle remaining elements in faceLivenessInfoList
                        }
                        drawHelper.draw(faceRectView, drawInfoList);


                        // Rest of your code...
                    }



                }*/
            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (drawHelper != null) {
                    drawHelper.setCameraDisplayOrientation(displayOrientation);
                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };
        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(previewView.getMeasuredWidth(), previewView.getMeasuredHeight()))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(rgbCameraId != null ? rgbCameraId : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
        cameraHelper.start();
     /*   if (cameraHelper != null) {
            boolean success = cameraHelper.switchCamera();
            if (!success) {
              //  showToast(getString(R.string.switch_camera_failed));
            } else {
                //showLongToast(getString(R.string.notice_change_detect_degree));
            }
        }*/
    }

    @Override
    void afterRequestPermission(int requestCode, boolean isAllGranted) {
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            if (isAllGranted) {
                initEngine();
                initCamera();
            } else {
                showToast(getString( R.string.permission_denied));
            }
        }
    }

    /**
     * 在{@link #previewView}第一次布局完成后，去除该监听，并且进行引擎和相机的初始化
     */
    @Override
    public void onGlobalLayout() {
        previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        } else {
            initEngine();
            initCamera();
        }
    }
    private Bitmap captureImageFromNV21(byte[] nv21, Rect rect) {
        // Implement the logic to capture an image from nv21 data
        // Create a Bitmap using the captured image data
        // You can use YUV tools or Camera parameters to convert it

        // Example code to convert NV21 to a Bitmap (you may need to adjust this):
        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, previewSize.width, previewSize.height, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(rect, 100, out);
        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

}
