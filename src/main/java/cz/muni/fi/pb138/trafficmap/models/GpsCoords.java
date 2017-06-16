package cz.muni.fi.pb138.trafficmap.models;

/**
 * GPS coordinates.
 *
 * Created by Matej on 4.5.2017.
 */
public class GpsCoords {

    private double lat;
    private double lng;

    public GpsCoords(double lng, double lat) {
        this.lat = lat;
        this.lng = lng;
    }

    public GpsCoords() {
        this.lat = 16.599346;
        this.lng = 49.210087;
    }

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

    @Override
    public String toString() {
        return "GpsCoords{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public String toSimpleString() {
        return lat + ", " + lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GpsCoords gpsCoords = (GpsCoords) o;

        if (Double.compare(gpsCoords.lat, lat) != 0) return false;
        return Double.compare(gpsCoords.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
