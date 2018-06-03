package com.example.android.wearable.recipeassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView Title;
    private TextView Summary;
    private TextView Ingredient;
    private TextView Steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);
        Title = (TextView)findViewById(R.id.DetailTitle);
        Ingredient = (TextView)findViewById(R.id.DetailIngredient);
        Summary = (TextView)findViewById(R.id.DetailSummary);
        Steps = (TextView)findViewById(R.id.DetailSteps);

        Title.setText(getIntent().getStringExtra("title"));
        Ingredient.setText(getIntent().getStringExtra("ingredient"));
        Summary.setText(getIntent().getStringExtra("summary"));
        Steps.setText(getIntent().getStringExtra("steps"));

    }
}
