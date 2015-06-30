package com.doers.games.geohangman.model.restful;

/**
 *
 * This class represents server response when the Challenge Image is requested
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class GetChallengeImageResponse extends AbstractResponse {

    /** The Image Id * */
    private Integer imageId;

    /** The Image Url * */
    private String imageUrl;

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
