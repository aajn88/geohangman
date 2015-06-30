package com.doers.games.geohangman.model;

import android.graphics.Bitmap;

/**
 * This class represents the challenge to be sent to the opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class Challenge {

    /** Challenge Id * */
    private Integer id;

    /** Pic taken * */
    private Bitmap pic;

    /** Word to be guessed * */
    private String word;

    /** Map Point * */
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

    /**
     * This class represents the MapPoint of the challenge
     */
    public static class MapPoint {

        /** Lat on map * */
        private double lat;

        /** Lng on map * */
        private double lng;

        /** Zoom to be used * */
        private float zoom;

        /**
         * @return the lat
         */
        public double getLat() {
            return lat;
        }

        /**
         * @return lat the lat to set
         */
        public void setLat(double lat) {
            this.lat = lat;
        }

        /**
         * @return the lng
         */
        public double getLng() {
            return lng;
        }

        /**
         * @return lng the lng to set
         */
        public void setLng(double lng) {
            this.lng = lng;
        }

        /**
         * @return the zoom
         */
        public float getZoom() {
            return zoom;
        }

        /**
         * @return zoom the zoom to set
         */
        public void setZoom(float zoom) {
            this.zoom = zoom;
        }
    }

}
