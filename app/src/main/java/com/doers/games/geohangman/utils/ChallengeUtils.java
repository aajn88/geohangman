package com.doers.games.geohangman.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.restful.GetChallengeResponse;
import com.doers.games.geohangman.model.MapPoint;

import java.io.IOException;
import java.net.URL;

/**
 * This ChallengeUtils helps to process basic Challenge operations
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class ChallengeUtils {

    /** Private no-parameters constructor * */
    private ChallengeUtils() {}

    /**
     * This method parsers server response of challenge and loads the challenge image given its url
     *
     * @param challengeResponse This is the server response
     * @param imageUrl          Where image is located
     *
     * @return Parsed Challenge
     */
    public static Challenge parseChallenge(GetChallengeResponse challengeResponse,
                                           String imageUrl) throws IOException {

        Challenge challenge = new Challenge();

        URL url = new URL(imageUrl);
        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        completeChallenge(challenge, challengeResponse);

        challenge.setPic(image);

        return challenge;
    }

    /**
     * This method fills challenge fields with challengeArgs (word and MapPoint)
     *
     * @param challenge     Challenge where args will be added
     * @param challengeResponse This is the server response
     */
    private static void completeChallenge(Challenge challenge, GetChallengeResponse challengeResponse) {
        MapPoint mapPoint = new MapPoint();

        challenge.setWord(challengeResponse.getWord());
        mapPoint.setLat(challengeResponse.getLat());
        mapPoint.setLng(challengeResponse.getLng());
        mapPoint.setZoom(challengeResponse.getZoom());
        challenge.setMapPoint(mapPoint);

    }

}
