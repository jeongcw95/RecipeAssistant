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



public class RecipeRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RecipeRegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutIngredients;
    private TextInputLayout textInputLayoutSummary;
    private TextInputLayout textInputLayoutSteps;

    private TextInputEditText textInputEditTextTitle;
    private TextInputEditText textInputEditTextIngredients;
    private TextInputEditText textInputEditTextSummary;
    private TextInputEditText textInputEditTextSteps;

    private AppCompatButton appCompatButtonRecipeRegister;

    private InputValidation inputValidation;
    private databaseHelper databaseHelper;
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_register);
        nestedScrollView = (NestedScrollView) findViewById(R.id.RecipeNestedScrollView);
        textInputEditTextTitle = (TextInputEditText) findViewById(R.id.textInputEditTextTitle);
        textInputEditTextIngredients = (TextInputEditText) findViewById(R.id.textInputEditTexIngredients);
        textInputEditTextSummary = (TextInputEditText) findViewById(R.id.textInputEditTextSummary);
        textInputEditTextSteps = (TextInputEditText) findViewById(R.id.textInputEditTextSteps);

        appCompatButtonRecipeRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRecipeRegister);
        initListeners();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
//    private void initViews() {
//        nestedScrollView = (NestedScrollView) findViewById(R.id.RecipeNestedScrollView);
//
//        textInputLayoutTitle = (TextInputLayout) findViewById(R.id.textInputLayoutTitle);
//        textInputLayoutIngredients = (TextInputLayout) findViewById(R.id.textInputLayoutIngredients);
//        textInputLayoutSummary = (TextInputLayout) findViewById(R.id.textInputLayoutSummary);
//        textInputLayoutSteps = (TextInputLayout) findViewById(R.id.textInputLayoutSteps);
//
//        textInputEditTextTitle = (TextInputEditText) findViewById(R.id.textInputEditTextTitle);
//        textInputEditTextIngredients = (TextInputEditText) findViewById(R.id.textInputLayoutIngredients);
//        textInputEditTextSummary = (TextInputEditText) findViewById(R.id.textInputEditTextSummary);
//        textInputEditTextSteps = (TextInputEditText) findViewById(R.id.textInputEditTextSteps);
//
//        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRecipeRegister);
//    }

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
        inputValidation = new InputValidation(activity);
        databaseHelper = new databaseHelper(activity);
        recipe = new Recipe();
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        postDataToSQLite();
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        recipe.setTitleText(textInputEditTextTitle.getText().toString().trim());
        recipe.setIngredientsText(textInputEditTextIngredients.getText().toString().trim());
        recipe.setSummaryText(textInputEditTextSummary.getText().toString().trim());
        recipe.setSteps(textInputEditTextSteps.getText().toString().trim());
        databaseHelper.getWritableDatabase();
        databaseHelper.addRecipe(recipe);

//        Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
        emptyInputEditText();
        finish();
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextTitle.setText(null);
        textInputEditTextIngredients.setText(null);
        textInputEditTextSummary.setText(null);
        textInputEditTextSteps.setText(null);
    }
}
