package com.example.movieapp;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.OutputStreamWriter;

public class XMLWriter {
    private static XMLWriter instance = null;

    public XMLWriter() {}

    public XMLWriter getInstance() {
        if (instance == null) {
            instance = new XMLWriter();
        }
        return instance;
    }

    public static File writeRawText(String data, String filename) {
        String task = "write to a file";
        Context context = ApplicationContext.getAppContext();
        try {
            OutputStreamWriter outputStreamWriter;
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (Exception e) {
            Log.e("Exception", "Failed to " + task + ": " + e.toString());
            e.printStackTrace();
        }
        return new File(context.getFilesDir(), filename);
    }
}
