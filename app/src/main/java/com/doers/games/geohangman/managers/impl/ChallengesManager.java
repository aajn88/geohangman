package com.doers.games.geohangman.managers.impl;

import android.util.Log;

import com.doers.games.geohangman.managers.IChallengesManager;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.persistance.DatabaseHelper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * This class implements IChallengesManager interface
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class ChallengesManager implements IChallengesManager {

    /** Log Tag * */
    private static final String LOG_TAG = ChallengesManager.class.getName();

    /** Database Helper * */
    private DatabaseHelper helper;

    /** The challenge Dao **/
    private Dao<Challenge, Integer> challengeDao;

    /**
     * Constructor for the Database Helper
     *
     * @param helper The DB Helper
     *
     * @throws SQLException
     */
    @Inject
    public ChallengesManager(DatabaseHelper helper) throws SQLException {
        this.helper = helper;
        this.challengeDao = helper.getDao(Challenge.class);
    }

    /**
     * Creates a challenge in DB
     *
     * @param challenge Challenge to be created
     *
     * @return Updated challenge
     */
    @Override
    public Challenge create(Challenge challenge) {
        try {
            challengeDao.create(challenge);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Error while creating Challenge", e);
            challenge = null;
        }
        return challenge;
    }

    /**
     * Finds a Challenge by its Id
     *
     * @param challengeId ChallengeId
     *
     * @return A challenge if a match was found. Otherwise returns null
     */
    @Override
    public Challenge findById(int challengeId) {
        Challenge challenge = null;
        try {
            challenge = challengeDao.queryForId(challengeId);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Error while finding Challenge by Id", e);
        }

        return challenge;
    }

    /**
     * Returns all existing Challenges
     *
     * @return All existing challenges
     */
    @Override
    public List<Challenge> findAll() {
        List<Challenge> challenges = null;
        try {
            challenges = challengeDao.queryForAll();
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Error while finding Challenge by Id", e);
        }
        return challenges;
    }

    /**
     * This method deletes a challenge from DB by its Id
     *
     * @param challengeId Challenge Id
     *
     * @return Erased Challenge. Null if a match was not found.
     */
    @Override
    public Challenge deleteById(int challengeId) {
        Challenge challenge = null;
        try {
            challenge = findById(challengeId);
            if (challenge != null) {
                challengeDao.deleteById(challengeId);
            }
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Error while finding Challenge by Id", e);
        }
        return challenge;
    }
}
