package cz.muni.fi.pb138.trafficmap.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.aksingh.owmjapis.CurrentWeather;

import java.io.IOException;

/**
 * Created by Matej on 20.5.2017.
 */
public class WeatherSerializer extends JsonSerializer<CurrentWeather> {
    @Override
    public void serialize(CurrentWeather currentWeather, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {

        jGen.writeStartObject();
        jGen.writeStringField("currentTemp", String.valueOf(currentWeather.getMainInstance().getTemperature()));
        jGen.writeStringField("maxTemp", String.valueOf(currentWeather.getMainInstance().getMaxTemperature()));
        jGen.writeStringField("minTemp", String.valueOf(currentWeather.getMainInstance().getMinTemperature()));
        jGen.writeStringField("pressure", String.valueOf(currentWeather.getMainInstance().getPressure()));
        jGen.writeStringField("humidity", String.valueOf(currentWeather.getMainInstance().getHumidity()));
        jGen.writeStringField("windSpeed", String.valueOf(currentWeather.getWindInstance().getWindSpeed()));
        jGen.writeStringField("windDegrees", String.valueOf(currentWeather.getWindInstance().getWindDegree()));
        jGen.writeStringField("windDirection", getWindDirection(currentWeather.getWindInstance().getWindDegree()));
        jGen.writeStringField("weatherDesc", currentWeather.getWeatherInstance(0).getWeatherDescription());
        jGen.writeStringField("weatherName", currentWeather.getWeatherInstance(0).getWeatherName());
        jGen.writeStringField("weatherIcon", "http://openweathermap.org/img/w/" + currentWeather.getWeatherInstance(0).getWeatherIconName() + ".png");
        jGen.writeStringField("locationName", currentWeather.getCityName());
        jGen.writeEndObject();

    }

    private String getWindDirection(float degree) {
        if (degree > 337.5) return "N";
        if (degree > 292.5) return "NW";
        if (degree > 247.5) return "W";
        if (degree > 202.5) return "SW";
        if (degree > 157.5) return "S";
        if (degree > 122.5) return "SE";
        if (degree > 67.5) return "E";
        if (degree > 22.5) return "NE";

        return "N";
    }
}
