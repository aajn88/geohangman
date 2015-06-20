package com.doers.games.geohangman.services.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.services.IServerClientService;
import com.doers.games.geohangman.services.IUsersService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Implementation for UsersService
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class UsersService implements IUsersService {

    /** Server Client Services **/
    @Inject
    private IServerClientService serverClient;

    /** Current User in session **/
    private UserInfo currentUser;

    /**
     * This method stores current User. It sends it to GeoHangman Server to store it
     *
     * @param currentUser to be stored
     */
    @Override
    public void storeCurrentUser(UserInfo currentUser) throws IOException,
            NoSuchAlgorithmException {
        serverClient.createOrUpdateUser(currentUser);
        this.currentUser = currentUser;
    }

    /**
     * Returns a reference of the current user
     *
     * @return Current User in session
     */
    @Override
    public UserInfo getCurrentUser() {
        return currentUser;
    }

    /**
     * Retrieves registered friends for a given user
     *
     * @param userId The User Id
     *
     * @return User's friends
     */
    @Override
    public List<UserInfo> getRegisteredFriends(String userId) throws IOException {
        return serverClient.getRegisteredFriends(userId);
    }

    /**
     * This method retrieves a user's profile picture given his id
     *
     * @param userId The user id
     *
     * @return A Bitmap that contains the profile picture. Otherwise returns null
     */
    @Override
    public Bitmap retrieveProfilePicture(String userId) throws IOException {
        String profilePicUrl =  serverClient.getGoogleProfilePicUrl(userId);
        Log.d("PROFILE_PIC_URL", "ProfilePic: " + profilePicUrl);
        URL picUrl = new URL(profilePicUrl);
        return BitmapFactory.decodeStream(picUrl.openConnection().getInputStream());
    }
}
