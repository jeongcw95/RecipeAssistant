package com.example.android.wearable.recipeassistant;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import java.net.URI;


public class RecipeRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RecipeRegisterActivity.this;

    private NestedScrollView nestedScrollView;
    private TextInputEditText textInputEditTextTitle;
    private TextInputEditText textInputEditTextIngredients;
    private TextInputEditText textInputEditTextSummary;
    private TextInputEditText textInputEditTextSteps;
    private AppCompatImageView RecipeImage;
    private AppCompatButton appCompatButtonRecipeRegister;
    private AppCompatButton ImageRegister;
    protected static String U_path;
    private InputValidation inputValidation;

    private databaseHelper databaseHelper;
    private Recipe recipe;

    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_register);
        nestedScrollView = (NestedScrollView) findViewById(R.id.RecipeNestedScrollView);
        textInputEditTextTitle = (TextInputEditText) findViewById(R.id.textInputEditTextTitle);
        textInputEditTextIngredients = (TextInputEditText) findViewById(R.id.textInputEditTexIngredients);
        textInputEditTextSummary = (TextInputEditText) findViewById(R.id.textInputEditTextSummary);
        textInputEditTextSteps = (TextInputEditText) findViewById(R.id.textInputEditTextSteps);
        RecipeImage = (AppCompatImageView)findViewById(R.id.RecipeImage);
        appCompatButtonRecipeRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRecipeRegister);
        ImageRegister = (AppCompatButton)findViewById(R.id.ImageRegisterButton);
        initListeners();
        initObjects();
    }

    private void initListeners() {
        appCompatButtonRecipeRegister.setOnClickListener(this);
        ImageRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new databaseHelper(activity);
        recipe = new Recipe();
        U_path = new String();
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.appCompatButtonRecipeRegister:
                postDataToSQLite();
                finish();
                break;
            case R.id.ImageRegisterButton:
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("contents://media/internal/images/media"));
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
                break;
        }
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
        Toast.makeText(this, "레시피가 추가되었습니다", Toast.LENGTH_LONG).show();
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
    //for image register
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == PICK_IMAGE){
                Uri uri = data.getData();
                RecipeImage.setImageURI(uri);
                U_path = getPath(uri);
                //Integer num = Integer.parseInt(U_path);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Uri uri){
        if(uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}
