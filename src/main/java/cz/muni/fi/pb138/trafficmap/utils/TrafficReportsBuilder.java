package cz.muni.fi.pb138.trafficmap.utils;


import com.google.maps.errors.ApiException;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import cz.muni.fi.pb138.trafficmap.models.GpsCoords;
import cz.muni.fi.pb138.trafficmap.models.TrafficReport;
import net.aksingh.owmjapis.CurrentWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Matej on 4.5.2017.
 */
public class TrafficReportsBuilder {
    private final static Logger log = LoggerFactory.getLogger(TrafficReportsBuilder.class);

    public static List<TrafficReport> getReports() {
        List<TrafficReport> reports = new ArrayList<>();
        try {
            NodeList reportElements = TrafficReportsDataDownloader.getTrafficReports();
            XPath xPath = XPathFactory.newInstance().newXPath();

            for (int i = 0; i < reportElements.getLength(); i++) {
                Node reportNode = reportElements.item(i);
                if (reportNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element reportElement = (Element) reportNode;
                    TrafficReport report = new TrafficReport();

                    GpsCoords start = getStartCoord(reportElement, xPath);
                    GpsCoords end = getEndCoord(reportElement, xPath);
                    CurrentWeather localWeather = WeatherUtils.getWeatherAtLocationObject((float) start.getLng(), (float) start.getLat());
                    String message = getMessage(reportElement, xPath);
                    ZonedDateTime from = getActiveFrom(reportElement, xPath);
                    ZonedDateTime to = getActiveTo(reportElement, xPath);
                    String district = GeocodingUtils.getDistrictFromCoords(start.getLat(), start.getLng());
                    String region = GeocodingUtils.getRegionFromCoords(start.getLat(), start.getLng());
                    report.setDistrict(district);
                    report.setRegion(region);
                    report.setActiveFrom(from);
                    report.setActiveTo(to);
                    report.setMessage(message);
                    report.setPrimaryLocalization(getPrimaryLocalization(reportElement, xPath));
                    report.setInfoText(getInfoText(reportElement, xPath));
                    report.setStart(start);
                    report.setEnd(end);
                    report.setLocalWeather(localWeather);

                    reports.add(report);
                }
            }
        } catch (XPathExpressionException ex) {
            log.error("XPathExpressionException: " + ex.getMessage());
        } catch (InterruptedException ex) {
            log.error("InterruptedException: " + ex.getMessage());
        } catch (IOException ex) {
            log.error("IOException: " + ex.getMessage());
        } catch (ApiException ex) {
            log.error("ApiException: " + ex.getMessage());
        }
        return reports;
    }

    public static String getJSONReports() {
        String result = null;
        List<TrafficReport> reports = getReports();
        ObjectMapper mapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        mapper.registerModule(javaTimeModule);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleModule sm = new SimpleModule("MyModule", new Version(1, 0, 0, null, null, null));
        sm.addSerializer(CurrentWeather.class, new WeatherSerializer());
        mapper.registerModule(sm);

        StringWriter sw = new StringWriter();
        try {
            mapper.writeValue(sw, reports);
            result = sw.toString();
        } catch (IOException ex) {
            log.error("IOException:" + ex.getMessage());
        }

        return result;
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

    private static String getPrimaryLocalization(Element elem, XPath xPath) throws XPathExpressionException {
        return (String) xPath.evaluate(".//TXPL/text()", elem, XPathConstants.STRING);
    }

    private static String getInfoText(Element elem, XPath xPath) throws XPathExpressionException {
        return (String) xPath.evaluate(".//OTXT/text()", elem, XPathConstants.STRING);
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
}
