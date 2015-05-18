package com.doers.games.geohangman.model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 *
 * This class represents the challenge to be sent to the opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class Challenge {

    /** Pic taken **/
    private Bitmap pic;

    /** Word to be guessed **/
    private String word;

    /** Map Point **/
    private MapPoint point;

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public MapPoint getPoint() {
        return point;
    }

    public void setPoint(MapPoint point) {
        this.point = point;
    }

    /**
     * This class represents the MapPoint of the challenge
     */
    public static class MapPoint {

        /** Location on map **/
        private LatLng location;

        /** Zoom to be used **/
        private float zoom;

        public LatLng getLocation() {
            return location;
        }

        public void setLocation(LatLng location) {
            this.location = location;
        }

        public float getZoom() {
            return zoom;
        }

        public void setZoom(float zoom) {
            this.zoom = zoom;
        }
    }

}
