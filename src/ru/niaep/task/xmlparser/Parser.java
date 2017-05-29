
package ru.niaep.task.xmlparser;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Parser {
    private static final Logger LOG = Logger.getLogger(StartParser.class);
    private static final String NEUTRAL_ACTOR_LOCATION_PIVOT_ORIGIN = "//Neutral/Actor.Location/Pivot/Origin[@X and @Y and @Z]/parent::node()/parent::node()/parent::node()";
    private static final String ACTOR_COLOR = "Actor.Color";
    
    public static void parseActorColor (Document doc) throws XPathExpressionException {
        String log4jConfPath = "resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression exprColorPath = xpath.compile(NEUTRAL_ACTOR_LOCATION_PIVOT_ORIGIN);

        NodeList nodes = (NodeList) exprColorPath.evaluate(doc, XPathConstants.NODESET);

        for (int i=0; i<nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            NodeList childNodes = element.getChildNodes();
            Boolean isExistColorTag = false;

            // проверяем есть на данном уровне узел Actor.Color
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node childNode = (Node) childNodes.item(j);

                // если есть, выставляем флаг
                if (childNode.getNodeName().equalsIgnoreCase(ACTOR_COLOR)) {
                    isExistColorTag = true;
                        break;
                }
            }

            if (!isExistColorTag){

                System.out.println("Добавляем тэг " +ACTOR_COLOR);

                Element newElement = doc.createElement(ACTOR_COLOR);
                newElement.setAttribute("R", "255");
                newElement.setAttribute("G", "0");
                newElement.setAttribute("B", "0");
                element.appendChild(newElement);
                
                LOG.info("Добавлен тэг " +ACTOR_COLOR);
            }
            else {
                System.out.println("Обновляем тэг " +ACTOR_COLOR);

                Element actorColor = (Element) xpath.evaluate(ACTOR_COLOR, element, XPathConstants.NODE);
                actorColor.setAttribute("R", "255");
                actorColor.setAttribute("G", "0");
                actorColor.setAttribute("B", "0");
                
                LOG.info("Обновлен тэг " +ACTOR_COLOR);
            }
        }
    }
}
