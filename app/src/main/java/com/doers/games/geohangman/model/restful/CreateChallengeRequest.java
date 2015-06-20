package com.doers.games.geohangman.model.restful;

/**
 * 
 * Create Challenge Request
 * 
 * @author @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 *
 */
public class CreateChallengeRequest extends AbstractRequest {

	/** Challenger Id **/
	private String challengerId;

	/** Opponent Id **/
	private String opponentId;

	/** The word to be guessed **/
	private String word;

	/** Map point Latitude **/
	private Double lat;

	/** Map point Longitude **/
	private Double lng;

	/** Map point zoom **/
	private Float zoom;

	/**
	 * @return the challengerId
	 */
	public String getChallengerId() {
		return challengerId;
	}

	/**
	 * @param challengerId
	 *            the challengerId to set
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
	 * @param opponentId
	 *            the opponentId to set
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
	 * @param word
	 *            the word to set
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
	 * @param lat
	 *            the lat to set
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
	 * @param lng
	 *            the lng to set
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
	 * @param zoom
	 *            the zoom to set
	 */
	public void setZoom(Float zoom) {
		this.zoom = zoom;
	}

}
