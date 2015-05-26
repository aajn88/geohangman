package com.doers.games.geohangman.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.doers.games.geohangman.model.Challenge;

/**
 * This ChallengeUtils helps to process basic Challenge operations
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class ChallengeUtils {

    /** Separator used for challenge args **/
    private static final String SEPARATOR = "|";

    /**
     * This method parsers the variables in the intent to a Challenge
     *
     * @param imageBytes
     * @param challengeArgs in the following format: "(word)|(lat)|(lng)|(zoom)" -> "MyWord|1.1212313|4.1132133|10.0"
     * @return Parsed Challenge
     */
    public static Challenge parseChallenge(byte []imageBytes, String challengeArgs) {

        Challenge challenge = new Challenge();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = Boolean.TRUE;

        Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

        completeChallenge(challenge, challengeArgs);

        challenge.setPic(image);

        return challenge;
    }

    /**
     *
     * This method fills challenge fields with challengeArgs (word and MapPoint)
     *
     * @param challenge Challenge where args will be added
     * @param challengeArgs Challenge args in the following format: "(word)|(lat)|(lng)|(zoom)" -> "MyWord|1.1212313|4.1132133|10.0"
     */
    private static void completeChallenge(Challenge challenge, String challengeArgs) {
        Challenge.MapPoint mapPoint = new Challenge.MapPoint();

        String []args = challengeArgs.split(SEPARATOR);
        challenge.setWord(args[0]);
        mapPoint.setLat(Double.parseDouble(args[1]));
        mapPoint.setLng(Double.parseDouble(args[2]));
        mapPoint.setZoom(Float.parseFloat(args[3]));
        challenge.setMapPoint(mapPoint);

    }

    /**
     * Returns in a single String all the Challenge args separated by |, i.e.:
     *
     * Challenge.word = "MyWord"
     * Challenge.mapPoint.lat = 1.1212313
     * Challenge.mapPoint.lng = 4.1132133
     * Challenge.mapPoint.zoom = 10.0
     *
     * then, this method returns:
     *
     * "MyWord|1.1212313|4.1132133|10.0"
     *
     * @return Challenge args separated by |
     */
    public static String buildOtherChallengeArgs(Challenge challenge) {

        Challenge.MapPoint mp = challenge.getMapPoint();

        StringBuilder sb = new StringBuilder();
        sb.append(challenge.getWord());
        sb.append(SEPARATOR);
        sb.append(mp.getLat());
        sb.append(SEPARATOR);
        sb.append(mp.getLng());
        sb.append(SEPARATOR);
        sb.append(mp.getZoom());

        return sb.toString();
    }

}
