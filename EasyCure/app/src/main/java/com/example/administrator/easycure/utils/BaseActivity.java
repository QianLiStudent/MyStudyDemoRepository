package com.example.administrator.easycure.utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Administrator on 2018/10/20 0020.
 */

public class BaseActivity extends Activity {
   protected void  onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
   }
}
