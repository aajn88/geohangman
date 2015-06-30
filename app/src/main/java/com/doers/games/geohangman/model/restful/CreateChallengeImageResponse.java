package com.doers.games.geohangman.model.restful;

/**
 * Create Challenge Image Response that will return Create Challenge Image result as
 * challengeImageId
 *
 * @author @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class CreateChallengeImageResponse extends AbstractResponse {

    /** The image Url **/
    private String imageUrl;

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @return imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
