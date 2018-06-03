package com.example.android.wearable.recipeassistant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView Title;
    private TextView Summary;
    private TextView Ingredient;
    private TextView Steps;
    private AppCompatImageView RecipeImageView;
    private byte[] btarr;
    private Bitmap bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("fuck","sex");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        Title = (TextView)findViewById(R.id.DetailTitle);
        Ingredient = (TextView)findViewById(R.id.DetailIngredient);
        Summary = (TextView)findViewById(R.id.DetailSummary);
        Steps = (TextView)findViewById(R.id.DetailSteps);
       // RecipeImageView = (AppCompatImageView) findViewById(R.id.RecipeImageView);

        Title.setText(getIntent().getStringExtra("title"));
        Ingredient.setText(getIntent().getStringExtra("ingredient"));
        Summary.setText(getIntent().getStringExtra("summary"));
        Steps.setText(getIntent().getStringExtra("steps"));
//        btarr = getIntent().getByteArrayExtra("image");
//        bt = BitmapFactory.decodeByteArray(btarr, 0, btarr.length);
//        RecipeImageView.setImageBitmap(bt);
    }
}
