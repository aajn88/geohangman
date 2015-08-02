package com.doers.games.geohangman.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.UserInfo;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * SQLite helper
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {

    /** Logs TAG **/
    private static final String LOG_TAG = DatabaseHelper.class.getName();

    /** Database Name **/
    private static final String DATABASE_NAME = "geohangman.db";

    /** Database Version **/
    private static final int DATABASE_VERSION = 1;

    /** Connection Source **/
    protected AndroidConnectionSource connectionSource = new AndroidConnectionSource(this);

    /**
     * Constructor for the current context
     *
     * @param context The context which will apply the DB
     */
    @Inject
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        setUpDb(db);
    }

    /**
     * This method sets up the DB
     *
     * @param db DB Connection
     */
    private void setUpDb(SQLiteDatabase db) {
        setUpDb(db, null, null);
    }

    /**
     * This method sets up the DB when it is updated. If any (or both) of the values is null, then
     * update process will be omitted and regular build process will be executed.
     *
     * @param db         DB Connection
     * @param oldVersion The DB Old Version
     * @param newVersion The DB New Version
     */
    private void setUpDb(SQLiteDatabase db, Integer oldVersion, Integer newVersion) {
        DatabaseConnection conn = connectionSource.getSpecialConnection();
        boolean cleanSpecial = false;
        if (conn == null) {
            conn = new AndroidDatabaseConnection(db, true);

            try {
                connectionSource.saveSpecialConnection(conn);
                cleanSpecial = true;
            } catch (SQLException e) {
                throw new IllegalStateException("Cannot save special connection", e);
            }
        }

        try {
            if (oldVersion == null || newVersion == null) {
                onCreate();
            } else {
                onUpgrade(oldVersion, newVersion);
            }
        } finally {
            if (cleanSpecial) {
                connectionSource.clearSpecialConnection(conn);
            }
        }
    }

    /**
     * This method creates the DB schema
     */
    private void onCreate() {
        try {
            Log.i(LOG_TAG, "onCreate");
            TableUtils.createTable(connectionSource, Challenge.class);
            TableUtils.createTable(connectionSource, UserInfo.class);
            Log.i(LOG_TAG, "DB Creation has been successful");
        } catch (SQLException e) {
            Log.e(LOG_TAG, "DB could not been created", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        setUpDb(db, oldVersion, newVersion);
    }

    /**
     * This method upgrades the DB with the new version
     *
     * @param oldVersion Versión anterior de la BD
     * @param newVersion Nueva versión de la BD
     */
    private void onUpgrade(int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Challenge.class, true);
            TableUtils.dropTable(connectionSource, UserInfo.class, true);

            onCreate();
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Could not delete the DB", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método obtiene un DAO dado
     * <p/>
     * Extraido de: https://goo.gl/6LIYy2
     *
     * @param clazz Instancia de la clase pedida para el DAO
     * @param <D>   Super clase del DAO
     * @param <T>   Clase pedida para el DAO
     *
     * @return El DAO
     *
     * @throws SQLException
     */
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        // lookup the dao, possibly invoking the cached database config
        Dao<T, ?> dao = DaoManager.lookupDao(connectionSource, clazz);
        if (dao == null) {
            // try to use our new reflection magic
            DatabaseTableConfig<T> tableConfig = DatabaseTableConfigUtil
                    .fromClass(connectionSource, clazz);
            if (tableConfig == null) {
                /**
                 * TODO: we have to do this to get to see if they are using the deprecated
                 * annotations like
                 * {@link DatabaseFieldSimple}.
                 */
                dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, clazz);
            } else {
                dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, tableConfig);
            }
        }

        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }

}
