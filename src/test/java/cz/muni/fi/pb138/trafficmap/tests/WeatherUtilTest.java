package cz.muni.fi.pb138.trafficmap.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;

import cz.muni.fi.pb138.trafficmap.utils.WeatherUtils;

public class WeatherUtilTest {

	private final static float LONGITUDE = 16.599277f;
	private final static float LATITUDE = 49.210032f;

	private Document doc;

	@Before
	public void getData() throws ParserConfigurationException {
		doc = WeatherUtils.getWeatherAtLocation(LONGITUDE, LATITUDE);
	}

	@Test
	public void testReturnsSomething() {
		assertTrue(doc != null);
	}

	@Test
	public void testCorrectRoot() {
		Element root = doc.getDocumentElement();
		assertEquals("weather_root", root.getTagName());
	}

	@Test
	public void testCorrectPlace() {
		Node place = doc.getElementsByTagName("name").item(0);
		assertTrue(place != null);
		assertEquals("Veveří", place.getTextContent());
	}

	@Test
	public void testCorrectNodes() {
		assertTrue(doc.getElementsByTagName("temp").getLength() == 1);
		assertTrue(doc.getElementsByTagName("temp_min").getLength() == 1);
		assertTrue(doc.getElementsByTagName("temp_max").getLength() == 1);
		assertTrue(doc.getElementsByTagName("humidity").getLength() == 1);
		assertTrue(doc.getElementsByTagName("description").getLength() == 1);
	}
}
