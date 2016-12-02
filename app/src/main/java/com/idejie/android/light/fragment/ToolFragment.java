package com.idejie.android.light.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.idejie.android.light.R;

import static android.app.Activity.RESULT_OK;
public class ToolFragment extends Fragment{
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_tool, container, false);

        return view;
    }
}
//public class ToolFragment extends Fragment  implements View.OnClickListener
//{
//
//    View view;
//
//    private final String TAG = "ToolFragmnet";
//
//    BridgeWebView webView;
//
//    Button button;
//
//    int RESULT_CODE = 0;
//
//    ValueCallback<Uri> mUploadMessage;
//
//    @Override
//    public void onClick(View v) {
//        if (button.equals(v)) {
//            webView.callHandler("functionInJs", "data from Java", new CallBackFunction() {
//
//                @Override
//                public void onCallBack(String data) {
//                    // TODO Auto-generated method stub
//                    Log.i(TAG, "reponse data from js " + data);
//                }
//
//            });
//        }
//    }
//
//    static class Location {
//        String address;
//    }
//
//    static class User {
//        String name;
//        Location location;
//        String testStr;
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view =inflater.inflate(R.layout.fragment_tool, container, false);
//        webView = (BridgeWebView) view.findViewById(R.id.webView);
//        button = (Button) view.findViewById(R.id.button);
//
//        webView.setDefaultHandler(new DefaultHandler());
//
//        webView.setWebChromeClient(new WebChromeClient() {
//
//            @SuppressWarnings("unused")
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
//                this.openFileChooser(uploadMsg);
//            }
//
//            @SuppressWarnings("unused")
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
//                this.openFileChooser(uploadMsg);
//            }
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                mUploadMessage = uploadMsg;
//                pickFile();
//            }
//        });
//
//        webView.loadUrl("file:///android_asset/demo.html");
//
//        webView.registerHandler("submitFromWeb", new BridgeHandler() {
//
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
//            }
//
//        });
//
//        User user = new User();
//        Location location = new Location();
//        location.address = "SDU";
//        user.location = location;
//        user.name = "大头鬼";
//
//        webView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//
//            }
//        });
//
//        webView.send("hello");
//
//        return view;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (requestCode == RESULT_CODE) {
//            if (null == mUploadMessage){
//                return;
//            }
//            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
//            mUploadMessage.onReceiveValue(result);
//            mUploadMessage = null;
//        }
//    }
//
//    private void pickFile() {
//        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        chooserIntent.setType("image/*");
//        startActivityForResult(chooserIntent, RESULT_CODE);
//    }
//
//
//}
