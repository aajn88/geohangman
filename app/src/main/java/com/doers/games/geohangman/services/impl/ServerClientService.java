package com.doers.games.geohangman.services.impl;

import android.util.Log;

import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.constants.ServerUrlTypes;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.model.restful.CreateChallengeImageResponse;
import com.doers.games.geohangman.model.restful.CreateChallengeRequest;
import com.doers.games.geohangman.model.restful.CreateChallengeResponse;
import com.doers.games.geohangman.model.restful.CreateUpdateFriendsRequest;
import com.doers.games.geohangman.model.restful.CreateUpdateUserRequest;
import com.doers.games.geohangman.model.restful.GetChallengeImageResponse;
import com.doers.games.geohangman.model.restful.GetChallengeResponse;
import com.doers.games.geohangman.model.restful.GoogleProfilePicResponse;
import com.doers.games.geohangman.services.IServerClientService;
import com.doers.games.geohangman.utils.RestUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IServerClientService implementation
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class ServerClientService implements IServerClientService {

    /** Server Client Service Helper * */
    @Inject
    private ServerClientServiceHelper helper;

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

        String registeredFriendsUrl = helper.getUrl(ServerUrlTypes.REGISTERED_FRIENDS);

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
        String requestUrl = helper.getUrl(ServerUrlTypes.GOOGLE_PROFILE_PICTURE);

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
     * @param challenge To be sent
     *
     * @return Server's response
     */
    @Override
    public CreateChallengeResponse createChallenge(Challenge challenge) throws IOException {
        CreateChallengeResponse response = sendBasicChallenge(challenge);

        challenge.setId(response.getChallengeId());

        CreateChallengeImageResponse imageResponse = sendImageChallenge(challenge);

        Log.d(Messages.CREATED_CHALLENGE_TAG,
                String.format(Messages.CREATED_CHALLENGE_MSG, challenge.getId(),
                        imageResponse.getImageUrl()));

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
    public GetChallengeImageResponse getChallengeImageUrl(Integer challengeId) throws IOException {

        String getChallengeImageUrl = helper.getUrl(ServerUrlTypes.REQUEST_CHALLENGES_IMAGE);

        getChallengeImageUrl = String.format(getChallengeImageUrl, challengeId);

        return RestUtils.get(getChallengeImageUrl, GetChallengeImageResponse.class);
    }

    /**
     * This method requests a given challenge
     *
     * @param challengeId The challenge to be requested
     *
     * @return The result challenge if exists, otherwise returns null
     */
    @Override
    public GetChallengeResponse getChallenge(Integer challengeId) throws IOException {
        String getChallengeUrl = helper.getUrl(ServerUrlTypes.REQUEST_CHALLENGES);

        getChallengeUrl = String.format(getChallengeUrl, challengeId);

        return RestUtils.get(getChallengeUrl, GetChallengeResponse.class);
    }

    /**
     * This method creates or updates a given token in Geohangman Servers
     *
     * @param userId User to be updated
     * @param token  Token to be sent
     *
     * @return User Id if succeeded, otherwise returns null
     *
     * @throws IOException
     */
    @Override
    public String createOrUpdateToken(String userId, String token) throws IOException {
        String tokenUrl = String.format(helper.getUrl(ServerUrlTypes.TOKENS), userId, token);

        String response = null;
        try {
            response = RestUtils.post(tokenUrl, null, String.class);
        } catch (IllegalArgumentException ex) {
            Log.e(Messages.ERROR, "An error has occurred while creating/updating token to server",
                    ex);
        }
        return response;
    }

    private CreateChallengeImageResponse sendImageChallenge(Challenge challenge) throws
            IOException {

        String createImageUrl = String
                .format(helper.getUrl(ServerUrlTypes.CHALLENGES_IMAGE), challenge.getId());

        Map<String, File> files = new HashMap<String, File>();
        files.put("pic", new File(challenge.getPicPath()));

        return RestUtils.postFiles(createImageUrl, null, files, CreateChallengeImageResponse
                .class).getBody();
    }

    /**
     * This method sends the basic Challenge Information to server (all information but image)
     *
     * @param challenge The Challenge to be sent
     *
     * @return Request response by server
     *
     * @throws IOException If a communication error occurs
     */
    private CreateChallengeResponse sendBasicChallenge(Challenge challenge) throws IOException {
        CreateChallengeRequest request = ServerClientServiceHelper
                .buildCreateChallengeRequest(challenge);

        String challengesUrl = helper.getUrl(ServerUrlTypes.CHALLENGES);

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
        CreateUpdateUserRequest request = helper.buildUserRequest(user);

        String usersUrl = helper.getUrl(ServerUrlTypes.USERS);

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
        CreateUpdateFriendsRequest request = helper.buildUserFriendsRequest(user.getFriends());

        String usersUrl = helper.getUrl(ServerUrlTypes.FRIENDS);
        usersUrl = String.format(usersUrl, user.getId());

        String response = RestUtils.post(usersUrl, request, String.class);

        Log.d(Messages.REQUEST_RESPONSE_TAG, String.format(Messages.SERVER_RESPONSE, response));
    }


}
