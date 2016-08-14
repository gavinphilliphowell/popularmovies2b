package com.nabigeto.gavin.popularmovie2b;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Detail_Movie_Activity extends AppCompatActivity {



    public int position;

    public static final String KEY_FILE = "Shared_Preference_KEY_FILE_Detail";
    public static final String KEY_FILE2 = "movie";



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detail_movies);


        if (savedInstanceState == null) {

            String location_ID_d = getIntent().getExtras().getString(KEY_FILE2);
            Log.v("Gavin", "DetailMovieActivity" + location_ID_d);

            Bundle arguments = new Bundle();

            arguments.putString(KEY_FILE, location_ID_d);

            Detail_Movie_Fragment fragment = new Detail_Movie_Fragment();
            fragment.setArguments(arguments);


            getSupportFragmentManager().beginTransaction().add(R.id.fragment_detail_container, fragment).commit();

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
