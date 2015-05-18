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
    private MapPoint mapPoint;

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

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
    }

    /**
     * This class represents the MapPoint of the challenge
     */
    public static class MapPoint {

        /** Lat on map **/
        private double lat;

        /** Lng on map **/
        private double lng;

        /** Zoom to be used **/
        private float zoom;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public float getZoom() {
            return zoom;
        }

        public void setZoom(float zoom) {
            this.zoom = zoom;
        }
    }

}
