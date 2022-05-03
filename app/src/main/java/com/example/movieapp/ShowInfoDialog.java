package com.example.movieapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ShowInfoDialog extends AppCompatDialogFragment {
    private TextView tvMovieTitle;
    private Button btnMovieInfo;
    private Button btnShowInfo;
    private Button btnSaveReview;
    private View view;
    private Movie movie;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_show_info, null);
        tvMovieTitle = view.findViewById(R.id.tvMovieTitle);
        btnMovieInfo = view.findViewById(R.id.btnMovieInfo);
        btnShowInfo = view.findViewById(R.id.btnShowInfo);
        btnSaveReview = view.findViewById(R.id.btnSaveReview);

        builder.setView(view)
                .setTitle("Arvostelusi ja elokuvan tiedot")
                .setNegativeButton("peruuta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("tallenna", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        tvMovieTitle.setText(movie.getTitle());

        return builder.create();
    }

}
