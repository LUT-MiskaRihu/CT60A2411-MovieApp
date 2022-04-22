package com.example.movieapp;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParser {
    /**
     *
     * @param url
     * @return
     */
    public static Document parseDocument(String url) {
        String task = "parse document";
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(url);
        }
        catch (Exception e) {
            Log.e("Exception", "Failed to " + task + ": " + e.toString());
            e.printStackTrace();
        }
        return document;
    }

    /**
     * Takes XML url and tag name as parameters, returns NodeList of found elements.
     * @param url XML url
     * @param tagName XML tag name
     * @return NodeList of found nodes
     */
    public static NodeList getNodesByTagName(String url, String tagName) {
        String task = "get nodes by tag name";
        NodeList nodes = null;
        try {
            Document document = parseDocument(url);
            document.getDocumentElement().normalize();
            nodes = document.getElementsByTagName(tagName);
        }
        catch (Exception e) {
            Log.e("Exception", "Failed to " + task + ": " + e.toString());
            e.printStackTrace();
        }
        return nodes;
    }
}
