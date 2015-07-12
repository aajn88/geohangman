package com.doers.games.geohangman.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is the DB Helper
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class GeohangmanSQLiteHelper extends SQLiteOpenHelper {

    /** DB Name * */
    private static final String DATABASE_NAME = "geohangman.db";

    /** The database version * */
    private static final int DATABASE_VERSION = 1;

    /** Geohangman User Table * */
    public static final String TABLE_USER = "GEOHANGMAN_USER";

    /** User Id Column * */
    public static final String USER_ID_COLUMN = "_id";

    /** User's token column * */
    public static final String TOKEN_COLUMN = "token";

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE_SQL =
            "create table " + TABLE_USER + "(" + USER_ID_COLUMN + " text primary key, " +
                    TOKEN_COLUMN + " text);";

    /**
     * Create a helper object to create, open, and/or manage a database. This method always returns
     * very quickly.  The database is not actually created or opened until one of {@link
     * #getWritableDatabase} or {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public GeohangmanSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the creation of tables
     * and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SQL);
    }

    /**
     * Called when the database needs to be upgraded. The implementation should use this method to
     * drop tables, add tables, or do anything else it needs to upgrade to the new schema version.
     * <p/> <p> The SQLite ALTER TABLE documentation can be found <a href="http://sqlite.org/lang_altertable.html">here</a>.
     * If you add new columns you can use ALTER TABLE to insert them into a live table. If you
     * rename or remove columns you can use ALTER TABLE to rename the old table, then create the new
     * table and then populate the new table with the contents of the old table. </p><p> This method
     * executes within a transaction.  If an exception is thrown, all changes will automatically be
     * rolled back. </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StringBuilder sb = new StringBuilder("Upgrading database from version ");
        sb.append(oldVersion);
        sb.append(" to ");
        sb.append(newVersion);
        sb.append(", which will destroy all old data");

        Log.w(GeohangmanSQLiteHelper.class.getName(), sb.toString());
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
