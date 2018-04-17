package com.example.android.wearable.recipeassistant;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private AppCompatActivity activity = RecipeListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewRecipe;
    private List<Recipe> listRecipe;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private databaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        //getSupportActionBar().setTitle("");
        initViews();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewRecipe = (RecyclerView) findViewById(R.id.recyclerViewRecipe);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listRecipe = new ArrayList<>();
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(listRecipe);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewRecipe.setLayoutManager(mLayoutManager);
        recyclerViewRecipe.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRecipe.setHasFixedSize(true);
        recyclerViewRecipe.setAdapter(recipeRecyclerAdapter);
        databaseHelper = new databaseHelper(activity);

//        String titleFromIntent = getIntent().getStringExtra("TITLE");
//        textViewName.setText(titleFromIntent);

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
}
