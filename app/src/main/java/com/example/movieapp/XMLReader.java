package com.example.movieapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.renderscript.ScriptGroup;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

public class XMLReader {
    private static XMLReader instance = null;
    private static String logTag = "XMLReader";

    public XMLReader() {
        Log.v(logTag, "Creating new instance");
    }

    public static XMLReader getInstance() {
        Log.v(logTag, "getInstance()");
        if (instance == null) {
            instance = new XMLReader();
        }
        Log.v(logTag, "Instance exists, returning");
        return instance;
    }

    // Copied from: https://stackoverflow.com/a/13873495
    private static String getRawXML(String address) {
        Log.v(logTag, "getRawXML(" + address + ")");
        URL url = null;
        URLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String line = null;
        StringBuilder stringBuilder = null;
        String text = null;

        try {
            url = new URL(address);
            Log.v(logTag, "url = " + url);
            urlConnection = url.openConnection();
            Log.v(logTag, "urlConnection = " + urlConnection);
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            Log.v(logTag, "bufferedReader = ");
            stringBuilder = new StringBuilder();

            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                Log.v(logTag, "line = " + line);
            }
            text = stringBuilder.toString();
            Log.v(logTag, "text = " + text);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static File getTheatreFile() {
        Log.v(logTag, "getTheatreFile()");
        String filename = "theatres.xml";
        long maxDiff = 30 * 86400000L; // max number of days (in milliseconds) between updates
        boolean updateRequired = false;

        File file = new File("theatres.xml");
        Log.v(logTag, "file: " + file);

        if (!file.exists()) {
            Log.v(logTag, "file.exists(): " + file.exists());
            updateRequired = true;
        } else {
            Date currentDate = new Date();
            long currentTime = currentDate.getTime();
            long currentDiff = currentTime - file.lastModified();

            if (currentDiff > maxDiff) {
                updateRequired = true;
            }
        }

        if (updateRequired) {
            Log.v(logTag, "Fetching theatres data...");
            file = XMLWriter.writeRawText(getRawXML("https://www.finnkino.fi/xml/TheatreAreas"), "theatres.xml");
            Log.v(logTag, "Done, file: " + file);
        }

        return file;
    }

    @SuppressLint("DefaultLocale")
    public static File getShowsFile(int locationID) {
        Log.v(logTag, "getShowFile(" + locationID + ")");
        String filename, url;
        filename = String.format("theatre-%d.xml", locationID);
        url = String.format("https://www.finnkino.fi/xml/Schedule/?nrOfDays=31&locationID=%d", locationID);
        return getFile(url, filename);
    }

    private static File getFile(String url, String filename) {
        Log.v(logTag, "getFile(" + url + ", " + filename + ")");
        long maxDiff = 30 * 86400000L; // max number of days (in milliseconds) between updates
        boolean updateRequired = false;

        File file = new File(ApplicationContext.getAppContext().getFilesDir(), filename);

        if (!file.exists()) {
            updateRequired = true;
        } else {
            Date currentDate = new Date();
            long currentTime = currentDate.getTime();
            long currentDiff = currentTime - file.lastModified();

            if (currentDiff > maxDiff) {
                updateRequired = true;
            }
        }

        if (updateRequired) {
            file = XMLWriter.writeRawText(getRawXML(url), filename);
        }

        return file;
    }
}
