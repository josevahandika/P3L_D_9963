package com.josevahandika.akb_mobile.menu;

import android.content.Context;

public class SharedPref {
    android.content.SharedPreferences sharedPreferences;

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    public void setIsLogin(boolean isLogin) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", isLogin);
        editor.apply();
    }

    public boolean loadIsLogin() {
        return sharedPreferences.getBoolean("isLogin", false);
    }

//    public void setFirstTimeLaunch(boolean isFirstTime) {
//        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("IsFirstTimeLaunch", isFirstTime);
//        editor.apply();
//    }

//    public boolean isFirstTimeLaunch() {
//        return sharedPreferences.getBoolean("IsFirstTimeLaunch", true);
//    }

//    public void setToken(String token) {
//        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("token", token);
//        editor.apply();
//    }

//    public String getToken() {
//        return sharedPreferences.getString("token", "");
//    }

    public void setReservasi(String reservasi) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("reservasi", reservasi);
        editor.apply();
    }

    public String  getReservasi() {
        return sharedPreferences.getString("reservasi", "0");
    }
}
