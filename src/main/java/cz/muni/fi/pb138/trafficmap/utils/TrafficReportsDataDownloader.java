package cz.muni.fi.pb138.trafficmap.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/**
 * Downloads actual traffic reports from http://aplikace.policie.cz/dopravni-informace/
 */
public class TrafficReportsDataDownloader {

    private final static String TRAFFIC_REPORTS_DATA_URL =
            "http://aplikace.policie.cz/dopravni-informace/GetFile.aspx";
    private final static Logger log = LoggerFactory.getLogger(TrafficReportsDataDownloader.class);

    /**
     * Returns NodeList containing nodes with MJD elements.
     * Example of the MJD element can be found at src/main/resources/mjd_example.xml
     *
     * @return NodeList with MJD elements or null in case an error is thrown
     */
    public static NodeList getTrafficReports() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(TRAFFIC_REPORTS_DATA_URL).getElementsByTagName("MJD");
        } catch (ParserConfigurationException ex) {
            log.error("ParserConfigurationException: " + ex.getMessage());
        } catch (IOException ex) {
            log.error("IOException: " + ex.getMessage());
        } catch (SAXException ex) {
            log.error("SAXException: " + ex.getMessage());
        }

        return null;
    }

}
