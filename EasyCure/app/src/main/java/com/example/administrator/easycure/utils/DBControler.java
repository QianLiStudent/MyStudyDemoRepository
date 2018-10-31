package com.example.administrator.easycure.utils;

import android.database.Cursor;

import com.example.administrator.easycure.JavaBean.SchedulePlan;
import com.example.administrator.easycure.JavaBean.User;

import org.litepal.crud.DataSupport;

import java.util.Map;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public class DBControler {

    public static Cursor cursor = null;

    public static void setScheduleItemArgs(SchedulePlan plan,Map<String,String> map){
        plan.setTitle(map.get("title"));
        plan.setTime(map.get("time"));
        plan.setCreateMonth(map.get("createMonth"));
        plan.setCreateDay(map.get("createDay"));
        plan.setMessage(map.get("message"));
    }


//    在数据库中添加一条记录,map集合中保存的是一条item的所有数据
    public static boolean addScheduleItem(Map<String,String> map){
        SchedulePlan plan = new SchedulePlan();
        //把设置参数的代码封装到函数中，以便复用
        setScheduleItemArgs(plan,map);
        //调用save（）直接保存进数据库
        return plan.save();
    }

//    在数据库中删除一条记录，id表示所删除的item在数据库中的id
    public static boolean deleteScheduleItem(int index){
        boolean isDeletedSuccessful = DataSupport.delete(SchedulePlan.class, getPositionInScheduleTable(index)) > 0;
        return isDeletedSuccessful;
    }

//    更新数据库中指定的记录
    public static boolean updateScheduleItem(int index,Map<String,String> map){
        SchedulePlan plan = DataSupport.find(SchedulePlan.class,getPositionInScheduleTable(index));
        setScheduleItemArgs(plan,map);
        return plan.save();
    }

//    查询数据库中的指定id的记录
    public static SchedulePlan selectScheduleItem(int index){

        return DataSupport.find(SchedulePlan.class,getPositionInScheduleTable(index));
    }

//    获得数据库中指定表的条目数
    public static int getTableItemCount(String tableName){
        return DataSupport.count(tableName);
    }

//    与列表对应，所点击的item在列表中的额第几行我们就从数据库找第几行

    public static int getPositionInScheduleTable(int index){
        cursor = DataSupport.findBySQL("select * from (select * from " + Constant.TABLE_NAME_SCHEDULE_PLAN +
                " order by id limit 0," + index + ") order by id desc limit 0,1");
        if(cursor.moveToFirst()){
            index = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            cursor.close();
        }
        return index;
    }
//----------------------------------------------------上面的是备忘录的数据表--------------------------------------------------

//在User数据表中加入一条数据，表示一个用户注册账号了
    public static boolean addAccountItem(Map<String,String> map){
        User user = new User();
        //把设置参数的代码封装到函数中，以便复用
        setUserItemArgs(user,map);
        //调用save（）直接保存进数据库
        return user.save();
    }

    //查找指定手机号码的用户数据
    public static User selectAccountItem(String phoneNumber){
        Cursor cursor = DataSupport.findBySQL("select * from " + Constant.TABLE_NAME_USER + " where phoneNumber='" + phoneNumber + "'");
        User user = null;

        if(cursor != null){
            if(cursor.moveToFirst()){

                user = new User();
                //列参数不要用之前定义的常量，否则取不到相应的数据
                user.setUserName(cursor.getString(cursor.getColumnIndex("username")));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phonenumber")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setSecurityPhoneNumber(cursor.getString(cursor.getColumnIndex("securityphonenumber")));
                user.setSecurityQuestion1(cursor.getString(cursor.getColumnIndex("securityquestion1")));
                user.setSecurityQuestion2(cursor.getString(cursor.getColumnIndex("securityquestion2")));
                user.setSecurityQuestion3(cursor.getString(cursor.getColumnIndex("securityquestion3")));
                user.setSecurityAnswer1(cursor.getString(cursor.getColumnIndex("securityanswer1")));
                user.setSecurityAnswer2(cursor.getString(cursor.getColumnIndex("securityanswer2")));
                user.setSecurityAnswer3(cursor.getString(cursor.getColumnIndex("securityanswer3")));
            }
        }
        return user;
    }

    //修改用户数据
    public static boolean updateAccountItem(String phoneNumber,Map<String,String> map){
        User user = selectUserItem(getIdInAccountTable(phoneNumber));
        setUserItemArgs(user,map);
        return user.save();
    }

    //通过User表中某条记录的id找到对应的数据并封装进user对象
    public static User selectUserItem(int id){

        return DataSupport.find(User.class,id);
    }

    //用于获取用户表中指定手机号的那条数据的id号
    public static int getIdInAccountTable(String phoneNumber){
        Cursor cursor = DataSupport.findBySQL("select * from " + Constant.TABLE_NAME_USER + " where " + Constant.PHONENUMBER + "=" + phoneNumber);
        if(cursor != null && cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            cursor.close();
            return id;
        }
        return -1;
    }

    public static void setUserItemArgs(User user,Map<String,String> map){
        user.setUserName(map.get(Constant.USERNAME));
        user.setPhoneNumber(map.get(Constant.PHONENUMBER));
        user.setPassword(map.get(Constant.PASSWORD));
        user.setSecurityPhoneNumber(map.get(Constant.SECURITY_NUMBER));
        user.setSecurityQuestion1(map.get(Constant.SECURITY_QUESTION1));
        user.setSecurityQuestion2(map.get(Constant.SECURITY_QUESTION2));
        user.setSecurityQuestion3(map.get(Constant.SECURITY_QUESTION3));
        user.setSecurityAnswer1(map.get(Constant.SECURITY_ANSWER1));
        user.setSecurityAnswer2(map.get(Constant.SECURITY_ANSWER2));
        user.setSecurityAnswer3(map.get(Constant.SECURITY_ANSWER3));
    }

//----------------------------------------------------上面的是用户账号的数据表--------------------------------------------------
}
