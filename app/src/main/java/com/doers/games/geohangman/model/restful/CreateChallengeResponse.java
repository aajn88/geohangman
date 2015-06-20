package com.doers.games.geohangman.model.restful;

/**
 * This class represents the CreateChallenge service response
 * 
 * @author @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 *
 */
public class CreateChallengeResponse extends AbstractResponse {

	/** Created Challenge Id **/
	private Integer challengeId;

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

}
