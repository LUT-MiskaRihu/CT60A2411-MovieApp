package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    Button button;
    private ListView lvResults;
    private ArrayList<Show> alShows;
    private ArrayAdapter<Show> aaShows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        button = findViewById(R.id.button);
        lvResults = findViewById(R.id.lvResults);

        alShows = Filter.getInstance().getShows();
        aaShows = new ArrayAdapter<>(ResultsActivity.this, android.R.layout.simple_list_item_1, alShows);
        lvResults.setAdapter(aaShows);

        if (alShows.size() == 0) {
            Toast.makeText(ResultsActivity.this, "Hakukriteereitä vastaavia esityksiä ei löytynyt.", Toast.LENGTH_LONG).show();
        }

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                Movie movie = alShows.get(index);
                openDialog(movie.getTitle());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultsActivity.this, DebugFilterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openDialog(String sMovieTitle) {
        ShowInfoDialog showInfoDialog = new ShowInfoDialog();
        showInfoDialog.show(getSupportFragmentManager(), "Arvostele elokuva\n" + sMovieTitle);
    }
}