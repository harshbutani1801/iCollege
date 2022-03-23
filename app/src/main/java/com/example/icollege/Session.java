package com.example.icollege;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Session(Context context) {
        sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public void setlogin(Boolean login){
        editor.putBoolean("Key_Login", login);
        editor.commit();
    }

    public boolean getlogin(){
        return sharedPreferences.getBoolean("Key_Login", false);
    }

    public void setuserid(String userid){
        editor.putString("Key_UserID", userid);
        editor.commit();
    }

    public String getuserid(){
        return sharedPreferences.getString("Key_UserID", "");
    }

    public void setusertype(String position){
        editor.putString("Key_Position", position);
        editor.commit();
    }

    public String getusertype(){
        return sharedPreferences.getString("Key_Position", "");
    }
}
