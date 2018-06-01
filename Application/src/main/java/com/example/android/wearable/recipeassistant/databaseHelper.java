package com.example.android.wearable.recipeassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class databaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "RecipeAssistant.db";

    // User table name
    private static final String TABLE_USER = "user";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_AUTOLOGIN = "user_auto_login";

    private static final String TABLE_RECIPE = "recipe";

    private static final String COLUMN_RECIPE_ID = "recipe_id";
    private static final String COLUMN_RECIPE_TITLE = "recipe_title";
    private static final String COLUMN_RECIPE_SUMMARY = "recipe_summary";
    private static final String COLUMN_RECIPE_INGREDIENTS = "recipe_ingredients";
    private static final String COLUMN_RECIPE_STEP = "recipe_steps";

    private static final String TABLE_FAVORITE = "favorite";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_AUTOLOGIN + " INTEGER" + ")";

    private String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPE + "("
            + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECIPE_TITLE + " TEXT,"
            + COLUMN_RECIPE_SUMMARY + " TEXT," + COLUMN_RECIPE_INGREDIENTS + " TEXT," + COLUMN_RECIPE_STEP + " TEXT" + ")";

    private String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITE + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY," + COLUMN_RECIPE_TITLE + " TEXT" + ")";
    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private String DROP_RECIPE_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECIPE;

    private String DROP_FAVORITE_TABLE = "DROP TABLE IF EXISTS " + TABLE_FAVORITE;
    /**
     * Constructor
     *
     * @param context
     */
    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_RECIPE_TABLE);
        db.execSQL(DROP_FAVORITE_TABLE);
        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_AUTOLOGIN, user.getAutologin());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_TITLE, recipe.getTitleText());
        values.put(COLUMN_RECIPE_SUMMARY, recipe.getSummaryText());
        values.put(COLUMN_RECIPE_INGREDIENTS, recipe.getIngredientsText());
        values.put(COLUMN_RECIPE_STEP, recipe.getSteps());

        // Inserting Row
        db.insert(TABLE_RECIPE, null, values);
        db.close();
    }

    public void addFavorite(User user, Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.getId());
        values.put(COLUMN_RECIPE_TITLE, recipe.getTitleText());

        // Inserting Row
        db.insert(TABLE_FAVORITE, null, values);
        db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }

    public List<Recipe> getAllRecipe() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_RECIPE_ID,
                COLUMN_RECIPE_TITLE,
                COLUMN_RECIPE_SUMMARY,
                COLUMN_RECIPE_INGREDIENTS,
                COLUMN_RECIPE_STEP
        };
        // sorting orders
        String sortOrder =
                COLUMN_RECIPE_TITLE + " ASC";
        List<Recipe> recipeList = new ArrayList<Recipe>();

        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_RECIPE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_ID))));
                recipe.setTitleText(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_TITLE)));
                recipe.setSummaryText(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_SUMMARY)));
                recipe.setIngredientsText(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_INGREDIENTS)));
                recipe.setSteps(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_STEP)));

                // Adding user record to list
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return recipeList;
    }
    public List<Favorite> getAllFavorite() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_RECIPE_TITLE
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_ID + " ASC";
        List<Favorite> favoriteList = new ArrayList<Favorite>();

        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_FAVORITE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                favorite.setRecipe_title(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_TITLE)));


                // Adding user record to list
                favoriteList.add(favorite);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return favoriteList;
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_AUTOLOGIN, user.getAutologin());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_TITLE, recipe.getTitleText());
        values.put(COLUMN_RECIPE_SUMMARY, recipe.getSummaryText());
        values.put(COLUMN_RECIPE_INGREDIENTS, recipe.getIngredientsText());
        values.put(COLUMN_RECIPE_STEP, recipe.getSteps());

        // updating row
        db.update(TABLE_RECIPE, values, COLUMN_RECIPE_TITLE + " = ?",
                new String[]{String.valueOf(recipe.getTitleText())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_RECIPE, COLUMN_RECIPE_TITLE + " = ?",
                new String[]{String.valueOf(recipe.getTitleText())});
        db.close();
    }

    public void deleteFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_FAVORITE, COLUMN_RECIPE_TITLE + " = ?",
                new String[]{String.valueOf(favorite.getRecipe_title())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        /*
        Login_User.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
        Login_User.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
        Login_User.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        Login_User.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        */
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkRecipe(String title) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_RECIPE_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_RECIPE_ID + " = ?";

        // selection argument
        String[] selectionArgs = {title};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_RECIPE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public User CheckLoginUser(String email) {
        User returnUser = new User();
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorposition = cursor.getPosition();
        cursor.moveToNext();

        returnUser.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
        returnUser.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        returnUser.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        returnUser.setAutologin(LoginActivity.AutoLoginSign);

        cursor.close();
        db.close();

        return returnUser;
    }
}
