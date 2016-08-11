package com.nabigeto.gavin.popularmovie2b;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.nabigeto.gavin.popularmovie2b.Sync.MovieSyncAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements MainActivityFragment.Callback {

    public boolean mTwoPane;
    public static final String FRAGMENT_NAME = "Frag1";
    public static final String KEY_FILE2 = "movie";
    public static final String KEY_FILE3 = "startmovie";

    public Movie_Adapter loaddata;
    public Bundle onStartData_Bundle;
    public int position;

    private int movie_index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_detail_container) != null) {
            mTwoPane = true;
            Log.v("Gavin", "Got this far activity");
            Log.v("Gavin", "True");

            if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, new Detail_Movie_Fragment(), FRAGMENT_NAME).commit();

            }
        } else {

            mTwoPane = false;
            Log.v("Gavin", "Got this far activity + 2");
            Log.v("Gavin", "False");
        }

        MainActivityFragment mainActivityFragment = ((MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment));
        mainActivityFragment.setuseCaseLayout(!mTwoPane);

        MovieSyncAdapter.initialiseSyncAdapter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();


    }


    @Override
    public void onItemSelected(String table_ID) {
        if (mTwoPane) {

            Bundle args = new Bundle();
            Log.v("Gavin", table_ID);
            args.putString(KEY_FILE2, table_ID);


            Detail_Movie_Fragment DF = new Detail_Movie_Fragment();
            DF.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, new Detail_Movie_Fragment(), FRAGMENT_NAME).commit();

        } else {
            Intent intent = new Intent(this, Detail_Movie_Activity.class).putExtra(KEY_FILE2,table_ID);
            startActivity(intent);

        }

    }

}
