package cz.muni.fi.pb138.trafficmap.utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class StatisticsUtil {

	private static Document doc;

	/**
	 * Sets the statistic data file to be used in evaluateExpression method
	 */
	static {
		File raw = new File(StatisticsUtil.class.getClassLoader().getResource("raw_statistic_data.xml").getFile());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(raw);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static String getRegionName(String regionId) {
		return evaluateExpression("//uzemi/element[@ID = '" + regionId + "' ]/text");
	}

	public static int getTotalAccidents(String regionId) {
		return Integer.valueOf(evaluateExpression("//udaj[uze = '" + regionId + "'][1]/hod"));
	}

	public static int getDrunkDriving(String regionId) {
		return Integer.valueOf(evaluateExpression("//udaj[uze = '" + regionId + "'][2]/hod"));
	}

	public static int getKilledPersons(String regionId) {
		return Integer.valueOf(evaluateExpression("//udaj[uze = '" + regionId + "'][3]/hod"));
	}

	public static int getSeriouslyInjured(String regionId) {
		return Integer.valueOf(evaluateExpression("//udaj[uze = '" + regionId + "'][4]/hod"));
	}

	public static int getSlightlyInjured(String regionId) {
		return Integer.valueOf(evaluateExpression("//udaj[uze = '" + regionId + "'][5]/hod"));
	}

	public static double getPropertyDamage(String regionId) {
		return Double.valueOf(evaluateExpression("//udaj[uze = '" + regionId + "'][6]/hod"));
	}

	/**
	 * Evaluates the given XPath expression and returns the response
	 * @param exp  XPath expression
	 * @return response of XPath expression
	 */
	private static String evaluateExpression(String exp) {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		String response;
		try {
			XPathExpression expr = xpath.compile(exp);
			response = expr.evaluate(doc);
		} catch (XPathExpressionException e) {
			log.error(e.getLocalizedMessage());
			response = "-1";
		}

		return response;
	}
}
