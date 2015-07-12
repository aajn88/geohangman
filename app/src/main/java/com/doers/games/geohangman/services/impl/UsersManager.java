package com.doers.games.geohangman.services.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doers.games.geohangman.persistance.GeohangmanSQLiteHelper;
import com.doers.games.geohangman.services.IUsersManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This is the UsersManager
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class UsersManager implements IUsersManager {

    /** The DB Helper * */
    private GeohangmanSQLiteHelper dbHelper;

    /** Current Context * */
    private Context context;

    /** All columns * */
    private String[] allColumns = {GeohangmanSQLiteHelper.USER_ID_COLUMN,
            GeohangmanSQLiteHelper.TOKEN_COLUMN};

    /**
     * UsersManager constructor
     * @param context Current Context
     */
    @Inject
    public UsersManager(Context context) {
        this.context = context;
        this.dbHelper = new GeohangmanSQLiteHelper(context);
    }

    /**
     * This method creates the user in this device. There will be just one user
     *
     * @param id User Id
     *
     * @return User Id if success, otherwise returns null
     */
    @Override
    public String createUser(String id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        trunkDatabase(database);

        ContentValues values = new ContentValues();
        values.put(GeohangmanSQLiteHelper.USER_ID_COLUMN, id);
        database.insert(GeohangmanSQLiteHelper.TABLE_USER, null, values);

        database.close();
        dbHelper.close();
        return id;
    }

    /**
     * This method retrieves stored User Id
     *
     * @return User Id if exists. Otherwise returns null
     */
    @Override
    public String getUser() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String userId = null;
        Cursor cursor = database
                .query(GeohangmanSQLiteHelper.TABLE_USER, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            userId = cursor.getString(0);
        }

        cursor.close();
        database.close();
        dbHelper.close();

        return userId;
    }

    /**
     * Erase all User table information
     */
    private void trunkDatabase(SQLiteDatabase database) {
        database.delete(GeohangmanSQLiteHelper.TABLE_USER, null, null);
    }

    /**
     * Creates a given token to the registered user
     *
     * @param token Token to be set
     *
     * @return The User Id if success, otherwise returns null
     */
    @Override
    public String createToken(String token) {
        String userId = getUser();
        if (userId != null) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(GeohangmanSQLiteHelper.TOKEN_COLUMN, token);
            database.update(GeohangmanSQLiteHelper.TABLE_USER, values, null, null);

            database.close();
            dbHelper.close();
        }

        return userId;
    }

    /**
     * Retrieves registered token
     *
     * @return Registered token, otherwise returns null
     */
    @Override
    public String getToken() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String token = null;

        Cursor cursor = database.query(GeohangmanSQLiteHelper.TABLE_USER,
                new String[]{GeohangmanSQLiteHelper.TOKEN_COLUMN}, null, null, null, null, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            token = cursor.getString(0);
        }

        database.close();
        dbHelper.close();
        return token;
    }
}
