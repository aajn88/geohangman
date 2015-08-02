package com.doers.games.geohangman.managers;

import com.doers.games.geohangman.model.Challenge;

import java.sql.SQLException;
import java.util.List;

/**
 * This interface represents the Challenges Manager who offers all CRUD services for Services in DB
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IChallengesManager {

    /**
     * Creates a challenge in DB
     *
     * @param challenge Challenge to be created
     *
     * @return Updated challenge
     */
    Challenge create(Challenge challenge) throws SQLException;

    /**
     * Finds a Challenge by its Id
     *
     * @param challengeId ChallengeId
     *
     * @return A challenge if a match was found. Otherwise returns null
     */
    Challenge findById(int challengeId);

    /**
     * Returns all existing Challenges
     *
     * @return All existing challenges
     */
    List<Challenge> findAll();

    /**
     * This method deletes a challenge from DB by its Id
     *
     * @param challengeId Challenge Id
     * @return Erased Challenge. Null if a match was not found.
     */
    Challenge deleteById(int challengeId);

}
