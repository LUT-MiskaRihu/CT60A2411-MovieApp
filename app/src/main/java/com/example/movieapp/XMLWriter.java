package com.example.movieapp;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.FileWriter;
import java.io.OutputStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLWriter {
    private static XMLWriter instance = null;

    public XMLWriter() {}

    public XMLWriter getInstance() {
        if (instance == null) {
            instance = new XMLWriter();
        }
        return instance;
    }

    public void saveRawXML(Document document, String filename) {
        String task = "Failed to save raw XML: ";
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            FileWriter fileWriter = new FileWriter(filename);
            transformer.transform(new DOMSource(document), new StreamResult(fileWriter));
        }
        catch (Exception e) {
            Log.e("Exception", "Failed to " + task + ": " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeToFile(String data, Context context) {
        String task = "write to a file";
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (Exception e) {
            Log.e("Exception", "Failed to " + task + ": " + e.toString());
            e.printStackTrace();
        }
    }
}
