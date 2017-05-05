package cz.muni.fi.pb138;

/**
 * Created by Matej on 4.5.2017.
 */
public class Weather {

    double temperature; //Celsius
    int humidity; //Percent
    int pressure; //hectopascal
    double windSpeed; //meters per hour
    String description;
    String icon; //icon URL
    String locName;
    String mainDescription;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = "http://openweathermap.org/img/w/" + icon + ".png";
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        if (Double.compare(weather.temperature, temperature) != 0) return false;
        if (humidity != weather.humidity) return false;
        if (pressure != weather.pressure) return false;
        if (Double.compare(weather.windSpeed, windSpeed) != 0) return false;
        if (description != null ? !description.equals(weather.description) : weather.description != null) return false;
        if (icon != null ? !icon.equals(weather.icon) : weather.icon != null) return false;
        if (locName != null ? !locName.equals(weather.locName) : weather.locName != null) return false;
        return mainDescription != null ? mainDescription.equals(weather.mainDescription) : weather.mainDescription == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(temperature);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + humidity;
        result = 31 * result + pressure;
        temp = Double.doubleToLongBits(windSpeed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (locName != null ? locName.hashCode() : 0);
        result = 31 * result + (mainDescription != null ? mainDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", locName='" + locName + '\'' +
                ", mainDescription='" + mainDescription + '\'' +
                '}';
    }
}
