package com.example.android.wearable.recipeassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
    databaseHelper dh;
    static User LoginUser;
    static boolean AutoLoginActive ;
    public static int AutoLoginChecked;
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AutoLoginActive = false;
                LoginUser = new User();
                dh = new databaseHelper(getApplicationContext());
                AutoLoginChecked = 0;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!(dh.CheckAutoLogin(1) == null)){
                    AutoLoginActive = true;
                    LoginUser = dh.CheckAutoLogin(1);
                    AutoLoginChecked = 1;
                    startActivity(new Intent(Splash.this, MainPage.class));
                    finish();
                }
                else {
                    AutoLoginActive = false;
                    startActivity(new Intent(Splash.this, LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}