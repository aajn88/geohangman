package com.doers.games.geohangman.services;

import com.doers.games.geohangman.model.UserInfo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Server Restful Client Service
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IServerClientService {

    /**
     * This method sends a user to the server to be created or updated
     *
     * @param user to be created/updated
     */
    void createOrUpdateUser(UserInfo user) throws NoSuchAlgorithmException, IOException;

    /**
     * Retrieves registered friends for a given user
     *
     * @param userId The User Id
     *
     * @return User's friends
     */
    List<UserInfo> getRegisteredFriends(String userId) throws IOException;

    /**
     * Retrieves the Google Profile picture URL
     *
     * @param userId userId to be searched
     * @return Profile Picture URL
     */
    String getGoogleProfilePicUrl(String userId) throws IOException;

}
