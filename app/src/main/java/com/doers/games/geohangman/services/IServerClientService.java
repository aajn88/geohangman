package com.doers.games.geohangman.services;

import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.model.restful.CreateChallengeResponse;
import com.doers.games.geohangman.model.restful.GetChallengeImageResponse;
import com.doers.games.geohangman.model.restful.GetChallengeResponse;

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
     *
     * @return Profile Picture URL
     */
    String getGoogleProfilePicUrl(String userId) throws IOException;

    /**
     * This method sends a create challenge request to the server. Then its response is returned
     *
     * @param challenge The challenge to be sent
     *
     * @return Server's response
     */
    CreateChallengeResponse createChallenge(Challenge challenge) throws IOException;

    /**
     * This method gets the challenge image given a challengeId
     *
     * @param challengeId The challengeId
     *
     * @return The server response for GetChallengeImage request
     */
    GetChallengeImageResponse getChallengeImageUrl(Integer challengeId) throws IOException;

    /**
     * This method requests a given challenge
     *
     * @param challengeId The challenge to be requested
     *
     * @return The result challenge if exists, otherwise returns null
     */
    GetChallengeResponse getChallenge(Integer challengeId) throws IOException;

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
    String createOrUpdateToken(String userId, String token) throws IOException;

}
