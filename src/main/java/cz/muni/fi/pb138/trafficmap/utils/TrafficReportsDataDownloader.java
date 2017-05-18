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
     * Returns NodeList containing nodes with MJD elements
     * <pre>
     * {@code
     * <MJD count="1">
     *  <MSG id="{ff808181-5bf3-922d-015c-1d4449a214eb}" version="2">
     *      <MTIME format="yyyy-mm-ddThh:mm:ss">
     *          <TGEN date="2017-05-18" time="22:37:10"/>
     *          <TSTA date="2017-05-18" time="22:30:00"/>
     *          <TSTO date="2017-05-19" time="00:35:00"/>
     *          <TUPD date="2017-05-18" time="22:37:10"/>
     *      </MTIME>
     *      <MTXT language="CZ">
     *          Od 18.5.2017 22:30 do 19.5.2017 00:35...
     *      </MTXT>
     *      <MEVT>
     *          <TMCE authorized="true" credibility="3" directionality="false" diversion="0" duration="0" timescale="false" urgency="0">
     *              <EVI eventcode="201" eventorder="1" quantifier="0" updateclass="3"/>
     *              <EVI eventcode="211" eventorder="2" quantifier="0" updateclass="4"/>
     *              <EVI eventcode="922" eventorder="3" quantifier="0" updateclass="13"/>
     *          </TMCE>
     *          <OTXT language="CZ">
     *              nehoda; havarované vozidlo; zvířata na vozovce; motocykl x zvíře, se zraněním.
     *          </OTXT>
     *      </MEVT>
     *      <MLOC PrimaryLocalization="SNTL">
     *          <TXPL>na silnici 173 u obce Radomyšl okres Strakonice</TXPL>
     *          <SNTL coordsystem="WGS84" count="1">
     *              <SBEG x="13.934273" y="49.324917"/>
     *              <SEND x="13.934273" y="49.324917"/>
     *              <STEP begin="0.0" end="0.0"/>
     *              <STEL el_code="3461045" el_dir="+" order="1"/>
     *          </SNTL>
     *      </MLOC>
     *  </MSG>
     * </MJD>
     * }
     * </pre>
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
