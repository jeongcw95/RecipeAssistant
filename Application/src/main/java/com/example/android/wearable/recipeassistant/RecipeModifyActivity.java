package com.example.android.wearable.recipeassistant;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class RecipeModifyActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RecipeModifyActivity.this;

    private NestedScrollView nestedScrollView;

    private TextView textTitle;
    private TextInputEditText textInputEditTextIngredients;
    private TextInputEditText textInputEditTextSummary;
    private TextInputEditText textInputEditTextSteps;

    private AppCompatButton appCompatButtonRecipeRegister;

    private databaseHelper databaseHelper;
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_modify);
        nestedScrollView = (NestedScrollView) findViewById(R.id.RecipeNestedScrollView);
        textTitle = (TextView) findViewById(R.id.textViewTitle);
        textInputEditTextIngredients = (TextInputEditText) findViewById(R.id.textInputEditTexIngredients);
        textInputEditTextSummary = (TextInputEditText) findViewById(R.id.textInputEditTextSummary);
        textInputEditTextSteps = (TextInputEditText) findViewById(R.id.textInputEditTextSteps);

        appCompatButtonRecipeRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRecipeModify);
        initListeners();
        initObjects();
        textTitle.setText(getIntent().getStringExtra("title"));

    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRecipeRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        databaseHelper = new databaseHelper(activity);
        recipe = new Recipe();
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */


    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void modifyDataToSQLite() {
        recipe.setTitleText(textTitle.getText().toString().trim());
        recipe.setIngredientsText(textInputEditTextIngredients.getText().toString().trim());
        recipe.setSummaryText(textInputEditTextSummary.getText().toString().trim());
        recipe.setSteps(textInputEditTextSteps.getText().toString().trim());
        databaseHelper.getWritableDatabase();
        databaseHelper.updateRecipe(recipe);

        Toast.makeText(this, "레시피가 수정되었습니다", Toast.LENGTH_LONG).show();
        emptyInputEditText();
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textTitle.setText(null);
        textInputEditTextIngredients.setText(null);
        textInputEditTextSummary.setText(null);
        textInputEditTextSteps.setText(null);
    }

    @Override
    public void onClick(View v) {
        modifyDataToSQLite();
        finish();
    }
}
