package com.doers.games.geohangman.model;

import java.io.Serializable;

/**
 * This class represents the MapPoint of the challenge
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class MapPoint implements Serializable {

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
