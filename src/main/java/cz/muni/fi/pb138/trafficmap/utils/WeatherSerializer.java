package cz.muni.fi.pb138.trafficmap.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.aksingh.owmjapis.CurrentWeather;

import java.io.IOException;

/**
 * Created by Matej on 20.5.2017.
 */
public class WeatherSerializer extends JsonSerializer<CurrentWeather> {
    @Override
    public void serialize(CurrentWeather currentWeather, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jGen.writeStartObject();
        jGen.writeStringField("currentTemp", String.valueOf(currentWeather.getMainInstance().getTemperature()));
        jGen.writeStringField("maxTemp", String.valueOf(currentWeather.getMainInstance().getMaxTemperature()));
        jGen.writeStringField("minTemp", String.valueOf(currentWeather.getMainInstance().getMinTemperature()));
        jGen.writeStringField("pressure", String.valueOf(currentWeather.getMainInstance().getPressure()));
        jGen.writeStringField("humidity", String.valueOf(currentWeather.getMainInstance().getHumidity()));
        jGen.writeStringField("windSpeed", String.valueOf(currentWeather.getWindInstance().getWindSpeed()));
        jGen.writeStringField("windDegress", String.valueOf(currentWeather.getWindInstance().getWindDegree()));
        jGen.writeStringField("weatherDesc", String.valueOf(currentWeather.getWeatherInstance(0).getWeatherDescription()));
        jGen.writeStringField("weatherName", String.valueOf(currentWeather.getWeatherInstance(0).getWeatherName()));
        jGen.writeStringField("weatherIcon", String.valueOf(currentWeather.getWeatherInstance(0).getWeatherIconName()));
        jGen.writeStringField("locationName", String.valueOf(currentWeather.getCityName()));
        jGen.writeEndObject();

    }
}
