package com.doers.games.geohangman.services;

import android.graphics.Bitmap;

import com.doers.games.geohangman.model.UserInfo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * This service espouses all User services
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IUsersService {

    /**
     * This method stores current User. It sends it to GeoHangman Server to store it
     *
     * @param currentUser to be stored
     */
    void storeCurrentUser(UserInfo currentUser) throws IOException, NoSuchAlgorithmException;

    /**
     * Returns a reference of the current user
     *
     * @return Current User in session
     */
    UserInfo getCurrentUser();

    /**
     * Retrieves registered friends for a given user
     *
     * @param userId The User Id
     *
     * @return User's friends
     */
    List<UserInfo> getRegisteredFriends(String userId) throws IOException;

    /**
     * This method retrieves a user's profile picture given his id
     *
     * @param userId The user id
     *
     * @return A Bitmap that contains the profile picture. Otherwise returns null
     */
    Bitmap retrieveProfilePicture(String userId) throws IOException;

}
