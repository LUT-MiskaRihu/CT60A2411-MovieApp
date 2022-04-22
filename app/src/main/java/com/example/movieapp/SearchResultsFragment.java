package com.example.movieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SearchResultsFragment extends Fragment {
    private View view;
    private Database database = Database.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_results, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Show> shows = Filter.getInstance().getShows();
        ArrayAdapter<Show> adapter = new ArrayAdapter<Show>(getActivity(), android.R.layout.simple_list_item_1, shows);
        ListView list = view.findViewById(R.id.searchResults);
        list.setAdapter(adapter);
        if (shows.size() == 0) {
            Toast.makeText(getActivity(),"Hakukriteereitä vastaavia esityksiä ei löytynyt.",Toast.LENGTH_SHORT).show();
        }
    }

    public void setView(View view) {
        this.view = view;
    }
}
