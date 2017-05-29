
package ru.niaep.task.xmlparser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class StartParser {
    private static final String RESOURCES_LOG4J_PROPERTIES = "resources/log4j.properties";
    private static final Logger LOG = Logger.getLogger(StartParser.class);
    
    public static void main(String[] args) {
        String log4jConfPath = RESOURCES_LOG4J_PROPERTIES;
        PropertyConfigurator.configure(log4jConfPath);
        
        XMLObjectToParse xml = new XMLObjectToParse("xml/source_file.xml", "xml/target_file.xml");
        try {
            Document doc = xml.readFile();
            Parser.parseActorColor(doc);
            xml.writeFile(doc);
        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException | TransformerException ex) {
            LOG.error(ex.getMessage());
        }
    }
}
