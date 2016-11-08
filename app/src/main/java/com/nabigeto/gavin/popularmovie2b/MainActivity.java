package com.nabigeto.gavin.popularmovie2b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nabigeto.gavin.popularmovie2b.Adapter.Movie_Adapter;
import com.nabigeto.gavin.popularmovie2b.Sync.MovieSyncAdapter;
import com.nabigeto.gavin.popularmovie2b.Sync.ReviewSyncAdapter;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;

public class MainActivity extends AppCompatActivity  implements MainActivityFragment.Callback {

    public boolean mTwoPane;
    public static final String FRAGMENT_NAME = "Frag1";
    public static final String KEY_FILE = "Shared_Preference_KEY_FILE_Detail";
    public static final String KEY_FILE2 = "movie";
    public static final String KEY_FILE3 = "startmovie";
    public static final String KEY_FILE4 = "n";

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
Log.v("Gavin", "Test 1");

        Log.v("Gavin", "Test 2");
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
            Log.v("Gavin", "MainActivity Passer" + table_ID);

            args.putString(KEY_FILE,table_ID);

            Detail_Movie_Fragment DF = new Detail_Movie_Fragment();
            DF.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, DF, FRAGMENT_NAME).commit();

        } else {
            Intent intent = new Intent(this, Detail_Movie_Activity.class).putExtra(KEY_FILE2,table_ID);
            startActivity(intent);
            Log.v("Gavin", "Main Activity" + table_ID);
        }

    }

}
