package com.example.movieapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {
    private final String sClassTag = "SearchActivity";
    private Spinner spnTheatres;
    private EditText etTitle;
    private EditText etDate;
    private EditText etTimeMin;
    private EditText etTimeMax;
    private Button btnClearTitle;
    private Button btnClearDate;
    private Button btnClearTimeMin;
    private Button btnClearTimeMax;

    private Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Assign variables to UI elements
        spnTheatres = findViewById(R.id.spnTheaters);
        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etTimeMin = findViewById(R.id.etTimeMin);
        etTimeMax = findViewById(R.id.etTimeMax);
        btnClearTitle = findViewById(R.id.btnClearTitle);
        btnClearDate = findViewById(R.id.btnClearDate);
        btnClearTimeMin = findViewById(R.id.btnClearTimeMin);
        btnClearTimeMax = findViewById(R.id.btnClearTimeMax);

        // Initiate spinner
        ArrayAdapter<Theatre> theatreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Database.getTheatres());
        spnTheatres.setAdapter(theatreAdapter);

        // Initiate filter
        filter = Filter.getInstance();

        // Set onClick actions
        View.OnClickListener onClickListener = view -> {
            // Pick date
            if (view == etDate) {
                pickDate();
            }

            // Pick time min
            else if (view == etTimeMin) {
                pickTime(etTimeMin);
            }

            // Pick time max
            else if (view == etTimeMax) {
                pickTime(etTimeMax);
            }

            // Clear title
            else if (view == btnClearTitle) {
                etTitle.setText(null);
            }

            // Clear date
            else if (view == btnClearDate) {
                etDate.setText(null);
            }

            // Clear time min
            else if (view == btnClearTimeMin) {
                etTimeMin.setText(null);
            }

            // Clear time max
            else if (view == btnClearTimeMax) {
                etTimeMax.setText(null);
            }
        };

        etDate.setOnClickListener(onClickListener);
        etTimeMin.setOnClickListener(onClickListener);
        etTimeMax.setOnClickListener(onClickListener);
        btnClearTitle.setOnClickListener(onClickListener);
        btnClearDate.setOnClickListener(onClickListener);
        btnClearTimeMin.setOnClickListener(onClickListener);
        btnClearTimeMax.setOnClickListener(onClickListener);
    }

    // Makes it impossible to go back to the splash screen
    @Override
    public void onBackPressed() { }

    /**
     * This function shows the DatePickerDialog when the user clicks on the date field,
     * and sets the date params in filter object (year, month, and day of month)
     */
    @SuppressLint("DefaultLocale")
    public void pickDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener;
        onDateSetListener = (datePicker, iYear, iMonth, iDay) -> etDate.setText(String.format("%02d.%02d.%04d", iDay, iMonth + 1, iYear));

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(
                SearchActivity.this,
                onDateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    /**
     * This function shows TimePickerDialog when the user clicks on either the minimum or maximum
     * time fields, and sets the filters attributes accordingly.
     * @param etTimeField text field to edit
     */
    @SuppressLint("DefaultLocale")
    public void pickTime(EditText etTimeField) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener;
        onTimeSetListener = (timePicker, iHour, iMinute) -> etTimeField.setText(String.format("%2d:%02d", iHour, iMinute));

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                SearchActivity.this,
                onTimeSetListener,
                12,
                0,
                true
        );

        timePickerDialog.show();
    }

    public void search(View view) {
        Date dateCurrentDateTime = new Date();
        setFilterTheatre();
        setFilterTitle();
        boolean bDateOK = setFilterDate(dateCurrentDateTime);
        boolean bTimeMinOK = setFilterMinTime(dateCurrentDateTime);
        boolean bTimeMaxOK = setFilterMaxTime(dateCurrentDateTime);
        boolean bNoErrors = bDateOK & bTimeMinOK & bTimeMaxOK;

        if (!bDateOK) {
            Toast.makeText(SearchActivity.this, ErrorMessages.ERR_INVALID_DATE_ENTERED, Toast.LENGTH_LONG).show();
        }

        if (!bTimeMinOK) {
            Toast.makeText(SearchActivity.this, ErrorMessages.ERR_INVALID_MIN_START_TIME_ENTERED, Toast.LENGTH_LONG).show();
        }

        if (!bTimeMaxOK) {
            Toast.makeText(SearchActivity.this, ErrorMessages.ERR_INVALID_MAX_START_TIME_ENTERED, Toast.LENGTH_LONG).show();
        }

        if (bNoErrors) {
            loadDebugActivity(view);
        }
    }

    public void loadDebugActivity(View view) {
        Intent intent = new Intent(SearchActivity.this, DebugActivity.class);
        startActivity(intent);
    }

    private void setFilterTheatre() {
        final String sMethodTag = "setFilterTheatre";
        String sTheatre = spnTheatres.getSelectedItem().toString();

        Log.i(sClassTag + "." + sMethodTag, "Setting filter's theatre to '" + sTheatre + "'.");
        filter.setTheatre(sTheatre);
    }

    private void setFilterTitle() {
        final String sMethodTag = "setFilterTitle";
        String sTitle = etTitle.getText().toString();

        if (sTitle.equals("")) {
            Log.w(sClassTag + "." + sMethodTag, "Input string sTitle is empty, using default value.");
            filter.clearTitle();
        } else {
            Log.i(sClassTag + "." + sMethodTag, "Setting filter's title to '" + sTitle + "'.");
            filter.setTitle(sTitle);
        }
    }

    private boolean setFilterDate(Date dateCurrentDateTime) {
        final String sMethodTag = "setFilterDate";

        String sDate = etDate.getText().toString();
        boolean success = true;

        if (sDate.equals("")) {
            Log.w(sClassTag + "." + sMethodTag, "Input string sDate is empty, using default value.");
            filter.clearMinStartDate();
        } else {
            long lCurrentDate = CalendarConverter.extractDateAsLong(dateCurrentDateTime);
            long lDate = CalendarConverter.extractDateAsLong(Parser.parseDate(sDate));

            if (lDate < lCurrentDate) {
                success = false;
            }

            Log.i(sClassTag + "." + sMethodTag, "Setting filter's date to '" + sDate + "'.");
            filter.setMinStartDate(sDate);
        }

        return success;
    }

    private boolean setFilterMinTime(Date dateCurrentDateTime) {
        final String sMethodTag = "setFilterMinTime";

        String sTimeMin = etTimeMin.getText().toString();
        boolean success = true;

        if (sTimeMin.equals("")) {
            Log.w(sClassTag + "." + sMethodTag, "Input string sTimeMin is empty, using default value.");
            filter.clearMinStartTime();
        } else {
            String sDate = etDate.getText().toString();
            String sCurrentDate = CalendarConverter.convertToDateString(dateCurrentDateTime);

            if (sDate.equals(sCurrentDate)) {
                long lCurrentTime = CalendarConverter.extractTimeAsLong(dateCurrentDateTime);
                long lTimeMin = CalendarConverter.extractTimeAsLong(Parser.parseTime(sTimeMin));
                if (lTimeMin < lCurrentTime) {
                    success = false;
                }
            } else {
                Log.i(sClassTag + "." + sMethodTag, "Setting filter's minimum time to '" + sTimeMin + "'.");
                filter.setMinStartTime(sTimeMin);
            }
        }

        return success;
    }

    private boolean setFilterMaxTime(Date dateCurrentDateTime) {
        final String sMethodTag = "setFilterMaxTime";

        String sTimeMax = etTimeMax.getText().toString();
        boolean success = true;

        if (sTimeMax.equals("")) {
            Log.w(sClassTag + "." + sMethodTag, "Input string sMaxTime is empty, using default value.");
            filter.clearMaxStartTime();
        } else {
            String sDate = etDate.getText().toString();
            String sCurrentDate = CalendarConverter.convertToDateString(dateCurrentDateTime);

            if (sDate.equals(sCurrentDate)) {
                long lCurrentTime = CalendarConverter.extractTimeAsLong(dateCurrentDateTime);
                long lTimeMax = CalendarConverter.extractTimeAsLong(Parser.parseTime(sTimeMax));
                if (lTimeMax < lCurrentTime) {
                    success = false;
                }
            } else {
                Log.i(sClassTag + "." + sMethodTag, "Setting filter's minimum time to '" + sTimeMax + "'.");
                filter.setMinStartTime(sTimeMax);
            }
        }

        return success;
    }
}