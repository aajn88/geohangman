package com.doers.games.geohangman.model.restful;

/**
 * Create Challenge Image Response that will return Create Challenge Image
 * result as challengeImageId
 * 
 * @author @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 *
 */
public class CreateChallengeImageResponse extends AbstractResponse {

	/** The Challenge Image Id **/
	private Integer challengeImageId;

	/**
	 * @return the challengeImageId
	 */
	public Integer getChallengeImageId() {
		return challengeImageId;
	}

	/**
	 * @param challengeImageId
	 *            the challengeImageId to set
	 */
	public void setChallengeImageId(Integer challengeImageId) {
		this.challengeImageId = challengeImageId;
	}

}
