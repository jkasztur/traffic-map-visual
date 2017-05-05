package cz.muni.fi.pb138;

import java.time.ZonedDateTime;

/**
 * Created by Matej on 4.5.2017.
 */
public class TrafficReport {

    private String message;
    private Weather localWeather;
    private ZonedDateTime activeFrom;
    private ZonedDateTime activeTo;
    private String region;
    private String district;
    private GpsCoords startLoc;
    private GpsCoords endLoc;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Weather getLocalWeather() {
        return localWeather;
    }

    public void setLocalWeather(Weather localWeather) {
        this.localWeather = localWeather;
    }

    public ZonedDateTime getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(ZonedDateTime activeFrom) {
        this.activeFrom = activeFrom;
    }

    public ZonedDateTime getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(ZonedDateTime activeTo) {
        this.activeTo = activeTo;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public GpsCoords getStart() {
        return startLoc;
    }

    public void setStart(GpsCoords startLoc) {
        this.startLoc = startLoc;
    }

    public GpsCoords getEnd() {
        return endLoc;
    }

    public void setEnd(GpsCoords endLoc) {
        this.endLoc = endLoc;
    }

    @Override
    public String toString() {
        return "TrafficReport{" +
                "message='" + message + '\'' +
                ", localWeather=" + localWeather +
                ", activeFrom=" + activeFrom +
                ", activeTo=" + activeTo +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", startLoc=" + startLoc +
                ", endLoc=" + endLoc +
                '}';
    }
}
