package com.doers.games.geohangman.services.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.doers.games.geohangman.managers.IChallengesManager;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.model.restful.GetChallengeImageResponse;
import com.doers.games.geohangman.model.restful.GetChallengeResponse;
import com.doers.games.geohangman.model.MapPoint;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.doers.games.geohangman.services.IServerClientService;
import com.doers.games.geohangman.services.IUsersService;
import com.doers.games.geohangman.services.android_services.UploadChallengeService;
import com.doers.games.geohangman.utils.ChallengeUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This is the GeoHangman Main Service.
 * <p>
 * Here is where challenge is stored and sent to the opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class GeoHangmanService implements IGeoHangmanService {

    /** Application tag for beam * */
    private static final String APPLICATION_TAG = "application/";

    /** Current challenge to be sent * */
    private Challenge challenge;

    /** The server client service * */
    @Inject
    private IServerClientService serverClientService;

    /** The Challenges Manager **/
    @Inject
    private IChallengesManager challengesManager;

    /** The users service * */
    @Inject
    private IUsersService usersService;

    /** Current Context **/
    @Inject
    private Context context;

    /**
     * GeoHangman no-parameters constructor
     */
    public GeoHangmanService() {
        challenge = new Challenge();
    }

    /**
     * This method receives an image to be stored and then sent
     */
    @Override
    public void storePic(Bitmap pic, Uri picUri) {
        challenge.setPic(pic);
        challenge.setPicPath(picUri.getPath());
    }

    /**
     * This method returns the stored Pic
     *
     * @return A reference to the stored Pic or null if it doesn't exist
     */
    @Override
    public Bitmap getStoredPic() {
        return challenge.getPic();
    }

    /**
     * This method receives a map location (latitude and longitude) and an specific zoom value
     *
     * @param lat  latitude value to be stored
     * @param lng  longitude value to be stored
     * @param zoom to be stored
     */
    @Override
    public void storeLocation(double lat, double lng, float zoom) {
        MapPoint mapPoint = new MapPoint();
        mapPoint.setLat(lat);
        mapPoint.setLng(lng);
        mapPoint.setZoom(zoom);

        challenge.setMapPoint(mapPoint);
    }

    /**
     * This method clears stored location
     */
    @Override
    public void clearLocation() {
        challenge.setMapPoint(null);
    }

    /**
     * Returns stored location
     *
     * @return Stored location or null if it does not exist
     */
    @Override
    public MapPoint getStoredLocation() {
        return challenge.getMapPoint();
    }

    /**
     * This method stores the word to be guessed by the opponent.
     *
     * @param word to be guessed by the opponent
     */
    @Override
    public void storeWord(String word) {
        challenge.setWord(word);
    }

    /**
     * This method returns the stored word to be guessed
     *
     * @return Word to be guessed or null if it does not exist
     */
    @Override
    public String getStoredWord() {
        return challenge.getWord();
    }

    /**
     * * This method receives the image bytes and challengeArgs to start the Challenge
     *
     * @param challengeId The challenge Id to be requested
     *
     * @throws IOException
     * @throws IllegalArgumentException This exception is thrown when the challenge does not exist
     *                                  or has been already played
     */
    @Override
    public void startChallenge(Integer challengeId) throws IOException, IllegalArgumentException {
        GetChallengeResponse challengeResponse = serverClientService.getChallenge(challengeId);
        if (challengeResponse != null && challengeResponse.isPlayed()) {
            throw new IllegalArgumentException(
                    "This challenge does not exist or has been already played!");
        }
        String imageUrl = requestChallengeImageUrl(challengeId);

        this.challenge = ChallengeUtils.parseChallenge(challengeResponse, imageUrl);
    }

    /**
     * This method verifies if that a given word is exactly the Challenge word to be guessed
     *
     * @param word to verify
     *
     * @return True if there's a match, otherwise returns False
     */
    public Boolean verifyWord(String word) {
        return getStoredWord().equals(word);
    }

    /**
     * This method restarts all challenge
     */
    @Override
    public void restartAll() {
        this.challenge = new Challenge();
    }

    /**
     * This method sends the challenge to a given opponent. This challenge is sent to the server and
     * the server stores the challenge and notify opponent about the challenge
     *
     * @param opponentId The opponent Id
     */
    @Override
    public void sendChallengeToOpponent(String opponentId) throws IOException, SQLException {
        UserInfo userInfo = usersService.getCurrentUser();
        challenge.setOpponentId(opponentId);
        challenge.setChallengerId(userInfo.getId());
        challengesManager.create(challenge);
        Intent uploadChallengeServiceIntent = new Intent(context, UploadChallengeService.class);
        context.startService(uploadChallengeServiceIntent);
    }

    /**
     * This method requests challenge image from server given a challengeId
     *
     * @param challengeId The challenge Id
     *
     * @return Respective challenge image
     */
    @Override
    public String requestChallengeImageUrl(Integer challengeId) throws IOException {
        GetChallengeImageResponse response = serverClientService.getChallengeImageUrl(challengeId);
        return response.getImageUrl();
    }

}
