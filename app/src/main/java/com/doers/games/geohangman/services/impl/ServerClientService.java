package com.doers.games.geohangman.services.impl;

import android.content.Context;
import android.util.Log;

import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.constants.ServerUrlTypes;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.model.restful.CreateUpdateFriendsRequest;
import com.doers.games.geohangman.model.restful.CreateUpdateUserRequest;
import com.doers.games.geohangman.model.restful.GoogleProfilePicResponse;
import com.doers.games.geohangman.services.IServerClientService;
import com.doers.games.geohangman.utils.RestUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * IServerClientService implementation
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class ServerClientService implements IServerClientService {

    /** Properties File name * */
    private static final String PROPERTIES_FILE = "server.properties";

    /** Current context * */
    @Inject
    private Context context;

    /** ServerClientService properties * */
    private Properties properties;

    /**
     * Get a configured properties instance
     *
     * @return Configured properties instance
     *
     * @throws IOException
     */
    private Properties getProperties() throws IOException {
        if (properties == null) {
            properties = ServerClientServiceHelper.buildProperties(context, PROPERTIES_FILE);
        }
        return properties;
    }

    /**
     * This method sends a user to the server to be created or updated
     *
     * @param user to be created/updated
     */
    @Override
    public void createOrUpdateUser(UserInfo user) throws NoSuchAlgorithmException, IOException {

        createUser(user);

        createFriends(user);
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

        String registeredFriendsUrl = ServerClientServiceHelper
                .getUrl(getProperties(), ServerUrlTypes.REGISTERED_FRIENDS);

        UserInfo []registeredFriends = RestUtils
                .get(String.format(registeredFriendsUrl, userId), UserInfo[].class);

        return Arrays.asList(registeredFriends);
    }

    /**
     * Retrieves the Google Profile picture URL
     *
     * @param userId userId to be searched
     *
     * @return Profile Picture URL
     */
    @Override
    public String getGoogleProfilePicUrl(String userId) throws IOException {
        String requestUrl = ServerClientServiceHelper.getUrl(getProperties(), ServerUrlTypes
                .GOOGLE_PROFILE_PICTURE);

        GoogleProfilePicResponse pic = RestUtils.get(String.format(requestUrl, userId),
                GoogleProfilePicResponse.class);

        String profilePicUrl = null;

        if(pic != null) {
            profilePicUrl = pic.getImage().getUrl();
        }

        return profilePicUrl;
    }

    /**
     * This method creates User
     *
     * @param user The user to be created
     *
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private void createUser(UserInfo user) throws IOException, NoSuchAlgorithmException {
        CreateUpdateUserRequest request = ServerClientServiceHelper
                .buildUserRequest(user, getProperties());

        String usersUrl = ServerClientServiceHelper.getUrl(getProperties(), ServerUrlTypes.USERS);

        String response = RestUtils.post(usersUrl, request, String.class);

        if (response != null) {
            user.setId(response);
        }

        Log.d(Messages.REQUEST_RESPONSE_TAG, String.format(Messages.SERVER_RESPONSE, response));
    }

    /**
     * This method creates User's Friends
     *
     * @param user The user
     *
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private void createFriends(UserInfo user) throws IOException, NoSuchAlgorithmException {
        CreateUpdateFriendsRequest request = ServerClientServiceHelper
                .buildUserFriendsRequest(user.getFriends(), getProperties());

        String usersUrl = ServerClientServiceHelper.getUrl(getProperties(), ServerUrlTypes.FRIENDS);
        usersUrl = String.format(usersUrl, user.getId());

        String response = RestUtils.post(usersUrl, request, String.class);

        Log.d(Messages.REQUEST_RESPONSE_TAG, String.format(Messages.SERVER_RESPONSE, response));
    }
}
