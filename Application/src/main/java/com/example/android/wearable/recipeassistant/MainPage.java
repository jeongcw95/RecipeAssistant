package com.example.android.wearable.recipeassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class MainPage extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);



        }

    public void StartButtonClicked(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    public void FavoriteButtonClicked(View view) {
        Intent intent = new Intent(this, Favorite.class);
        startActivity(intent);
    }
}

