package com.example.movieapp;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

public class XMLReader {
    private static XMLReader instance = null;
    private int nrOfDays = 31;

    public XMLReader() {}

    public static XMLReader getInstance() {
        if (instance == null) {
            instance = new XMLReader();
        }
        return instance;
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
        NodeList theatreNodes = XMLParser.getNodesByTagName(theatresURL, theatreTag);

        for (int i=0; i<theatreNodes.getLength(); i++) {
            Node theatreNode = theatreNodes.item(i);
            if (theatreNode.getNodeType() == Node.ELEMENT_NODE) {
                Element theatreElement = (Element) theatreNode;
                Theatre theatre = new Theatre();
                theatre.setID(Integer.parseInt(getTextContent(theatreElement, "ID")));
                theatre.setName(getTextContent(theatreElement, "Name"));
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
    public ArrayList<Show> getShowsAt(int area) {
        System.out.println("#### XMLReader.getShowsAt(int id) ###################################");
        final String DELIMIT = "&";
        StringBuilder url = new StringBuilder("https://www.finnkino.fi/xml/Schedule/?");
        url.append("nrOfDays=" + nrOfDays); // set maximum number of days to display
        url.append(DELIMIT + "area=" + area);

        ArrayList<Show> shows = new ArrayList<Show>();
        NodeList showNodes = XMLParser.getNodesByTagName(url.toString(), "Show");

        for (int i=0; i<showNodes.getLength(); i++) {
            Node showNode = showNodes.item(i);
            if (showNode.getNodeType() == Element.ELEMENT_NODE) {
                Element showElement = (Element) showNode;
                Show show = new Show();
                show.setID(
                        getTextContent(showElement, "ID"));
                show.setStartDateTime(
                        Parser.parseDateTime(getTextContent(showElement, "dttmShowStart")));
                show.setEndDateTime(
                        Parser.parseDateTime(getTextContent(showElement, "dttmShowEnd")));
                show.setEventID(
                        getTextContent(showElement, "EventID"));
                show.setTitle(
                        getTextContent(showElement, "Title"));
                show.setOriginalTitle(
                        getTextContent(showElement, "OriginalTitle"));
                show.setProductionYear(
                        getTextContent(showElement, "ProductionYear"));
                show.setLength(
                        getTextContent(showElement, "LengthInMinutes"));
                show.setLocalReleaseDateTime(
                        Parser.parseDateTime(getTextContent(showElement, "dtLocalRelease")));
                show.setRating(
                        getTextContent(showElement, "Rating"));
                show.setEventType(
                        getTextContent(showElement, "EventType"));
                show.setGenres(
                        getTextContent(showElement, "Genres"));
                show.setLocationID(
                        getTextContent(showElement, "TheatreID"));
                show.setLocationName(
                        getTextContent(showElement, "TheatreAndAuditorium"));
                show.setPresentationMethodAndLanguage(
                        getTextContent(showElement, "PresentationMethodAndLanguage"));
                shows.add(show);
            }
        }
        System.out.println("#####################################################################");
        return shows;
    }
}
