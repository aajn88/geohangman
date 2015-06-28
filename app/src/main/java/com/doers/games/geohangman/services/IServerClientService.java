package com.doers.games.geohangman.services;

import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.model.restful.CreateChallengeResponse;
import com.doers.games.geohangman.model.restful.GetChallengeImageResponse;

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

    /**
     * This method sends a create challenge request to the server. Then its response is returned
     *
     * @param challenge The challenge to be sent
     * @param challengerId The challenger Id
     * @param opponentId The target opponent who will receive the challenge
     * @return Server's response
     */
    CreateChallengeResponse createChallenge(Challenge challenge, String challengerId, String
                                                   opponentId) throws IOException;

    /**
     * This method gets the challenge image given a challengeId
     *
     * @param challengeId The challengeId
     * @return The server response for GetChallengeImage request
     */
    GetChallengeImageResponse getChallengeImage(Integer challengeId) throws IOException;

}
