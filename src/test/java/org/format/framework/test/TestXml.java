package org.format.framework.test;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;

public class TestXml {

    @Test
    public void testBinders() throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                TestXml.class.getClassLoader().getResourceAsStream("propertyEditor/customBinders.xml"));
        Element doc = document.getDocumentElement();
        NodeList list = doc.getElementsByTagName("binder");
        for(int i = 0; i < list.getLength(); i ++) {
            Node node = list.item(i);
            NodeList children = node.getChildNodes();
            for(int j = 0; j < children.getLength(); j ++) {
                Node childNode = children.item(j);
                if(children.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println(childNode.getNodeName() + ", " + childNode.getTextContent());
                }
            }
        }
    }

}
