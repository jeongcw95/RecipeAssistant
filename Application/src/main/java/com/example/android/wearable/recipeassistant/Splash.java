package com.example.android.wearable.recipeassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
public class Splash extends Activity {
    databaseHelper dh;
    static User LoginUser;
    static boolean AutoLoginActive ;
    public static int AutoLoginChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            startActivity(new Intent(this, MainPage.class));
            finish();
        }
        else {
            AutoLoginActive = false;
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}