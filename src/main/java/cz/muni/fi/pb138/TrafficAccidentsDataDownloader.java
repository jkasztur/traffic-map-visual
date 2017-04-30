package cz.muni.fi.pb138;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/**
 * Created by robert-ntb on 4/30/17.
 */
public class TrafficAccidentsDataDownloader {

    private final static String TRAFFIC_ACCIDENTS_DATA_URL =
            "http://aplikace.policie.cz/dopravni-informace/GetFile.aspx";
    private final static Logger log = LoggerFactory.getLogger(TrafficAccidentsDataDownloader.class);

    public static Document downloadData() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(TRAFFIC_ACCIDENTS_DATA_URL);
        } catch (ParserConfigurationException ex) {
            log.error("ParserConfigurationException: " + ex.getMessage());
        } catch (IOException ex) {
            log.error("IOException: " + ex.getMessage());
        } catch (SAXException ex) {
            log.error("SAXException: " + ex.getMessage());
        }

        return null;
    }

//    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
//        TransformerFactory tf = TransformerFactory.newInstance();
//        Transformer transformer = tf.newTransformer();
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//
//        transformer.transform(new DOMSource(doc),
//                new StreamResult(new OutputStreamWriter(out, "UTF-8")));
//    }

}
