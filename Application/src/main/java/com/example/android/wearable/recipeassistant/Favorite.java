package com.example.android.wearable.recipeassistant;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import static android.content.ContentValues.TAG;

public class Favorite extends Activity {
    public static SwipeMenuListView FavoriteListView;
    static final ArrayList<String> FAVORITE_LIST = new ArrayList<String>();
    public static ArrayAdapter adapter1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);

        FavoriteListView = (SwipeMenuListView) findViewById(R.id.FavoriteList);

        adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, FAVORITE_LIST);
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
//                        recreate();

                        break;
                    case 1:
                        Log.d(TAG, "Close button Execute");
                        FAVORITE_LIST.remove(position);
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

    /*public void delete(View view) {
        int count, checked;
        count = adapter1.getCount();
        if (count > 0) {
            checked = FavoriteListView.getCheckedItemPosition();
            if (checked > -1 && checked < count) {
                FAVORITE_LIST.remove(checked);
                FavoriteListView.clearChoices();
                adapter1.notifyDataSetChanged();
            }
        }
    }*/

//    public boolean checkList(){
//        int count;
//        for(int i=0; i<adapter1.getCount(); i++){
//            if(FAVORITE_LIST.get(i) == RecipeActivity.mRecipe.titleText)
//        }
//
//    }
//}
