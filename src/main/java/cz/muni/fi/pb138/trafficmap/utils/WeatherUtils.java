package cz.muni.fi.pb138.trafficmap.utils;

import lombok.extern.slf4j.Slf4j;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

import static net.aksingh.owmjapis.OpenWeatherMap.Units.METRIC;

/**
 * Uses http://openweathermap.org/ API to download current weather information.
 *
 * @author jkasztur
 */
@Slf4j
public class WeatherUtils {

	private static final String WEATHER_API_KEY = "8797b4d7c4f89c3decc803862f4574eb";
	private static final OpenWeatherMap owm = new OpenWeatherMap(METRIC, WEATHER_API_KEY);


	public static CurrentWeather getWeatherAtLocationObject(float lng, float lat) {
		return owm.currentWeatherByCoordinates(lat, lng);
	}

	/**
	 * Returns weather document that contains weather information
	 * Example can be found at src/main/resources/weather_example.xml
	 *
	 * @param lng longitude
	 * @param lat latitude
	 */
	public static Document getWeatherAtLocationXml(float lng, float lat) throws ParserConfigurationException {
		final CurrentWeather cw = getWeatherAtLocationObject(lng, lat);
		final String str = transformToXmlString(cw);
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document = null;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(str)));
		} catch (SAXException e) {
			log.error("ParserConfigurationException: " + e.getMessage());
		} catch (IOException e) {
			log.error("IOException: " + e.getMessage());
		}
		return document;
	}

	/**
	 * Parse the weather location to JSON.
	 * @param lng longitude
	 * @param lat latitude
	 * @return
	 */
	public static JSONObject getWeatherAtLocationJson(float lng, float lat) {
		final CurrentWeather cw = getWeatherAtLocationObject(lng, lat);
		return new JSONObject(cw.getRawResponse());
	}

	private static String transformToXmlString(CurrentWeather cw) {
		final JSONObject json = new JSONObject(cw.getRawResponse());
		String s = "<weather_root>";
		s += XML.toString(json);
		s += "</weather_root>";
		return s;
	}
}
