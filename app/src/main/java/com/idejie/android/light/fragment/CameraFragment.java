package com.idejie.android.light.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.idejie.android.light.MainActivity;
import com.idejie.android.light.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class CameraFragment extends Fragment implements CameraBridgeViewBase.CvCameraViewListener2,View.OnClickListener {
    private static final String TAG = "CameraFragment";

    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean              mIsJavaCamera = true;
    private MenuItem mItemSwitchCamera = null;


    static {
        OpenCVLoader.initDebug();
    }
    private int num=0;
    private int lastshownum=0;
    private Mat mRGBa;
    private Mat mFlipRGBa;
    private Mat mTransposeRGBa;
    private Handler handler;
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(getContext()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    public CameraFragment(){
        Log.i(TAG,"Instantiated new " + this.getClass());
    }



    MainActivity activity = (MainActivity) getActivity();
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_camera, container, false);


//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mOpenCvCameraView= (CameraBridgeViewBase) view.findViewById(R.id.java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Log.d("test.....cont.....",num+"");
//                Toast.makeText(OpenCameraActivity.this,lastshownum,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        FloatingActionButton fb = (FloatingActionButton) view.findViewById(R.id.fab2);
        fb.setOnClickListener(this);
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, getContext(), mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRGBa=new Mat();
        mFlipRGBa=new Mat();
        mTransposeRGBa=new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRGBa.release();
        mFlipRGBa.release();
        mTransposeRGBa.release();

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRGBa=inputFrame.rgba();
        Core.flip(mRGBa,mFlipRGBa,1);
        Core.flip(mFlipRGBa,mFlipRGBa,0);
        Core.flip(mFlipRGBa,mFlipRGBa,1);
        Core.transpose(mFlipRGBa,mTransposeRGBa);
        Imgproc.resize(mTransposeRGBa,mFlipRGBa,mFlipRGBa.size(),0.0D,0.0D,0);
        Imgproc.cvtColor(mFlipRGBa,mRGBa,Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(mRGBa,mRGBa,100,255,Imgproc.THRESH_BINARY);
        Imgproc.cvtColor(mRGBa,mFlipRGBa,Imgproc.COLOR_GRAY2BGRA,4);

        Mat hierarchy = new Mat();
        //转换矩阵的数据类型
        hierarchy.convertTo(hierarchy, CvType.CV_32SC1);
        //定义轮廓抽取模式
        //zhichaozhaowaiweilunkuo
        int mode = Imgproc.RETR_EXTERNAL;
        //定义轮廓识别方法,边缘近似方法
        //将所有连码点转换成点
        int method = Imgproc.CHAIN_APPROX_NONE;
        //Log.d("test.....","here");
        ArrayList<MatOfPoint> contours=new ArrayList<MatOfPoint>();
        Imgproc.findContours(mRGBa, contours,hierarchy,mode,method);
        //Log.d("test.....","here2");

        num=contours.size();
        double x=0;
        double y=0;
        for (int k=0; k < contours.size(); k++){
            Point[] ap=contours.get(k).toArray();
            x=x+ap[0].x;
            y=y+ap[0].y;
        }
        //Point cenPoint=new Point(x/contours.size(),y/contours.size());
        ArrayList<MatOfInt> hull=new ArrayList<MatOfInt>();
        ArrayList<MatOfInt4> dis=new ArrayList<MatOfInt4>();
        int shownum=0;
        int max=0;
        lastshownum=0;
        for (int k=0; k < contours.size(); k++){
            MatOfInt matint=new MatOfInt();
            //凸包
            Imgproc.convexHull(contours.get(k), matint,true);
            hull.add(matint);

        }
        for (int k=0; k < contours.size(); k++){
            try {
                MatOfInt4 matint4=new MatOfInt4();
                //凸缺陷
                Imgproc.convexityDefects(contours.get(k), hull.get(k),matint4);
                List<Integer> cdList = matint4.toList();

                shownum=0;
                for (int i=0;i<cdList.size();i++){
                    if (i%4==3&&cdList.get(i)>40500){
//                        Point[] a1=contours.get(cdList.get(i-3)).toArray();
//                        Double x1=a1[0].x;
//                        Double y1=a1[0].y;
//                        Point[] a2=contours.get(cdList.get(i-2)).toArray();
//                        Double x2=a2[0].x;
//                        Double y2=a2[0].y;
//                        Point[] a3=contours.get(cdList.get(i-1)).toArray();
//                        Double x3=a3[0].x;
//                        Double y3=a3[0].y;
//                        if ((x1-x3)*(x2-x3)+(y1-y3)*(y2-y3)>0){
//                            shownum++;
//                        }
//                        找到最大值
//                        if (cdList.get(i)>max){
//                            max=cdList.get(i);
//                        }

                        shownum++;
                    }
                }
//                for (int i=0;i<cdList.size();i++){
////                    防止max为0的时候
//                    if (max==0){
//                        break;
//                    }
//                    if (i%4==3&&max*1.0/cdList.get(i)<2){
//                        shownum++;
//                    }
//                }
//                //        手指比凹槽+1
//                shownum++;


                if (shownum>lastshownum){
                    lastshownum=shownum;
                }

            }catch (Exception e){

            }

        }
        num=dis.size()+1;
        handler.sendEmptyMessage(lastshownum);
        return mFlipRGBa;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab2:
                if (lastshownum==0){
                    lastshownum++;
                }
                Toast.makeText(getContext(),lastshownum+"",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
