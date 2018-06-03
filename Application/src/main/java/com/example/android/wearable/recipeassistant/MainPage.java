package com.example.android.wearable.recipeassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;

public class MainPage extends Activity {
    databaseHelper dh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        dh = new databaseHelper(getApplicationContext());
    }

    public void StartButtonClicked(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    public void FavoriteButtonClicked(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    public void LogOutButtonClicked(View view){
        Splash.AutoLoginChecked = 0;
        Splash.LoginUser.setAutologin(0);
        dh.updateUser(Splash.LoginUser);
        if(Splash.AutoLoginActive == true) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}