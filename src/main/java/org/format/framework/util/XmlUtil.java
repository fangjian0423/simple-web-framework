package org.format.framework.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class XmlUtil {

    public static Element buildDoc(String fileName) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                                XmlUtil.class.getClassLoader().getResourceAsStream(fileName));
            Element doc = document.getDocumentElement();
            return doc;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Element> getChildElements(Element ele) {
        NodeList nl = ele.getChildNodes();
        List<Element> childEles = new ArrayList<Element>();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                childEles.add((Element) node);
            }
        }
        return childEles;
    }

    public static List<Element> getChildElementsByTagName(Element ele, String childEleName) {
        return getChildElementsByTagName(ele, new String[] {childEleName});
    }

    public static List<Element> getChildElementsByTagName(Element ele, String... childEleNames) {
        List<String> childEleNameList = Arrays.asList(childEleNames);
        NodeList nl = ele.getChildNodes();
        List<Element> childEles = new ArrayList<Element>();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element && nodeNameMatch(node, childEleNameList)) {
                childEles.add((Element) node);
            }
        }
        return childEles;
    }

    public static Element getChildElementByTagName(Element ele, String childEleName) {
        NodeList nl = ele.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element && nodeNameMatch(node, childEleName)) {
                return (Element) node;
            }
        }
        return null;
    }

    private static boolean nodeNameMatch(Node node, Collection<?> desiredNames) {
        return (desiredNames.contains(node.getNodeName()) || desiredNames.contains(node.getLocalName()));
    }

    private static boolean nodeNameMatch(Node node, String desiredName) {
        return (desiredName.equals(node.getNodeName()) || desiredName.equals(node.getLocalName()));
    }

}
