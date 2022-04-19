package com.example.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TheatreFragment extends Fragment {
    private View view;
    private Database database = Database.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theatre_list_item, container, false);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<Show> adapter = new ArrayAdapter<Show>(getActivity(), android.R.layout.simple_list_item_1, Filter.getInstance().getShows());
        ListView list = view.findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    public void setView(View view) {
        this.view = view;
    }
}
