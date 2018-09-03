package com.example.administrator.fourth;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int TAKE_PHOTO = 1;    //拍照权限请求码
    private static final int CHOOSE_PHOTO = 2;  //从相册选择图片权限请求码
    private static final int RECORD_VIDEO = 3;  //录制视频权限请求码

    private static final int TAKE_PHOTO_SKIP_REQUEST_CODE = 3;      //拍照跳转请求码
    private static final int CHOOSE_PHOTO_SKIP_REQUEST_CODE = 4;    //相册选图跳转请求码
    private static final int RECORD_VIDEO_SKIP_REQUEST_CODE = 5;         //录制视频跳转请求码

    private Button take_photo_btn,choose_photo_btn,record_video_btn;
    private ImageView img;

    private boolean isGetAllPermissions;    //保存是否获取所有权限的boolean值

    private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    private Uri picFileUri;        //拍照得到的图片的路径的uri
    private Uri vdieoFileUri;       //录制的视频的路径的uri

    private File photo;     //最终的图片路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView(){
        take_photo_btn = findViewById(R.id.take_photo_btn);
        choose_photo_btn = findViewById(R.id.choose_photo_btn);
        record_video_btn = findViewById(R.id.record_video_btn);
        img = findViewById(R.id.img);

        take_photo_btn.setOnClickListener(this);
        choose_photo_btn.setOnClickListener(this);
        record_video_btn.setOnClickListener(this);

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.take_photo_btn:
                isGetAllPermissions = checkAllPermission(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
                //如果值为true表示拥有所有的权限，则直接执行拍照逻辑，否则就请求权限
                if(isGetAllPermissions){
                    takePhoto();
                }else{
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
                }
                break;
            case R.id.choose_photo_btn:
                //这里写两份一模一样权限判断是为了让读者了解到当场景中需要动态注册多种权限的时候，大概也是这个流程。
                isGetAllPermissions = checkAllPermission(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
                //如果值为true表示拥有所有的权限，则直接执行拍照逻辑，否则就请求权限
                if(isGetAllPermissions){
                    choosePhoto();
                }else{
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
                }
                break;
            case R.id.record_video_btn:
                isGetAllPermissions = checkAllPermission(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
                if(isGetAllPermissions){
                    recordVideo();
                }else{
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
                }
                break;
        }
    }

    //检查该有的权限是否已经被授予了
    private boolean checkAllPermission(String[] permissions){
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    //请求权限，这里会自动弹窗
    private void requestPermissions(String[] permissions){
        ActivityCompat.requestPermissions(this,permissions,TAKE_PHOTO);
    }

    //请求权限的结果，向用户请求权限并当用户做出应答的之后会自动调用该方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean isGetAllPermissions;
        switch(requestCode){
            case TAKE_PHOTO:
                isGetAllPermissions = true;
                for(int grant : grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        isGetAllPermissions = false;
                        break;
                    }
                }
                if(isGetAllPermissions){
                    takePhoto();
                }else{
                    guideToSetting();
                }
                break;
            case CHOOSE_PHOTO:
                isGetAllPermissions = true;
                for(int grant : grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        isGetAllPermissions = false;
                        break;
                    }
                }
                if(isGetAllPermissions){
                    choosePhoto();
                }else{
                    guideToSetting();
                }
                break;
            case RECORD_VIDEO:
                isGetAllPermissions = true;
                for(int grant : grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        isGetAllPermissions = false;
                        break;
                    }
                }
                if(isGetAllPermissions){
                    recordVideo();
                }else{
                    guideToSetting();
                }
                break;
        }
    }

    //拍照
    public void takePhoto(){

        File file = new File(rootPath + "/我的私人相册");
        if(!file.exists()){
            file.mkdir();
        }
        photo = new File(file,SystemClock.uptimeMillis() + ".jpg");
        try{
            if(photo.exists()){
                photo.delete();
            }
            photo.getParentFile().createNewFile();
        }catch(Exception e){
            e.printStackTrace();
        }

        picFileUri = Uri.fromFile(photo);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picFileUri); // set the image file name
        // start the image capture Intent
        startActivityForResult(intent, TAKE_PHOTO_SKIP_REQUEST_CODE);
    }

    //从相册中选取图片
    public void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, CHOOSE_PHOTO_SKIP_REQUEST_CODE);
    }

    //录制视频
    public void recordVideo(){
        File file = new File(Environment.getExternalStorageDirectory(),SystemClock.uptimeMillis() + ".avi");
        vdieoFileUri = Uri.fromFile(file);
        //create new Intent
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, vdieoFileUri);  // set the video file name

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

        // start the Video Capture Intent
        startActivityForResult(intent,RECORD_VIDEO_SKIP_REQUEST_CODE);
    }

    //引导用户前往设置界面，注意：有的手机在询问授权这块会覆盖掉下面这个方法，因为比较健全的手机系统都是自带询问对话框的，那个的优先级比我们这个高
    public void guideToSetting(){
        new AlertDialog.Builder(this).setTitle("提示").
                setMessage("应用功能涉及到读写sdcard以及相机权限，是否前往应用设置授予权限？").
                setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        //跳转到setting界面
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        //建一个新栈存setting界面
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //setting界面不放于当前栈中
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        //setting界面不会保存在最近启动的活动列表中
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消",null).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case TAKE_PHOTO_SKIP_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    try{
                        //发送广播通知图库刷新
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.parse("file://" + photo.getAbsolutePath())));
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(picFileUri));
                        img.setImageBitmap(bitmap);
                    }catch(FileNotFoundException e) {
                            Toast.makeText(this,"图片解析异常",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this,"返回结果失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case CHOOSE_PHOTO_SKIP_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                        img.setImageBitmap(bitmap);
                        Toast.makeText(this,"图片解析成功",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this,"图片解析异常",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this,"选取图片失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case RECORD_VIDEO_SKIP_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Toast.makeText(this,"视频保存成功，请到"
                            + Environment.getExternalStorageDirectory().getPath() + "下查看",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"视频录制失败，请重试",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
