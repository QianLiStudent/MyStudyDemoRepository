package com.example.administrator.qrcodetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_scanQRCode,btn_createQRCode;
    private EditText et;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


    }

    public void initView(){
        btn_scanQRCode = (Button)findViewById(R.id.btn_scanQRCode);
        btn_createQRCode = (Button)findViewById(R.id.btn_createQRCode);
        et = (EditText)findViewById(R.id.et_message);
        tv = (TextView)findViewById(R.id.tv_message);

        btn_scanQRCode.setOnClickListener(this);
        btn_createQRCode.setOnClickListener(this);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_createQRCode:
                String message = et.getText().toString().trim();
                if(!message.equals("")){
                    Intent intent = new Intent(MainActivity.this,QRCodeActivity.class);
                    intent.putExtra("message",message);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_scanQRCode:
                Intent intent = new Intent(MainActivity.this,CaptureActivity.class);

                startActivity(intent);
                //startActivityForResult(intent,0);
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode == RESULT_OK){
//            Bundle bundle = data.getExtras();
//            tv.setText(bundle.getString("result"));
//        }
//    }
}
