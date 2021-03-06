package com.example.android.wearable.recipeassistant;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.wearable.recipeassistant.RecipeRecyclerAdapter.R_position;


public class RecipeListActivity extends AppCompatActivity {

    private AppCompatActivity activity = RecipeListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewRecipe;
    private List<Recipe> listRecipe;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private databaseHelper databaseHelper;
    public AppCompatButton Modify;
    public AppCompatButton Delete;
    public static boolean isClicked = false;
    private Favorite favorite;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        initViews();
        initObjects();
    }

    protected void onRestart(){
        super.onRestart();
        recreate();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewRecipe = (RecyclerView) findViewById(R.id.recyclerViewRecipe);
        Modify = (AppCompatButton) this.findViewById(R.id.recipeModifyButton);
        Delete = (AppCompatButton) this.findViewById(R.id.recipeDeleteButton);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listRecipe = new ArrayList<>();
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(listRecipe, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewRecipe.setLayoutManager(mLayoutManager);
        recyclerViewRecipe.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRecipe.setHasFixedSize(true);
        recyclerViewRecipe.setAdapter(recipeRecyclerAdapter);
        databaseHelper = new databaseHelper(activity);
        favorite = new Favorite();
        user = new User();

        getDataFromSQLite();
    }
    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listRecipe.clear();
                listRecipe.addAll(databaseHelper.getAllRecipe());

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                recipeRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    public void AddRecipeClicked(View view){
        Intent intent = new Intent(this, RecipeRegisterActivity.class);
        startActivity(intent);
    }

    public void settingClicked(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void recipeModifyClicked(View view){
        Intent intent = new Intent(this, RecipeModifyActivity.class);
        intent.putExtra("title", R_position.getTitleText());
        intent.putExtra("ingredients", R_position.getIngredientsText());
        intent.putExtra("summary", R_position.getSummaryText());
        intent.putExtra("steps", R_position.getSteps());
        startActivity(intent);
    }

    public void recipeDeleteClicked(View view){
        databaseHelper.deleteRecipe(R_position);
        recreate();
    }

    public void FavoriteClicked(View view){

        Toast.makeText(this, "즐겨찾기에 추가되었습니다", Toast.LENGTH_LONG).show();
//                  FavoriteActivity.FAVORITE_LIST.add(R_position.getTitleText());
        R_position.setIsFavorite(1);
        databaseHelper.updateRecipe(R_position);
        favorite.setUser_id(user.getId());
        favorite.setRecipe_title(String.valueOf(R_position.getTitleText()));
        databaseHelper.addFavorite(favorite);

    }

    private void goDetail(){
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        startActivity(intent);
    }

}