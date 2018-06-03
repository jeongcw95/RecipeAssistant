package com.example.android.wearable.recipeassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.android.wearable.recipeassistant.RecipeRecyclerAdapter.R_position;

public class FavoriteActivity extends Activity {
    public static SwipeMenuListView FavoriteListView;
    public Activity activity = FavoriteActivity.this;
//    static final ArrayList<String> FAVORITE_LIST;
    public databaseHelper databaseHelper;
    public static ArrayAdapter adapter1;
    public List<Favorite> listFavorite;
    public List<Recipe> listRecipe;
    public List<String> listFavoriteName = new ArrayList<>();

    private void initObjects() {
        listFavorite = new ArrayList<>();
        listRecipe = new ArrayList<>();
        databaseHelper = new databaseHelper(activity);

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
                listFavorite.clear();
                listFavorite.addAll(databaseHelper.getAllFavorite());
                listRecipe.clear();
                listRecipe.addAll(databaseHelper.getAllRecipe());

                for ( int i = 0 ; i < listFavorite.size(); i++) {
                    listFavoriteName.add(listFavorite.get(i).getRecipe_title());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);
        initObjects();

        FavoriteListView = (SwipeMenuListView) findViewById(R.id.FavoriteList);
//        adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, FAVORITE_LIST);
        adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listFavoriteName);
        FavoriteListView.setAdapter(adapter1);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9,  0xC9,  0xCE)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }

        };
        FavoriteListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
                FavoriteListView.smoothOpenMenu(position);
                Log.d(TAG, "click");
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
                FavoriteListView.smoothOpenMenu(position);
            }
        });

        FavoriteListView.setMenuCreator(creator);
        FavoriteListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.d(TAG, "Open Button Execute");
                        Intent intent = new Intent(activity, RecipeDetailActivity.class);
                        for ( int i = 0 ; i < listRecipe.size(); i++) {
                            if ( listRecipe.get(i).getTitleText().equals(listFavoriteName.get(position))){
                                intent.putExtra("title", listRecipe.get(position).getTitleText());
                                intent.putExtra("ingredient", listRecipe.get(position).getIngredientsText());
                                intent.putExtra("summary", listRecipe.get(position).getSummaryText());
                                intent.putExtra("steps", listRecipe.get(position).getSteps());
                                break;
                            }
                        }
                        startActivity(intent);

                        break;
                    case 1:
                        Log.d(TAG, "Close button Execute");
//                        FAVORITE_LIST.remove(position);
                        for ( int i = 0 ; i < listRecipe.size(); i++) {
                            if ( listRecipe.get(i).getTitleText().equals(listFavoriteName.get(position))){
                                listRecipe.get(i).setIsFavorite(0);
                                databaseHelper.updateRecipe(listRecipe.get(i));
                                break;
                            }
                        }
                        databaseHelper.deleteFavorite(listFavorite.get(position));
                        adapter1.notifyDataSetChanged();
                        recreate();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}