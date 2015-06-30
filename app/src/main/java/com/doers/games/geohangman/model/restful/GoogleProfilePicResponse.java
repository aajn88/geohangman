package com.doers.games.geohangman.model.restful;

/**
 * This class contains the Google Profile Pic Response
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class GoogleProfilePicResponse {

    /** Image information sent by Google * */
    private Image image;

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @return image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Image class that is nested into Google response
     */
    public class Image {

        /** Profile picture URL * */
        private String url;

        /** Field isDefault * */
        private Boolean isDefault;

        /**
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @return url the url to set
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * @return the isDefault
         */
        public Boolean isDefault() {
            return isDefault;
        }

        /**
         * @return isDefault the isDefault to set
         */
        public void setIsDefault(Boolean isDefault) {
            this.isDefault = isDefault;
        }
    }

}
