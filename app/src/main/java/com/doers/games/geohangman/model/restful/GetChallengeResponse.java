package com.doers.games.geohangman.model.restful;

/**
 * This class is the response of the Challenge request
 *
 * @author @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class GetChallengeResponse extends AbstractResponse {

    /** The challenge Id * */
    private Integer id;

    /** Challenger Id * */
    private String challengerId;

    /** Opponent Id * */
    private String opponentId;

    /** The word to be guessed * */
    private String word;

    /** Map point Latitude * */
    private Double lat;

    /** Map point Longitude * */
    private Double lng;

    /** Map point zoom * */
    private Float zoom;

    /** Indicates if challenge has been already played * */
    private boolean played;

    /** The image Id in the Server * */
    private Integer imageId;

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
     * @return the lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     * @return lat the lat to set
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * @return the lng
     */
    public Double getLng() {
        return lng;
    }

    /**
     * @return lng the lng to set
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     * @return the zoom
     */
    public Float getZoom() {
        return zoom;
    }

    /**
     * @return zoom the zoom to set
     */
    public void setZoom(Float zoom) {
        this.zoom = zoom;
    }

    /**
     * @return the played
     */
    public boolean isPlayed() {
        return played;
    }

    /**
     * @return played the played to set
     */
    public void setPlayed(boolean played) {
        this.played = played;
    }

    /**
     * @return the imageId
     */
    public Integer getImageId() {
        return imageId;
    }

    /**
     * @return imageId the imageId to set
     */
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
