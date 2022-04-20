package com.example.test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLReader {
    private static XMLReader instance = null;

    public XMLReader() { }

    public static XMLReader getInstance() {
        if (instance == null) {
            instance = new XMLReader();
        }
        return instance;
    }

    /**
     * Takes XML url and tag name as parameters, returns NodeList of found elements.
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

    /**
     * Gets the text content of a specified XML Element
     * @param e element
     * @param tagName tag whose content we want to get
     * @return text content by tag name of the specified element
     */
    private String getTextContent(Element e, String tagName) {
        return e.getElementsByTagName(tagName).item(0).getTextContent().toString();
    }

    /**
     * Reads theatre information from online XML data,
     * and converts the elements to Theatre objects.
     * @return ArrayList of found theatres (objects)
     */
    public ArrayList<Theatre> getTheatres() {
        String theatresURL = "https://www.finnkino.fi/xml/TheatreAreas";
        String theatreTag = "TheatreArea";
        ArrayList<Theatre> theatres = new ArrayList<Theatre>();
        NodeList theatreNodes = getNodesByTagName(theatresURL, theatreTag);

        for (int i=0; i<theatreNodes.getLength(); i++) {
            Node theatreNode = theatreNodes.item(i);
            if (theatreNode.getNodeType() == Node.ELEMENT_NODE) {
                Element theatreElement = (Element) theatreNode;
                Theatre theatre = new Theatre();
                theatre.setID(Integer.parseInt(theatreElement.getElementsByTagName("ID").item(0).getTextContent()));
                theatre.setName(theatreElement.getElementsByTagName("Name").item(0).getTextContent());
                theatres.add(theatre);
            }
        }
        return theatres;
    }

    /**
     * Reads show information from online XML data,
     * and converts the elements to Show objects.
     * @return ArrayList of found shows (objects)
     */
    public ArrayList<Show> getShows() {
        String scheduleURL = "https://www.finnkino.fi/xml/Schedule";
        String showTag = "Show";
        ArrayList<Show> shows = new ArrayList<Show>();
        NodeList showNodes = getNodesByTagName(scheduleURL, showTag);

        for (int i=0; i<showNodes.getLength(); i++) {
            Node showNode = showNodes.item(i);
            if (showNode.getNodeType() == Element.ELEMENT_NODE) {
                Element showElement = (Element) showNode;
                Show show = new Show();
                show.setID(getTextContent(showElement, "ID"));
                show.setDateTime(Show.START_DATE_TIME, getTextContent(showElement, "dttmShowStart"));
                show.setDateTime(Show.END_DATE_TIME, getTextContent(showElement, "dttmShowEnd"));
                show.setEventID(getTextContent(showElement, "EventID"));
                show.setTitle(getTextContent(showElement, "Title"));
                show.setOriginalTitle(getTextContent(showElement, "OriginalTitle"));
                show.setProductionYear(getTextContent(showElement, "ProductionYear"));
                show.setLength(getTextContent(showElement, "LengthInMinutes"));
                show.setDateTime(Show.LOCAL_RELEASE, getTextContent(showElement, "dtLocalRelease"));
                show.setRating(getTextContent(showElement, "Rating"));
                show.setEventType(getTextContent(showElement, "EventType"));
                show.setGenres(getTextContent(showElement, "Genres"));
                show.setLocationID(getTextContent(showElement, "TheatreID"));
                show.setLocationName(getTextContent(showElement, "TheatreAndAuditorium"));
                show.setPresentationMethodAndLanguage(getTextContent(showElement, "PresentationMethodAndLanguage"));
                shows.add(show);
            }
        }
        return shows;
    }
}
