/*
 *    Copyright (c) 2013, University of Toronto.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License"); you may
 *    not use this file except in compliance with the License. You may obtain
 *    a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 */
package edu.toronto.cs.xcurator.xml;

import edu.toronto.cs.xcurator.common.XMLUtils;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ekzhu
 */
public class XMLUtilsTest {

    @Test
    public void getUriTest() throws SAXException, IOException, ParserConfigurationException {
        String defaultUriBase = "http://example.org";
        Document dataDoc;
        dataDoc = edu.toronto.cs.xml2rdf.xml.XMLUtils.parse(
                XMLUtilsTest.class.getResourceAsStream(
                        "/secxbrls/data/fb-20121231.xml"), -1);
        Element root = dataDoc.getDocumentElement();
        assertTrue("Incorrect base URI",
                XMLUtils.getSchemaUri(root, defaultUriBase).equals(
                        root.getNamespaceURI() + "#" + root.getLocalName()));
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                Element child = (Element) n;
                assertTrue("Incorrect base URI",
                        XMLUtils.getSchemaUri(child, defaultUriBase).startsWith(child.getNamespaceURI()));
            }
        }
    }

    @Test
    public void getUriTestUseDefaultUriBase() throws SAXException, IOException, ParserConfigurationException {
        String defaultUriBase = "http://example.org";
        Document dataDoc;
        dataDoc = edu.toronto.cs.xml2rdf.xml.XMLUtils.parse(
                XMLUtilsTest.class.getResourceAsStream(
                        "/samplexmls/plant_catalog.xml"), -1);
        Element root = dataDoc.getDocumentElement();
        assertTrue("Incorrect base URI",
                XMLUtils.getSchemaUri(root, defaultUriBase).equals(
                        defaultUriBase + "#" + root.getLocalName()));
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                Element child = (Element) n;
                assertTrue("Incorrect base URI",
                        XMLUtils.getSchemaUri(child, defaultUriBase).startsWith(defaultUriBase));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUriTestUseDefaultUriBaseIsNull() throws SAXException, IOException, ParserConfigurationException {
        String defaultUriBase = null;
        Document dataDoc = edu.toronto.cs.xml2rdf.xml.XMLUtils.parse(
                XMLUtilsTest.class.getResourceAsStream(
                        "/samplexmls/plant_catalog.xml"), -1);
        Element root = dataDoc.getDocumentElement();
        System.out.println(XMLUtils.getSchemaUri(root, defaultUriBase));
    }

    @Test
    public void isLeafTest() throws SAXException, IOException, ParserConfigurationException {
        Document dataDoc = edu.toronto.cs.xml2rdf.xml.XMLUtils.parse(
                XMLUtilsTest.class.getResourceAsStream(
                        "/samplexmls/leaf.xml"), -1);
        Element root = dataDoc.getDocumentElement();
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            assertTrue(XMLUtils.isLeaf(n));
        }
    }
}
