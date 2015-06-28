package com.doers.games.geohangman.services;

import android.graphics.Bitmap;
import android.nfc.NdefMessage;

import com.doers.games.geohangman.model.Challenge;

import java.io.IOException;

/**
 * This is the GeoHangman main Interface. This interface has all main services of GeoHangman.
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IGeoHangmanService {

    /**
     * This method receives an image to be stored as part of the challenge
     *
     * @param pic to be stored
     */
    void storePic(Bitmap pic);

    /**
     * This method returns the stored Pic
     *
     * @return A reference to the stored Pic or null if it doesn't exist
     */
    Bitmap getStoredPic();

    /**
     * This method receives a map location (latitude and longitude) and an specific zoom value
     *
     * @param lat  latitude value to be stored
     * @param lng  longitude value to be stored
     * @param zoom to be stored
     */
    void storeLocation(double lat, double lng, float zoom);

    /**
     * This method clears stored location
     */
    void clearLocation();

    /**
     * Returns stored location
     *
     * @return Stored location or null if it does not exist
     */
    Challenge.MapPoint getStoredLocation();

    /**
     * This method stores the word to be guessed by the opponent.
     *
     * @param word to be guessed by the opponent
     */
    void storeWord(String word);

    /**
     * This method returns the stored word to be guessed
     *
     * @return Word to be guessed or null if it does not exist
     */
    String getStoredWord();

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
    NdefMessage buildNdefMessage();

    /**
     * This method receives the image bytes and challengeArgs to start the Challenge
     *
     * @param image         byte array
     * @param challengeArgs args separated by |, i.e.: "(word)|(lat)|(lng)|(zoom)" ->
     *                      "MyWord|1.1212313|4.1132133|10.0"
     */
    void startChallenge(byte[] image, String challengeArgs);

    /**
     * This method verifies if that a given word is exactly the Challenge word to be guessed
     *
     * @param word to verify
     *
     * @return True if there's a match, otherwise returns False
     */
    Boolean verifyWord(String word);

    /**
     * This method restarts all challenge
     */
    void restartAll();

    /**
     *
     * This method sends the challenge to a given opponent. This challenge is sent to the server
     * and the server stores the challenge and notify opponent about the challenge
     *
     * @param opponentId The opponent Id
     */
    void sendChallengeToOpponent(String opponentId) throws IOException;

    /**
     * This method requests challenge image from server given a challengeId
     *
     * @param challengeId The challenge Id
     * @return Respective challenge image
     */
    byte[] requestChallengeImage(Integer challengeId) throws IOException;

}
