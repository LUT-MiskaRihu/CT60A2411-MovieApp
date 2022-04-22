package com.example.movieapp;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParser {
    public static Document parseDocument(String uri) {
        String task = "parse document";
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(uri);
            System.out.println(document);
        }
        catch (Exception e) {
            Log.e("Exception", "Failed to " + task + ": " + e.toString());
            e.printStackTrace();
        }
        return document;
    }

    /**
     *
     * @param document
     * @param tagName
     * @return
     */
    public static NodeList getNodesByTagName(Document document, String tagName) {
        String task = "get nodes by tag name";
        NodeList nodes = null;
        try {
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
