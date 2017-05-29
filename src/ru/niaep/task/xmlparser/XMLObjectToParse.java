
package ru.niaep.task.xmlparser;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLObjectToParse {
    private static final Logger LOG = Logger.getLogger(StartParser.class);
    private static final String RESOURCES_LOG4J_PROPERTIES = "resources/log4j.properties";
    private String sourcePath;
    private String targetPath;

    public XMLObjectToParse(String sourcePath, String destionationPath) {
        this.sourcePath = sourcePath;
        this.targetPath = destionationPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestionationPath() {
        return targetPath;
    }

    public void setDestionationPath(String destionationPath) {
        this.targetPath = destionationPath;
    }
    
    protected Document readFile() throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(new File(getSourcePath()));
        return doc;
    }
    
    protected void writeFile(Document doc) throws TransformerConfigurationException, TransformerException {
        String log4jConfPath = RESOURCES_LOG4J_PROPERTIES;
        PropertyConfigurator.configure(log4jConfPath);
        
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(getDestionationPath()));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        LOG.info("XML успешно изменен!");
    }
}
