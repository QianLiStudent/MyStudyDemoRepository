package com.example.administrator.easycure.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/26 0026.
 */

public class SpUtil {

    public static SharedPreferences sharedPreferences;
    public static Map<String,String> map;

    //保存用户信息到SP文件中
    public static void saveUserInfo(Context context,String userNameValue,String passwordValue,
                                    String phoneNumValue){
        sharedPreferences = context.getSharedPreferences(Constant.SPFILE,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.USERNAME,userNameValue);
        editor.putString(Constant.PASSWORD,passwordValue);
        editor.putString(Constant.PHONENUMBER,phoneNumValue);
        editor.commit();
    }

    //从SP文件中取出用户信息，这里是活的一部分，不包括绑定的用户安全号码
    public static Map<String,String> getUserInfo(Context context){
        sharedPreferences = context.getSharedPreferences(Constant.SPFILE,context.MODE_PRIVATE);

        map = new HashMap<>();
        map.put(Constant.USERNAME,sharedPreferences.getString(Constant.USERNAME,""));
        map.put(Constant.PASSWORD,sharedPreferences.getString(Constant.PASSWORD,""));
        map.put(Constant.PHONENUMBER,sharedPreferences.getString(Constant.PHONENUMBER,""));

        return map;
    }

    //保存安全号码
    public static void saveSecurityNumber(Context context,String securityNumberValue){
        sharedPreferences = context.getSharedPreferences(Constant.SPFILE,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.SECURITY_NUMBER,securityNumberValue);
        editor.commit();
    }

}
