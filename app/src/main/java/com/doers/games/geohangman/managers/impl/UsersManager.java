package com.doers.games.geohangman.managers.impl;

import android.util.Log;

import com.doers.games.geohangman.managers.IUsersManager;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.persistance.DatabaseHelper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * This is the UsersManager
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class UsersManager implements IUsersManager {

    /** Logs Tag **/
    private static final String LOG_TAG = UsersManager.class.getName();

    /** The DB Helper * */
    private DatabaseHelper dbHelper;

    /** User's DAO **/
    private Dao<UserInfo, String> userDao;

    /**
     * UsersManager constructor
     *
     * @param dbHelper Database Helper
     */
    @Inject
    public UsersManager(DatabaseHelper dbHelper) throws SQLException {
        this.dbHelper = dbHelper;
        this.userDao = dbHelper.getDao(UserInfo.class);
    }

    /**
     * This method creates the user in this device. There will be just one user
     *
     * @param user The User
     *
     * @return User Id if success, otherwise returns null
     */
    @Override
    public boolean createUser(UserInfo user) {
        Log.d(LOG_TAG, String.format("Creating user: %s", user));
        boolean created = false;
        try {
            trunkDatabase();
            userDao.createOrUpdate(user);
            created = true;
        } catch (SQLException e) {
            Log.e(LOG_TAG, "An error has occurred while creating user", e);
        }

        return created;
    }

    /**
     * This method retrieves stored User Id
     *
     * @return User Id if exists. Otherwise returns null
     */
    @Override
    public UserInfo getUser() {
        UserInfo user = null;

        try {
            List<UserInfo> users = userDao.queryForAll();
            if (users != null && !users.isEmpty()) {
                user = users.get(0);
            }
        } catch (SQLException e) {
            Log.e(LOG_TAG, "An error has occurred while getting the user", e);
        }

        return user;
    }

    /**
     * Erase all User table information
     */
    private void trunkDatabase() throws SQLException {
        List<UserInfo> users = userDao.queryForAll();
        for (UserInfo user : users) {
            userDao.deleteById(user.getId());
        }
    }

    /**
     * Creates a given token to the registered user
     *
     * @param token Token to be set
     *
     * @return The User Id if success, otherwise returns null
     */
    @Override
    public boolean createToken(String token) {
        Log.d(LOG_TAG, String.format("Creating token: %s", token));
        boolean stored = false;

        UserInfo user = getUser();

        if (user != null) {
            user.setToken(token);
            stored = createUser(user);
        }

        return stored;
    }

    /**
     * Retrieves registered token
     *
     * @return Registered token, otherwise returns null
     */
    @Override
    public String getToken() {
        UserInfo user = getUser();
        return (user == null ? null : user.getToken());
    }
}
