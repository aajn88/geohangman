package com.doers.games.geohangman.model.restful;

/**
 * Create Challenge Image Request that will store challenge Image information as
 * challengeId and the Image bytes
 * 
 * @author @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 *
 */
public class CreateChallengeImageRequest extends AbstractRequest {

	/** Challenge which image will be attached **/
	private Integer challengeId;

	/** The image bytes **/
	private byte[] imageBytes;

	/**
	 * @return the challengeId
	 */
	public Integer getChallengeId() {
		return challengeId;
	}

	/**
	 * @param challengeId
	 *            the challengeId to set
	 */
	public void setChallengeId(Integer challengeId) {
		this.challengeId = challengeId;
	}

	/**
	 * @return the imageBytes
	 */
	public byte[] getImageBytes() {
		return imageBytes;
	}

	/**
	 * @param imageBytes
	 *            the imageBytes to set
	 */
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

}
