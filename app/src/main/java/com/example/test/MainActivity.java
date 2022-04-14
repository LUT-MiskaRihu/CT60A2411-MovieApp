package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
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

    private TextView debugText;
    private Button debugButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * DEBUG ELEMENTS, REMOVE OR DISABLE FROM THE FINAL RELEASE
         */
        debugButton = findViewById(R.id.debugButton);
        debugText = findViewById(R.id.debugText);

        /**
         * Assign variables to UI elements
         */
        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etTimeMin = findViewById(R.id.etTimeMin);
        etTimeMax = findViewById(R.id.etTimeMax);
        btnClearTitle = findViewById(R.id.btnClearTitle);
        btnClearDate = findViewById(R.id.btnClearDate);
        btnClearTimeMin = findViewById(R.id.btnClearTimeMin);
        btnClearTimeMax = findViewById(R.id.btnClearTimeMax);
        filter = Filter.getInstance();

        /**
         * Set onClickListeners (aka button click actions) for buttons
         */
        etTimeMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(etTimeMin);
            }
        });
        etTimeMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(etTimeMax);
            }
        });
        btnClearTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etTitle.setText(null);
            }
        });
        btnClearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter.clearField(Filter.FIELD_DATE);
                etDate.setText(null);
            }
        });
        btnClearTimeMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter.clearField(Filter.FIELD_TIME_MIN);
                etTimeMin.setText(null);
            }
        });
        btnClearTimeMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter.clearField(Filter.FIELD_TIME_MAX);
                etTimeMax.setText(null);
            }
        });


        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter.setTitle(etTitle.getText().toString());
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse("2022-04-01T22:12"));
            System.out.println(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function shows the DatePickerDialog when the user clicks on the date field,
     * and sets the date params in filter object (year, month, and day of month)
     * @param view
     */
    public void pickDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener onDateSetListener;

        // After the user click on "ok", the attributes are saved.
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                filter.setDateFor(Filter1.CALENDAR_START_BOTH, year, month, day);
                etDate.setText(String.format(
                        "%02d.%02d.%04d",
                        filter.getDayFrom(Filter1.CALENDAR_START_MIN),
                        filter.getMonthFrom(Filter1.CALENDAR_START_MIN),
                        filter.getYearFrom(Filter1.CALENDAR_START_MIN)
                ));
            }
        };

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                onDateSetListener,
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    /**
     * This function shows TimePickerDialog when the user clicks on either the minimum or maximum
     * time fields, and sets the filters attributes accordingly.
     * @param et
     */
    public void pickTime(EditText et) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener onTimeSetListener;
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if (et.equals(etTimeMin)) {
                    filter.setTimeFor(Filter1.CALENDAR_START_MIN, hour, minute);
                } else if (et.equals(etTimeMax)) {
                    filter.setTimeFor(Filter1.CALENDAR_START_MAX, hour, minute);
                }
                et.setText(String.format("%2d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                MainActivity.this,
                onTimeSetListener,
                hour,
                minute,
                true
        );
        timePickerDialog.show();
    }

    public void search(View view) {
        if (filter.getHourFrom(Filter1.CALENDAR_START_MIN) >= filter.getHourFrom(Filter1.CALENDAR_START_MAX)) {
            Toast.makeText(this, "Myöhemmän ajan on oltava myöhemmin kuin aikaisemman ajan!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Haetaan leffoja :)", Toast.LENGTH_SHORT).show();
        }
    }

    public void showFilter(View view) {
        debugText.setText(filter.toString());
    }
}