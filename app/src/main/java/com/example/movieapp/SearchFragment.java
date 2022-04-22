package com.example.movieapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movieapp.ui.main.PageViewModel;
import com.example.movieapp.ui.main.SectionsPagerAdapter;

import java.util.Calendar;
import java.util.zip.Inflater;

public class SearchFragment extends Fragment {

    private View view;
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

    private Context context = this.getContext();

    private Filter filter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Assign variables to UI elements
        spnTheatres = getView().findViewById(R.id.spnTheaters);
        etTitle = view.findViewById(R.id.etTitle);
        etDate = view.findViewById(R.id.etDate);
        etTimeMin = view.findViewById(R.id.etTimeMin);
        etTimeMax = view.findViewById(R.id.etTimeMax);
        btnClearTitle = view.findViewById(R.id.btnClearTitle);
        btnClearDate = view.findViewById(R.id.btnClearDate);
        btnClearTimeMin = view.findViewById(R.id.btnClearTimeMin);
        btnClearTimeMax = view.findViewById(R.id.btnClearTimeMax);

        // Initiate spinner
        ArrayAdapter<Theatre> theatreAdapter = new ArrayAdapter<Theatre>(getContext(), android.R.layout.simple_spinner_dropdown_item, Database.getInstance().getTheatres());
        spnTheatres.setAdapter(theatreAdapter);

        filter = Filter.getInstance();

        // Set onClick actions
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pick time min
                if (view == etTimeMin) {
                    pickTime(etTimeMin);
                }

                // Pick time max
                else if (view == etTimeMax) {
                    pickTime(etTimeMax);
                }

                // Pick date
                else if (view == etDate) {
                    pickDate();
                }

                // Clear title
                else if (view == btnClearTitle) {
                    filter.clearField(Filter.FIELD_TITLE);
                    etTitle.setText(null);
                }

                // Clear date
                else if (view == btnClearDate) {
                    filter.clearField(Filter.FIELD_DATE);
                    etDate.setText(null);
                }

                // Clear time min
                else if (view == btnClearTimeMin) {
                    filter.clearField(Filter.FIELD_TIME_MIN);
                    etTimeMin.setText(null);
                }

                // Clear time max
                else if (view == btnClearTimeMax) {
                    filter.clearField(Filter.FIELD_TIME_MAX);
                    etTimeMax.setText(null);
                }

                // Search
                else if (view == btnSearch) {
                    pickTheatre();
                    if (filter.getHourFrom(Filter.START_DT_MIN) >= filter.getHourFrom(Filter.START_DT_MAX)) {
                        Toast.makeText(getActivity(), "Alkamisajan minimi ei voi olla suurempi kuin sen maksini!", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(getActivity(), DebugActivity.class);
                        getActivity().startActivity(intent);
                    }
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
    }

    /**
     * This function shows the DatePickerDialog when the user clicks on the date field,
     * and sets the date params in filter object (year, month, and day of month)
     */
    public void pickDate() {
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
                filter.setDateFor(Filter.START_DT_BOTH, year, month, day);
                etDate.setText(String.format(
                        "%02d.%02d.%04d",
                        filter.getDayFrom(Filter.START_DT_MIN),
                        filter.getMonthFrom(Filter.START_DT_MIN),
                        filter.getYearFrom(Filter.START_DT_MIN)
                ));
            }
        };

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(
                getContext(),
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
     * @param et text field to edit
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
                    filter.setTimeFor(Filter.START_DT_MIN, hour, minute);
                } else if (et.equals(etTimeMax)) {
                    filter.setTimeFor(Filter.START_DT_MAX, hour, minute);
                }
                et.setText(String.format("%2d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                onTimeSetListener,
                12,
                0,
                true
        );
        timePickerDialog.show();
    }

    /**
     *
     */
    public void pickTheatre() {
        filter.setLocationID(spnTheatres.getSelectedItem().toString());
    }

    public void showFilter() {
        System.out.println("#### SearchActivity.showFilter() ##############################");
        System.out.println(filter.toString());
        System.out.println("#############################################################");
    }

    public void loadDebugActivity(View view) {
        Intent intent = new Intent(getContext(), DebugActivity.class);
        startActivity(intent);
    }
}