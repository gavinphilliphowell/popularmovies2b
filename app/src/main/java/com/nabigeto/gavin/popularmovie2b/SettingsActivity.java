package com.nabigeto.gavin.popularmovie2b;


import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Favourite_db_Helper;


public class SettingsActivity extends AppCompatActivity {

    View reset_favourites_button;

    public CheckBox reset_favourite_checkbox;



    public SettingsActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity_layout);



        reset_favourite_checkbox = (CheckBox) findViewById(R.id.reset_favourite_check);
        reset_favourite_checkbox.setChecked(false);
        reset_favourite_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Movie_Favourite_db_Helper movie_favourite_db_helper = new Movie_Favourite_db_Helper(getApplicationContext());

                SQLiteDatabase db = movie_favourite_db_helper.getWritableDatabase();

                movie_favourite_db_helper.onClear(db);
                movie_favourite_db_helper.onCreate(db);
            }
        });



        }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }




}
