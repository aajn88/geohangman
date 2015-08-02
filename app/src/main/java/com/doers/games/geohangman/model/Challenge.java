package com.doers.games.geohangman.model;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * This class represents the challenge to be sent to the opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class Challenge {

    /** Challenge Id * */
    @DatabaseField(generatedId = true)
    private Integer id;

    /** Challenger Id * */
    @DatabaseField
    private String challengerId;

    /** Opponent Id * */
    @DatabaseField
    private String opponentId;

    /** Pic taken * */
    private Bitmap pic;

    /** Pic Uri * */
    @DatabaseField
    private String picPath;

    /** Word to be guessed * */
    @DatabaseField
    private String word;

    /** Map Point * */
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private MapPoint mapPoint;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the challengerId
     */
    public String getChallengerId() {
        return challengerId;
    }

    /**
     * @return challengerId the challengerId to set
     */
    public void setChallengerId(String challengerId) {
        this.challengerId = challengerId;
    }

    /**
     * @return the opponentId
     */
    public String getOpponentId() {
        return opponentId;
    }

    /**
     * @return opponentId the opponentId to set
     */
    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    /**
     * @return the pic
     */
    public Bitmap getPic() {
        return pic;
    }

    /**
     * @return pic the pic to set
     */
    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    /**
     * @return the picPath
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * @return picPath the picPath to set
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @return word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @return the mapPoint
     */
    public MapPoint getMapPoint() {
        return mapPoint;
    }

    /**
     * @return mapPoint the mapPoint to set
     */
    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
    }
}
