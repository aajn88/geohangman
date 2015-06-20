package com.doers.games.geohangman.model.restful;

import com.doers.games.geohangman.constants.ResponseCode;

/**
 * @author @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 *
 */
public class AbstractResponse {

	private ResponseCode responseCode;

	/**
	 * Default Constructor. By default, responseCode will be SUCCESS
	 */
	public AbstractResponse() {
		this.responseCode = ResponseCode.SUCCESS;
	}

	/**
	 * @return the responseCode
	 */
	public ResponseCode getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode
	 *            the responseCode to set
	 */
	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

}
