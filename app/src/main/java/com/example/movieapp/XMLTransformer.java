package com.example.movieapp;

import android.util.Log;

import androidx.annotation.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

public class XMLTransformer {
    private static XMLTransformer instance = null;
    private static String logTag = "XMLTransformer";

    private XMLTransformer() {}

    public static XMLTransformer getInstance() {
        Log.v(logTag, "getInstance()");
        if (instance == null) {
            instance = new XMLTransformer();
        }
        return instance;
    }

    /**
     * Gets the text content of a specified XML Element
     * @param e element
     * @param tagName tag whose content we want to get
     * @return text content by tag name of the specified element
     */
    @NonNull
    private String getTextContent(@NonNull Element e, String tagName) {
        return e.getElementsByTagName(tagName).item(0).getTextContent().toString();
    }

    /**
     * Gets theatre file from XML reader.
     * Passes the file to the document parser, and receives the parsed document.
     * Send the document and tag name to getNodesByTagName, and converts the received list of Nodes
     * into Theatre objects.
     * @return theatres (objects) in an ArrayList.
     */
    public ArrayList<Theatre> getTheatres() {
        Log.v(logTag, "getTheatres()");
        String tag = "TheatreArea";
        Log.v(logTag, "tag: " + tag);
        File file = XMLReader.getTheatreFile();
        Log.v(logTag, "file: " + file);
        Document document = XMLParser.parseDocument(file.toURI().toString());
        Log.v(logTag, "document: " + document);
        NodeList theatreNodes = XMLParser.getNodesByTagName(document, tag);
        Log.v(logTag, "theatreNodes: " + theatreNodes);
        ArrayList<Theatre> theatres = new ArrayList<Theatre>();

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
    public ArrayList<Show> getShowsAt(int locationID) {
        System.out.println("#### XMLReader.getShowsAt(int id) ###################################");
        String tag = "Show";
        File file = XMLReader.getShowsFile(locationID);
        Document document = XMLParser.parseDocument(file.toURI().toString());
        NodeList showNodes = XMLParser.getNodesByTagName(document, "Show");
        ArrayList<Show> shows = new ArrayList<Show>();

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
