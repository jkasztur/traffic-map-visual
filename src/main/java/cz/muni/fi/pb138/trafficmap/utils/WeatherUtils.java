package cz.muni.fi.pb138.trafficmap.utils;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Uses http://openweathermap.org/ API to download current weather information.
 */
public class WeatherUtils {

	private static final String WEATHER_API_KEY = "8797b4d7c4f89c3decc803862f4574eb";
	private static final OpenWeatherMap owm = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, WEATHER_API_KEY);

	private final static Logger log = LoggerFactory.getLogger(WeatherUtils.class);

	/**
	 * Returns weather document that contains weather information
	 * Example can be found at src/main/resources/weather_example.xml
	 *
	 * @param x longitude
	 * @param y latitude
	 */
	public static Document getWeatherAtLocation(float x, float y) throws ParserConfigurationException {
		final CurrentWeather cw = owm.currentWeatherByCoordinates(y, x);
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

	private static String transformToXmlString(CurrentWeather cw) {
		final JSONObject json = new JSONObject(cw.getRawResponse());
		String s = "<weather_root>";
		s += XML.toString(json);
		s += "</weather_root>";
		return s;
	}
}
