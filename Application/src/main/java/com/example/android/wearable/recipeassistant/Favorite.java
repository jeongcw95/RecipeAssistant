package com.example.android.wearable.recipeassistant;

public class Favorite {

    public int user_id;
    public String recipe_title;

    public Favorite() {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRecipe_title() {
        return recipe_title;
    }

    public void setRecipe_title(String recipe_title) {
        this.recipe_title = recipe_title;
    }
}
