package com.example.android.wearable.recipeassistant;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    Switch sw;
    AppCompatButton save;
    databaseHelper dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sw = (Switch)findViewById(R.id.autologinswitch);
        save = (AppCompatButton)findViewById(R.id.saveButton);
        dh = new databaseHelper(getApplicationContext());
        startSetting();
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    Splash.AutoLoginChecked = 1;
                }
                else{
                    Splash.AutoLoginChecked = 0;
                }
            }
        });
    }

    public void onClickedSaveButton(View v){
        Splash.LoginUser.setAutologin(Splash.AutoLoginChecked);
        dh.updateUser(Splash.LoginUser);
        finish();
    }

    public void startSetting() {
        if (Splash.AutoLoginChecked == 0) {
            sw.setChecked(false);
        } else if (Splash.AutoLoginChecked == 1) {
            sw.setChecked(true);
        }
    }
}
