package com.example.test;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLReader {
    private final String theatresURL = "https://www.finnkino.fi/xml/TheatreAreas";
    private final String scheduleURL = "https://www.finnkino.fi/xml/Schedule";
    private final String theatreTag = "TheatreArea";
    private final String showTag = "Show";
    private XMLReader instance = null;

    public XMLReader() {    }

    public XMLReader getInstance() {
        if (instance == null) {
            instance = new XMLReader();
        }
        return instance;
    }

    /**
     * Takes XML url and tag name as parameters, returns nodelist of found elements.
     * @param url XML url
     * @param tagName XML tag name
     * @return NodeList of found nodes
     */
    private NodeList getNodesByTagName(String url, String tagName) {
        NodeList nodes = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(url);
            document.getDocumentElement().normalize();
            nodes = document.getElementsByTagName(tagName);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            System.err.println("ParserConfigurationException");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException");
        } catch (SAXException e) {
            e.printStackTrace();
            System.err.println("SAXException");
        }
        return nodes;
    }

    public List<Theatre> getTheatres() {
        List<Theatre> theatres = new List<Theatre>();
        NodeList theatreNodes = getNodesByTagName(theatresURL, theatreTag);
        for (int i=0; i<theatreNodes.getLength(); i++) {

        }
        return theatres;
    }
}
