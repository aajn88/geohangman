package com.doers.games.geohangman.services.impl;

import android.graphics.Bitmap;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.os.Build;

import com.doers.games.geohangman.BuildConfig;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.restful.CreateChallengeResponse;
import com.doers.games.geohangman.model.restful.GetChallengeImageResponse;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.doers.games.geohangman.services.IServerClientService;
import com.doers.games.geohangman.services.IUsersService;
import com.doers.games.geohangman.utils.ChallengeUtils;
import com.doers.games.geohangman.utils.ImageUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;

/**
 * This is the GeoHangman Main Service.
 * <p/>
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

    /** The users service * */
    @Inject
    private IUsersService usersService;

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
    public void storePic(Bitmap pic) {
        challenge.setPic(pic);
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
        Challenge.MapPoint mapPoint = new Challenge.MapPoint();
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
    public Challenge.MapPoint getStoredLocation() {
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
     * This method build NdefMessage based on the Challenge to be sent through NFC.
     * <p/>
     * This NdefMessage has two NdefRecords, one for challenge pic and the other one with challenge
     * args:
     * <p/>
     * NdefRecord[0] = challengeImage NdefRecord[1] = args separated by |, i.e.:
     * "(word)|(lat)|(lng)|(zoom)" -> "MyWord|1.1212313|4.1132133|10.0"
     *
     * @return NdefMessage with Challenge args and pics in separated NdefRecords
     */
    @Override
    public NdefMessage buildNdefMessage() {
        byte[] image = ImageUtils.buildBitmapByteArray(challenge.getPic());
        String args = ChallengeUtils.buildOtherChallengeArgs(challenge);

        return buildNdefMessage(image, args);
    }

    /**
     * This method creates the NdefMessage based on the challenge image and args
     *
     * @param challengeImage in a byte array
     * @param challengeArgs  separated by |, i.e.: "(word)|(lat)|(lng)|(zoom)" ->
     *                       "MyWord|1.1212313|4.1132133|10.0"
     *
     * @return the NdefMessage to be sent
     */
    private NdefMessage buildNdefMessage(byte[] challengeImage, String challengeArgs) {
        NdefRecord picRecord = NdefRecord
                .createMime(APPLICATION_TAG + BuildConfig.APPLICATION_ID, challengeImage);
        NdefRecord argsRecord = NdefRecord
                .createMime(APPLICATION_TAG + BuildConfig.APPLICATION_ID, challengeArgs.getBytes());

        NdefMessage message;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && false) {
            message = new NdefMessage(new NdefRecord[]{picRecord, argsRecord,
                    NdefRecord.createApplicationRecord(BuildConfig.APPLICATION_ID)});
        } else {
            message = new NdefMessage(new NdefRecord[]{argsRecord, picRecord});
        }

        return message;
    }

    /**
     * This method receives the image bytes and challengeArgs to start the Challenge
     *
     * @param image         byte array
     * @param challengeArgs args separated by |, i.e.: "(word)|(lat)|(lng)|(zoom)" ->
     *                      "MyWord|1.1212313|4.1132133|10.0"
     */
    @Override
    public void startChallenge(byte[] image, String challengeArgs) {
        challenge = ChallengeUtils.parseChallenge(image, challengeArgs);
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
    public void sendChallengeToOpponent(String opponentId) throws IOException {
        CreateChallengeResponse response = serverClientService
                .createChallenge(challenge, usersService.getCurrentUser().getId(), opponentId);
    }

    /**
     * This method requests challenge image from server given a challengeId
     *
     * @param challengeId The challenge Id
     *
     * @return Respective challenge image
     */
    @Override
    public byte[] requestChallengeImage(Integer challengeId) throws IOException {
        GetChallengeImageResponse response = serverClientService.getChallengeImage(challengeId);
        return response.getImageBytes().getBytes();
    }

}
