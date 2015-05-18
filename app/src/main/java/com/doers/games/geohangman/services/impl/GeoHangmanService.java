package com.doers.games.geohangman.services.impl;

import android.graphics.Bitmap;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.google.android.gms.maps.model.LatLng;
import com.google.inject.Singleton;

import roboguice.inject.InjectResource;

/**
 *
 * This is the GeoHangman Main Service.
 *
 * Here is where challenge is stored and sent to the opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class GeoHangmanService implements IGeoHangmanService {

    /** Current challenge to be sent **/
    private Challenge challenge;

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
     * @param location to be stored
     * @param zoom to be stored
     */
    @Override
    public void storeLocation(LatLng location, float zoom) {

    }

    /**
     * This method stores the word to be guessed by the opponent.
     *
     * @param word to be guessed by the opponent
     */
    @Override
    public void storeWord(String word) {

    }

    /**
     * This method sets up files to be sent through NFC
     */
    @Override
    public void sendChallenge() {

    }
}
