package com.doers.games.geohangman.services;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Antonio on 13/05/2015.
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
     * @param location to be stored
     * @param zoom to be stored
     */
    void storeLocation(LatLng location, float zoom);

    /**
     * This method stores the word to be guessed by the opponent.
     *
     * @param word to be guessed by the opponent
     */
    void storeWord(String word);

    /**
     * This method sets up files to be sent through NFC
     */
    void sendChallenge();

}
