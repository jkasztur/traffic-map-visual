package cz.muni.fi.pb138;

import cz.muni.fi.pb138.trafficmap.utils.WeatherUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Matej on 4.5.2017.
 */
public class TrafficReportsBuilder {
    private final static Logger log = LoggerFactory.getLogger(TrafficReportsBuilder.class);

    private static List<TrafficReport> getReports() {
        List<TrafficReport> reports = new ArrayList<>();
        try {
            NodeList reportElements = TrafficReportsDataDownloader.downloadData();
            XPath xPath = XPathFactory.newInstance().newXPath();
            for (int i = 0; i < reportElements.getLength(); i++) {
                Node reportNode = reportElements.item(i);
                if (reportNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element reportElement = (Element) reportNode;
                    TrafficReport report = new TrafficReport();
                    GpsCoords start = getStartCoord(reportElement, xPath);
                    GpsCoords end = getEndCoord(reportElement, xPath);
                    Weather localWeather = getWeatherFromCoords(start, xPath);
                    String message = getMessage(reportElement, xPath);
                    ZonedDateTime from = getActiveFrom(reportElement, xPath);
                    ZonedDateTime to = getActiveTo(reportElement, xPath);
                    report.setActiveFrom(from);
                    report.setActiveTo(to);
                    report.setMessage(message);
                    report.setStart(start);
                    report.setEnd(end);
                    report.setLocalWeather(localWeather);
                    reports.add(report);
                }
            }
        } catch (XPathExpressionException ex) {
            log.error("XPathExpressionException: " + ex.getMessage());
        } catch (ParserConfigurationException ex) {
            log.error("ParserConfigurationException: " + ex.getMessage());
        }
        return reports;
    }

    private static GpsCoords getStartCoord(Element elem, XPath xPath) throws XPathExpressionException {
        String xCoordXpath = ".//SBEG/@x";
        String yCoordXpath = ".//SBEG/@y";
        double x = (Double) xPath.evaluate(xCoordXpath, elem, XPathConstants.NUMBER);
        double y = (Double) xPath.evaluate(yCoordXpath, elem, XPathConstants.NUMBER);
        return new GpsCoords(x, y);
    }

    private static GpsCoords getEndCoord(Element elem, XPath xPath) throws XPathExpressionException {
        String xCoordXpath = ".//SEND/@x";
        String yCoordXpath = ".//SEND/@y";
        double x = (Double) xPath.evaluate(xCoordXpath, elem, XPathConstants.NUMBER);
        double y = (Double) xPath.evaluate(yCoordXpath, elem, XPathConstants.NUMBER);
        return new GpsCoords(x, y);
    }

    private static String getMessage(Element elem, XPath xPath) throws XPathExpressionException {
        String messageXpath = ".//MTXT/text()";
        String value = (String) xPath.evaluate(messageXpath, elem, XPathConstants.STRING);
        return value;
    }

    private static ZonedDateTime getActiveFrom(Element elem, XPath xPath) throws XPathExpressionException {
        String timeXpath = ".//TSTA/@time";
        String dateXpath = ".//TSTA/@date";
        String timeEval = (String) xPath.evaluate(timeXpath, elem, XPathConstants.STRING);
        String dateEval = (String) xPath.evaluate(dateXpath, elem, XPathConstants.STRING);
        String[] partedDate = dateEval.split("-");
        String[] partedTime = timeEval.split(":");
        ZonedDateTime value = ZonedDateTime.of(Integer.parseInt(partedDate[0]), Integer.parseInt(partedDate[1]),
                Integer.parseInt(partedDate[2]), Integer.parseInt(partedTime[0]), Integer.parseInt(partedTime[1]),
                Integer.parseInt(partedTime[2]), 0, ZoneId.of(TimeZone.getDefault().getID()));
        return value;
    }

    private static ZonedDateTime getActiveTo(Element elem, XPath xPath) throws XPathExpressionException {
        String timeXpath = ".//TSTO/@time";
        String dateXpath = ".//TSTO/@date";
        String timeEval = (String) xPath.evaluate(timeXpath, elem, XPathConstants.STRING);
        String dateEval = (String) xPath.evaluate(dateXpath, elem, XPathConstants.STRING);
        String[] partedDate = dateEval.split("-");
        String[] partedTime = timeEval.split(":");
        ZonedDateTime value = ZonedDateTime.of(Integer.parseInt(partedDate[0]), Integer.parseInt(partedDate[1]),
                Integer.parseInt(partedDate[2]), Integer.parseInt(partedTime[0]), Integer.parseInt(partedTime[1]),
                Integer.parseInt(partedTime[2]), 0, ZoneId.of(TimeZone.getDefault().getID()));
        return value;
    }

    private static Weather getWeatherFromCoords(GpsCoords coords, XPath xPath) throws ParserConfigurationException, XPathExpressionException {
        Weather localWeather = new Weather();
        Document weatherDoc = WeatherUtils.getWeatherAtLocation((float) coords.getLongitude(), (float) coords.getLatitude());
        String locXpath = "//name/text()";
        String tempXpath = "//temp/text()";
        String humidityXpath = "//humidity/text()";
        String descriptionXpath = "//description/text()";
        String windXpath = "//wind/speed/text()";
        String iconXpath = "//icon/text()";
        String mainDescXpath = "//weather/main/text()";
        String pressureXpath = "//pressure/text()";
        double tempEval = (double) xPath.evaluate(tempXpath, weatherDoc, XPathConstants.NUMBER);
        int humidityEval = ((Double) xPath.evaluate(humidityXpath, weatherDoc, XPathConstants.NUMBER)).intValue();
        int pressureEval = ((Double) xPath.evaluate(pressureXpath, weatherDoc, XPathConstants.NUMBER)).intValue();
        double windSpeedEval = (double) xPath.evaluate(windXpath, weatherDoc, XPathConstants.NUMBER);
        String descEval = (String) xPath.evaluate(descriptionXpath, weatherDoc, XPathConstants.STRING);
        String iconEval = (String) xPath.evaluate(iconXpath, weatherDoc, XPathConstants.STRING);
        String locationEval = (String) xPath.evaluate(locXpath, weatherDoc, XPathConstants.STRING);
        String mainDescEval = (String) xPath.evaluate(mainDescXpath, weatherDoc, XPathConstants.STRING);
        localWeather.setDescription(descEval);
        localWeather.setHumidity(humidityEval);
        localWeather.setIcon(iconEval);
        localWeather.setMainDescription(mainDescEval);
        localWeather.setPressure(pressureEval);
        localWeather.setTemperature(tempEval);
        localWeather.setWindSpeed(windSpeedEval);
        localWeather.setLocName(locationEval);
        return localWeather;
    }

    /*public static void main(String[] args) {
        List<TrafficReport> list = getReports();
        for (TrafficReport item: list
             ) {
            System.out.println(item);
        }
        System.out.println(list.size());
    }*/
}
