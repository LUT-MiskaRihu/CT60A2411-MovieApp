package com.example.movieapp;

import android.util.Log;

import androidx.annotation.NonNull;

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
    private static Document parseOnlineXML(String url) {
        Log.i("XMLReader.parseOnlineXML", "Started parsing online XML document.");
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(url);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("XMLReader.parseOnlineXML", "Finished parsing online XML document");
        return document;
    }

    private static NodeList getNodesByTag(@NonNull Document document, String tag) {
        document.getDocumentElement().normalize();
        return document.getElementsByTagName(tag);
    }

    @NonNull
    private static ArrayList<Element> getElements(NodeList nodeList) {
        ArrayList<Element> alElements = new ArrayList<>();
        for (int i=0; i<nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                alElements.add(element);
            }
        }
        return alElements;
    }

    /**
     * Gets the text content of a specified XML Element
     * @param e element
     * @param tagName tag whose content we want to get
     * @return text content by tag name of the specified element
     */
    @NonNull
    private static String getTextContent(@NonNull Element e, String tagName) {
        return e.getElementsByTagName(tagName).item(0).getTextContent();
    }

    /**
     * Reads theatre information from online XML data,
     * and converts the elements to Theatre objects.
     * @return ArrayList of found theatres (objects)
     */
    @NonNull
    public static ArrayList<Theatre> getOnlineTheatres() {
        Log.v("XMLReader.getOnlineTheatres", "Started fetching information.");
        String url = "https://www.finnkino.fi/xml/TheatreAreas";
        String tag = "TheatreArea";
        Document document = parseOnlineXML(url);
        NodeList nodeList = getNodesByTag(document, tag);
        ArrayList<Element> elements = getElements(nodeList);
        ArrayList<Theatre> theatres = new ArrayList<>();

        for (Element element : elements) {
            String sTheatreName = element.getElementsByTagName("Name").item(0).getTextContent();
            int iTheatreID = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
            if ((sTheatreName.contains(":")) || (iTheatreID == 1029)) {
                theatres.add(new Theatre(iTheatreID, sTheatreName));
            }
        }

        Log.v("XMLReader.getOnlineTheatres", "Found " + theatres.size() + " theatres.");
        return theatres;
    }

    /**
     * Reads show information from online XML data,
     * and converts the elements to Show objects.
     * @return ArrayList of found shows (objects)
     */
    @NonNull
    public static ArrayList<Show> getOnlineShowsAt(int iTheatreID) {
        Log.i("XMLReader.getOnlineShowsAt", "Started fetching information for theatre " + iTheatreID + ".");
        String url = "https://www.finnkino.fi/xml/Schedule/?nrOfDays=31&area=" + iTheatreID;
        String tag = "Show";
        Document document = parseOnlineXML(url);
        NodeList nodeList = getNodesByTag(document, tag);
        ArrayList<Element> elements = getElements(nodeList);
        ArrayList<Show> shows = new ArrayList<>();

        for (Element element : elements) {
            // Movie Attributes
            String sTitle = getTextContent(element, "Title");
            String sOriginalTitle = getTextContent(element, "OriginalTitle");
            String sLength = getTextContent(element, "LengthInMinutes");
            String sProductionYear = getTextContent(element, "ProductionYear");
            String sLocalRelease = getTextContent(element, "dtLocalRelease");
            String sRating = getTextContent(element, "Rating");
            String sGenres = getTextContent(element, "Genres");
            //String sSpokenLanguage = ((Element) element.getChildNodes().item(0)).getTextContent();

            // Show Attributes
            String sShowID = getTextContent(element, "ID");
            String sEventID = getTextContent(element, "EventID");
            String sStartDateTime = getTextContent(element, "dttmShowStart");
            String sEndDateTime = getTextContent(element, "dttmShowEnd");
            String sTheatreID = Integer.toString(iTheatreID);
            String sAuditoriumName = getTextContent(element, "TheatreAuditorium");
            String sPresentationMethod = getTextContent(element, "PresentationMethod");

            Show show = new Show();

            // Set Movie Attributes
            show.setTitle(sTitle);
            show.setOriginalTitle(sOriginalTitle);
            show.setLength(sLength);
            show.setProductionYear(sProductionYear);
            show.setLocalReleaseDateTime(sLocalRelease);
            show.setRating(sRating);
            show.setGenres(sGenres);

            // Set Show Attributes
            show.setShowID(sShowID);
            show.setEventID(sEventID);
            show.setStartDateTime(sStartDateTime);
            show.setEndDateTime(sEndDateTime);
            show.setTheatreID(sTheatreID);
            show.setAuditoriumName(sAuditoriumName);
            show.setPresentationMethod(sPresentationMethod);

            // Add show to ArrayList
            shows.add(show);
        }

        Log.i("XMLReader.getOnlineShowsAt", "Found " + shows.size() + " shows for theatre " + iTheatreID + ".");
        return shows;
    }
}
