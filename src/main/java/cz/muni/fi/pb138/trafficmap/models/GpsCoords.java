package cz.muni.fi.pb138.trafficmap.models;

/**
 * Created by Matej on 4.5.2017.
 */
public class GpsCoords {

    private double latitude;
    private double longitude;

    public GpsCoords(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GpsCoords() {
        this.latitude = 16.599346;
        this.longitude = 49.210087;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GpsCoords{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public String toSimpleString() {
        return latitude + ", " + longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GpsCoords gpsCoords = (GpsCoords) o;

        if (Double.compare(gpsCoords.latitude, latitude) != 0) return false;
        return Double.compare(gpsCoords.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
