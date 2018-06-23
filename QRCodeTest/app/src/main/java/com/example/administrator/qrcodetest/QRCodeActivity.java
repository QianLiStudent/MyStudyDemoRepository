package com.example.administrator.qrcodetest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class QRCodeActivity extends Activity {

    private Intent intent;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_activity);

        intent = getIntent();
        String message = intent.getStringExtra("message");

        initView();

        showQRCode(message);

    }

    public void initView(){
        img = (ImageView)findViewById(R.id.img_qrcode);
    }

    public void showQRCode(String message){
        Bitmap bitmap = EncodingUtils.createQRCode(message,900,900,null);
        img.setImageBitmap(bitmap);

    }


}
