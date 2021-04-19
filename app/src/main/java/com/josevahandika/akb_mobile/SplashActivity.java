package com.josevahandika.akb_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.josevahandika.akb_mobile.databinding.ActivitySplashBinding;
import com.josevahandika.akb_mobile.menu.MenuActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private Handler handler = new Handler();
//    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
//        fauth = FirebaseAuth.getInstance();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        //sharedPref = new SharedPref(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(sharedPref.loginS()){
//
//                    System.out.println("\n\n\nLOGIN SHARED PREF\n\n\n");
//                    startActivity(new Intent(SplashActivity.this,SplashActivityAfterLogin.class));
//                }
//                else{
                    System.out.println("\n\n\nLOGIN gagal  PREF\n\n\n");
                    startActivity(new Intent(SplashActivity.this, MenuActivity.class));
                //}

                finish();
            }
        },3000);
    }
}