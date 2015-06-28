package com.doers.games.geohangman.services.impl;

import android.content.Context;
import android.util.Log;

import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.constants.ServerUrlTypes;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.model.restful.CreateChallengeImageRequest;
import com.doers.games.geohangman.model.restful.CreateChallengeImageResponse;
import com.doers.games.geohangman.model.restful.CreateChallengeRequest;
import com.doers.games.geohangman.model.restful.CreateChallengeResponse;
import com.doers.games.geohangman.model.restful.CreateUpdateFriendsRequest;
import com.doers.games.geohangman.model.restful.CreateUpdateUserRequest;
import com.doers.games.geohangman.model.restful.GetChallengeImageResponse;
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

        UserInfo[] registeredFriends = RestUtils
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
        String requestUrl = ServerClientServiceHelper
                .getUrl(getProperties(), ServerUrlTypes.GOOGLE_PROFILE_PICTURE);

        GoogleProfilePicResponse pic = RestUtils
                .get(String.format(requestUrl, userId), GoogleProfilePicResponse.class);

        String profilePicUrl = null;

        if (pic != null) {
            profilePicUrl = pic.getImage().getUrl();
        }

        return profilePicUrl;
    }

    /**
     * This method sends a create challenge request to the server. Then its response is returned
     *
     * @param challenge    To be sent
     * @param challengerId The challenger Id
     * @param opponentId   The target opponent who will receive the challenge
     *
     * @return Server's response
     */
    @Override
    public CreateChallengeResponse createChallenge(Challenge challenge, String challengerId,
                                                   String opponentId) throws IOException {
        CreateChallengeResponse response = sendBasicChallenge(challenge, challengerId, opponentId);

        challenge.setId(response.getChallengeId());

        CreateChallengeImageResponse imageResponse = sendImageChallenge(challenge);

        Log.d(Messages.CREATED_CHALLENGE_TAG, String.format(Messages.CREATED_CHALLENGE_MSG,
                challenge.getId(), imageResponse.getChallengeImageId()));

        return response;
    }

    /**
     * This method gets the challenge image given a challengeId
     *
     * @param challengeId The challengeId
     *
     * @return The server response for GetChallengeImage request
     */
    @Override
    public GetChallengeImageResponse getChallengeImage(Integer challengeId) throws IOException {

        String getChallengeImageUrl = ServerClientServiceHelper.getUrl(getProperties(),
                ServerUrlTypes.REQUEST_CHALLENGES_IMAGE);

        getChallengeImageUrl = String.format(getChallengeImageUrl, challengeId);

        return RestUtils.get(getChallengeImageUrl, GetChallengeImageResponse.class);
    }

    private CreateChallengeImageResponse sendImageChallenge(Challenge challenge) throws
            IOException {

        CreateChallengeImageRequest request = ServerClientServiceHelper
                .buildCreateImageChallengeRequest(challenge);

        String createImageUrl = ServerClientServiceHelper.getUrl(getProperties(), ServerUrlTypes
                .CHALLENGES_IMAGE);

        return RestUtils.post(createImageUrl, request, CreateChallengeImageResponse.class);
    }

    /**
     * This method sends the basic Challenge Information to server (all information but image)
     *
     * @param challenge The Challenge to be sent
     * @param challengerId The Challenger Id
     * @param opponentId The opponent Id
     * @return Request response by server
     * @throws IOException If a communication error occurs
     */
    private CreateChallengeResponse sendBasicChallenge(Challenge challenge, String challengerId,
                                                       String opponentId) throws IOException {
        CreateChallengeRequest request = ServerClientServiceHelper
                .buildCreateChallengeRequest(challenge, challengerId, opponentId);

        String challengesUrl = ServerClientServiceHelper
                .getUrl(getProperties(), ServerUrlTypes.CHALLENGES);

        return RestUtils.post(challengesUrl, request, CreateChallengeResponse.class);
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
