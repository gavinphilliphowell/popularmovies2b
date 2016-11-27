package com.nabigeto.gavin.popularmovie2b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Detail_Movie_Activity extends AppCompatActivity {



    public int position;

    public static final String KEY_FILE = "Shared_Preference_KEY_FILE_Detail";
    public static final String KEY_FILE2 = "movie";
    public static final String KEY_FILE3 = "startmovie";
    public static final String KEY_FAVOURITE = "favourite_state";
    public static final String KEY_FINDER = "favourite_finder";



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);


        if (savedInstanceState == null) {

            String location_ID_d = getIntent().getExtras().getString(KEY_FILE2);
            Boolean favourites_state = getIntent().getExtras().getBoolean(KEY_FAVOURITE);
            String favourites_finder = getIntent().getExtras().getString(KEY_FINDER);
            Log.v("Gavin", "DetailMovieActivity" + location_ID_d);

            String boolean_Value = String.valueOf(favourites_state);
            Log.v("Gavin", "MainActivity Fragment " + boolean_Value);

            Bundle arguments = new Bundle();

            arguments.putString(KEY_FILE, location_ID_d);
            arguments.putBoolean(KEY_FAVOURITE, favourites_state);
            arguments.putString(KEY_FINDER, favourites_finder);

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
