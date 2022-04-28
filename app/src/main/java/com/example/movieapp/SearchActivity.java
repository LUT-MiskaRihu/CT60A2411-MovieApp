package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {
    private Spinner spnTheatres;
    private EditText etTitle;
    private EditText etDate;
    private EditText etTimeMin;
    private EditText etTimeMax;
    private Button btnClearTitle;
    private Button btnClearDate;
    private Button btnClearTimeMin;
    private Button btnClearTimeMax;
    private Button btnSearch;
    private TextWatcher textWatcher;

    private Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        };

        etDate.setOnClickListener(onClickListener);
        etTimeMin.setOnClickListener(onClickListener);
        etTimeMax.setOnClickListener(onClickListener);
        btnClearTitle.setOnClickListener(onClickListener);
        btnClearDate.setOnClickListener(onClickListener);
        btnClearTimeMin.setOnClickListener(onClickListener);
        btnClearTimeMax.setOnClickListener(onClickListener);

//        etTitle.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter.setTitle(etTitle.getText().toString());
//            }
//        });

    }

    // Makes it impossible to go back to the splash screen
    @Override
    public void onBackPressed() { }

    /**
     * This function shows the DatePickerDialog when the user clicks on the date field,
     * and sets the date params in filter object (year, month, and day of month)
     */
    public void pickDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener;
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker datePicker, int iYear, int iMonth, int iDay) {
                etDate.setText(String.format("%02d.%02d.%04d", iDay, iMonth, iYear));
            }
        };

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
    public void pickTime(EditText etTimeField) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener;
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker timePicker, int iHour, int iMinute) {
//                if (etTimeField.equals(etTimeMin)) {
//                    filter.setTimeFor(Filter.START_DT_MIN, iHour, iMinute);
//                } else if (etTimeField.equals(etTimeMax)) {
//                    filter.setTimeFor(Filter.START_DT_MAX, iHour, iMinute);
//                }
                etTimeField.setText(String.format("%2d:%02d", iHour, iMinute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                SearchActivity.this,
                onTimeSetListener,
                12,
                0,
                true
        );

        timePickerDialog.show();
    }

//    /**
//     *
//     */
//    public void pickTheatre() {
//        filter.setLocationID(spnTheatres.getSelectedItem().toString());
//    }

    public void search(View view) {
        setFilterTheatre();
        setFilterTitle();
        setFilterDate();
        setFilterMinTime();
        setFilterMaxTime();
        loadDebugActivity(view);
//        pickTheatre();
//        if (filter.getHourFrom(Filter.START_DT_MIN) >= filter.getHourFrom(Filter.START_DT_MAX)) {
//            Toast.makeText(this, "Alkamisajan minimi ei voi olla suurempi kuin sen maksini!", Toast.LENGTH_LONG).show();
//        } else {
//            loadDebugActivity(view);
//        }
    }

    public void showFilter() {
        System.out.println("#### SearchActivity.showFilter() ##############################");
        System.out.println(filter.toString());
        System.out.println("#############################################################");
    }

    public void loadDebugActivity(View view) {
        Intent intent = new Intent(SearchActivity.this, DebugActivity.class);
        startActivity(intent);
    }

    private void setFilterTheatre() {
        filter.setTheatre(spnTheatres.getSelectedItem().toString());
    }

    private void setFilterTitle() {
        if (etTitle.getText().toString().equals("")) {
            filter.clearTitle();
        } else {
            filter.setTitle(etTitle.getText().toString());
        }
    }

    private void setFilterDate() {
        if (etDate.getText().toString().equals("")) {
            filter.clearMinStartDate();
        } else {
            filter.setMinStartDate(etDate.getText().toString());
        }
    }

    private void setFilterMinTime() {
        if (etTimeMin.getText().toString().equals("")) {
            filter.clearMinStartTime();
        } else {
            filter.setMinStartTime(etTimeMin.getText().toString());
        }
    }

    private void setFilterMaxTime() {
        if (etTimeMax.getText().toString().equals("")) {
            filter.clearMaxStartTime();
        } else {
            filter.setMaxStartTime(etTimeMax.getText().toString());
        }
    }
}