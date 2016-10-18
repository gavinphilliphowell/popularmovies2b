package com.nabigeto.gavin.popularmovie2b;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Favourite_db_Helper;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Favourites_Contract;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class Detail_Movie_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {



    Context dContext;
    public static final String KEY_FILE = "Shared_Preference_KEY_FILE_Detail";
    public static final String KEY_FILE2 = "movies";
    public static final String KEY_FILE3 = "startmovie";

    public int position;
    public String mSw;

    public String sduri;


    View view = null;
/**
    public String mTitle;
    public String mRelease;
    public String mRating;
    public String mInfo;
    public String mImage;
    public String mBackground;
**/
    static final String DETAIL_URI = "URI";

    private ShareActionProvider mShareActionProvider;

    /**
    String mMovie_ID_http = ("/movie/" + mMovie_ID + "/video");
    String mMovie_Trailer_http = ("/movie/" + mMovie_Trailer + "/reviews");


    public static final String AUTHORITY = ".UtilitiesDB.movieContentProvider";
    public static final String ACCOUNT_TYPE = "nabigeto.com";
    public static final String ACCOUNT = "dummyaccount";

    Account rAccount;
    **/
    public String dUri;

    private static final String Youtube_KEY = "AIzaSyD4abokmDnidF1bBI2c5nrSMIb7Q73Tnnk";

    private static final int DETAIL_LOADER = 1;
    private static final  int REVIEW_LOADER = 2;
    private static final int TRAILER_LOADER = 3;
    private static final int FAVOURITE_LOADER = 4;


    private static final String [] DETAIL_COLUMNS = {
            Movie_Contract.MovieInfo.TABLE_NAME + "." +
                    Movie_Contract.MovieInfo._ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_TITLE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RELEASE_DATE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RATING,
            Movie_Contract.MovieInfo.COLUMN_NAME_INFO,
            Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW1,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW2,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW3,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR1,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR2,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR3,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3,
            Movie_Contract.MovieInfo.COLUMN_FAVOURITE


    };

    public static final int _ID = 0;
    public static final int COL_MOVIE_ENTRY_ID = 1;
    public static final int COL_MOVIE_ID = 2;
    public static final int COL_MOVIE_TITLE_D = 3;
    public static final int COL_MOVIE_RELEASE_DATE_D = 4;
    public static final int COL_MOVIE_RATING = 5;
    public static final int COL_MOVIE_INFO = 6;
    public static final int COL_MOVIE_IMAGE_FILE = 7;
    public static final int COL_MOVIE_REVIEW1 = 8;
    public static final int COL_MOVIE_REVIEW2 = 9;
    public static final int COL_MOVIE_REVIEW3 = 10;
    public static final int COL_MOVIE_REVIEW_AUTHOR1 =11;
    public static final int COL_MOVIE_REVIEW_AUTHOR2 =12;
    public static final int COL_MOVIE_REVIEW_AUTHOR3 =13;
    public static final int COL_MOVIE_TRAILER1 = 14;
    public static final int COL_MOVIE_TRAILER2 = 15;
    public static final int COL_MOVIE_TRAILER3 = 16;
    public static final int COL_FAVOURITE = 17;



    /**    public static final int COL_REVIEW_ONE = 5;
    public static final int COL_REVIEW_TWO = 6;
    public static final int COL_MOVIE_TRAILER = 7;
**/

    public static final String [] Favourite_Columns = {
            Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F + "." +
            Movie_Favourites_Contract.FavouriteInfo._ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_ENTRY_ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_INFO,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW1,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW2,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW3,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR1,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR2,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR3,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER1,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER2,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER3,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_FAVOURITE

    };


    public ImageView dImage_File;
    public TextView dID;
    public TextView dTitle;
    public TextView dRelease_Date;
    public TextView dRating;
    public TextView dInfo;
    public TextView dFavourites;

    public TextView dReview1;
    public TextView dReview2;
    public TextView dReview3;

    public TextView dReviewauthor1;
    public TextView dReviewauthor2;
    public TextView dReviewauthor3;

    public ImageView dTrailer1;
    public ImageView dTrailer2;
    public ImageView dTrailer3;

    public ImageView movie_intent_button1;
    public ImageView movie_intent_button2;
    public ImageView movie_intent_button3;

    public String m_id;
    public String movie_EntryID;
    public String movie_id;
    public String movie_TitleF;
    public String movie_ReleaseF;
    public String movie_RatingF;
    public String movie_ImageF;
    public String movie_InfoF;
    public String movie_Review1F;
    public String movie_Review1_AuthorF;
    public String movie_Review2F;
    public String movie_Review2_AuthorF;
    public String movie_Review3F;
    public String movie_Review3_AuthorF;
    public String movie_Trailer1;
    public String movie_Trailer2;
    public String movie_Trailer3;
    public String movie_Favourite;

    public String movie_id_holder;


    public Detail_Movie_Fragment() {
        // Required empty public constructor
      /**  dContext = getContext();  **/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.v("Gavin", "onActivityCreated");
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        getLoaderManager().initLoader(REVIEW_LOADER, null, this);
        getLoaderManager().initLoader(TRAILER_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null) {
            dUri = arguments.getString(KEY_FILE);

            Log.v("Gavin", "uri " + "KEY_FILE" + dUri);
        }
        else{
            dUri = "3";
        }
        Log.v("Gavin", "uri " + dUri);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_details_layout, container, false);
        Log.v("Gavin", "Got to the bit in the detail fragment");


        dImage_File = (ImageView) rootView.findViewById(R.id.detail_movie_poster);
        dRating = (TextView) rootView.findViewById(R.id.movie_user_rating_d);
        dRelease_Date = (TextView) rootView.findViewById(R.id.movie_release_date_d);
        dInfo = (TextView) rootView.findViewById(R.id.movie_info_d);
        dTitle = (TextView) rootView.findViewById(R.id.movie_title_d);

        dReview1 = (TextView) rootView.findViewById(R.id.movie_review1);
        dReview2 = (TextView) rootView.findViewById(R.id.movie_review2);
        dReview3 = (TextView) rootView.findViewById(R.id.movie_review3);

        dReviewauthor1 = (TextView) rootView.findViewById(R.id.movie_review1_author);
        dReviewauthor2 = (TextView) rootView.findViewById(R.id.movie_review2_author);
        dReviewauthor3 = (TextView) rootView.findViewById(R.id.movie_review3_author);

        dTrailer1 = (ImageView) rootView.findViewById(R.id.movie_Trailer1);
        dTrailer2 = (ImageView) rootView.findViewById(R.id.movie_Trailer2);
        dTrailer3 = (ImageView) rootView.findViewById(R.id.movie_Trailer3);

        final CheckBox favouritebutton = (CheckBox) rootView.findViewById(R.id.checkbox_favourite);

        favouritebutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                    boolean checked = ((CheckBox) view).isChecked();

                    switch(view.getId()){
                        case R.id.checkbox_favourite:


                            if(checked){
                             String favouritesortOrder = Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING + " ASC";

                                String rSelectionClause = Movie_Favourites_Contract.FavouriteInfo._ID + " LIKE ?";
                                String[] rSelectionArgs = new String[]{dUri};
                                Log.v("Gavin","got to this bit loader 2");

                                Log.v("Gavin", "Checkbox checked");

                                Cursor favourite_details = getContext().getContentResolver().query(
                                        Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F,
                                        Favourite_Columns,
                                        rSelectionClause,
                                        rSelectionArgs,
                                        favouritesortOrder
                                );

                                int favourite_count = favourite_details.getCount();

                                Log.v("Gavin", "Details Movie Fragment On Click" + favourite_count);

                                Log.v("Gavin", "got to this bit loader 3");

                                ContentValues favouriteValues = new ContentValues();

                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo._ID, m_id);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_ENTRY_ID, movie_EntryID);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE, movie_TitleF);
                                Log.v("Gavin", "Detail Movie Fragment" + movie_TitleF);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING, movie_RatingF);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE, movie_ReleaseF);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_INFO, movie_InfoF);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE, movie_ImageF);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID, movie_id);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW1, movie_Review1F);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW2, movie_Review2F);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW3, movie_Review3F);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR1, movie_Review1_AuthorF);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR2, movie_Review2_AuthorF);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR3, movie_Review3_AuthorF);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER1, movie_Trailer1);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER2, movie_Trailer2);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER3, movie_Trailer3);
                                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_FAVOURITE, movie_Favourite);

                                if (favourite_count != 0){
                                    String favourite_update_name;
                                    long favourite_update_id;

                                    favourite_update_name = favourite_details.getString(COL_MOVIE_TITLE_D);


                                    favourite_update_id = Long.parseLong(favourite_details.getString(COL_MOVIE_ENTRY_ID));

                                    movie_id_holder = favourite_details.getString(COL_MOVIE_ENTRY_ID);

                                    Log.v("Gavin", "Detail_Movie_Fragment" + favourite_update_name);
                                    favourite_details.close();

                                    Uri favourite_movie_update = ContentUris.withAppendedId(Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F,favourite_update_id);

                                    int favourite_details_update = getContext().getContentResolver().update(
                                            favourite_movie_update,
                                            favouriteValues,
                                            null,
                                            null
                                    );
                                }

                                else {

                                     Uri movie_insert = getContext().getContentResolver().insert(
                                            Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F,
                                            favouriteValues);
                                }

                                    break;

                            }

                            else{


                                String rSelectionClause = Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE + " LIKE ?";
                                long favourite_delete = Long.parseLong(movie_id_holder);


                                Log.v("Gavin","got to this bit loader 2");

                                Uri favourite_movie_delete = ContentUris.withAppendedId(Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F, favourite_delete);


                                int favourite_details_delete = getContext().getContentResolver().delete(
                                        favourite_movie_delete,
                                        null,
                                        null);


                            }
                            break;
                    }
                }

        });

        dTrailer1.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v){
                watchYoutubeVideo(movie_Trailer1);
            }
        });

        dTrailer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                watchYoutubeVideo(movie_Trailer2);
            }
        });

        dTrailer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                watchYoutubeVideo(movie_Trailer3);
            }
        });


        Log.v("Gavin", "About to load rootview");
        return rootView;
    }

    public boolean readState(){

        boolean favourite_State = false;


        return favourite_State;
    }

    private void setState(boolean s_state){

        boolean favourite_State = s_state;

        if (s_state = true){

            getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String position;
        String row_Pref;

        switch (id) {


            case DETAIL_LOADER:

                position = " _ID = " + dUri;
                /**      Uri prefs_uri2 =  + " _ID " prefs_uri.getLastPathSegment();
                 **/
                row_Pref = dUri;
                Log.v("Gavin", "dUuri" + row_Pref);
                if (null != dUri) {
                    Log.v("Gavin", "Detail Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Contract.MovieInfo.CONTENT_URI_R,
                            DETAIL_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Detail Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;

            case REVIEW_LOADER:

                position = " _ID = " + dUri;
                /**      Uri prefs_uri2 =  + " _ID " prefs_uri.getLastPathSegment();
                 **/
                row_Pref = dUri;
                Log.v("Gavin", "dUuri" + row_Pref);
                if (null != dUri) {
                    Log.v("Gavin", "Detail Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Contract.MovieInfo.CONTENT_URI_R,
                            DETAIL_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Review Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;


            case TRAILER_LOADER:

                position = " _ID = " + dUri;
                /**      Uri prefs_uri2 =  + " _ID " prefs_uri.getLastPathSegment();
                 **/
                row_Pref = dUri;
                Log.v("Gavin", "dUuri" + row_Pref);
                if (null != dUri) {
                    Log.v("Gavin", "Trailer Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Contract.MovieInfo.CONTENT_URI_R,
                            DETAIL_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Trailer Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;

            case FAVOURITE_LOADER:

                position = " _ID = " + dUri;
                /**      Uri prefs_uri2 =  + " _ID " prefs_uri.getLastPathSegment();
                 **/
                row_Pref = dUri;
                Log.v("Gavin", "dUuri" + row_Pref);
                if (null != dUri) {
                    Log.v("Gavin", "Favourite Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F,
                            DETAIL_COLUMNS,
                            position,
                            null,
                            null
                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Favourite_Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data_d) {

        int loader_ID;
        int position;
        String positions;

        loader_ID = loader.getId();
        dContext = getContext();

        switch (loader_ID) {


            case DETAIL_LOADER:

                data_d.moveToFirst();
                Log.v("Gavin", "Position passed to loader");
                position = data_d.getPosition();
                positions = Integer.toString(position);
                Log.v("Gavin", "Cursor position in detail fragment" + positions);

                int movie_api_id = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID);
                String movie_api_id_s = data_d.getString(movie_api_id);


                int image_file_position = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE);


                movie_ImageF = data_d.getString(image_file_position);
                Log.v("Gavin", "DetailActivityFragment" + movie_ImageF);

                Log.v("Gavin", "Loading picasso in detail fragment");

                Picasso.with(dContext).load(movie_ImageF).placeholder(R.drawable.worms_head).into(dImage_File);

                int filmTitle = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TITLE);
                movie_TitleF = data_d.getString(filmTitle);
                Log.v("Gavin", "DetailActivityFragment" + movie_TitleF);

                dTitle.setText(movie_TitleF);

                movie_InfoF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_INFO);
                dInfo.setText(movie_InfoF);

                movie_ReleaseF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RELEASE_DATE_D);
                Log.v("Gavin", movie_ReleaseF);
                dRelease_Date.setText(movie_ReleaseF);

                movie_RatingF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RATING);
                Log.v("Gavin", movie_RatingF);
                dRating.setText(movie_RatingF);


                break;

                case REVIEW_LOADER:

                    data_d.moveToFirst();

                    movie_Review1F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW1);
                    if (movie_Review1F != "b"){
                        dReview1.setVisibility(View.VISIBLE);
                        dReview1.setText(movie_Review1F);
                    }

                    movie_Review1_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR1);
                    if (movie_Review1_AuthorF != "b"){
                        dReviewauthor1.setVisibility(View.VISIBLE);
                        dReviewauthor1.setText(movie_Review1_AuthorF);
                    }

                    movie_Review2F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW2);
                    if (movie_Review2F != "b"){
                        dReview2.setVisibility(View.VISIBLE);
                        dReview2.setText(movie_Review2F);
                    }

                    movie_Review2_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR2);
                    if (movie_Review2_AuthorF != "b"){
                        dReviewauthor2.setVisibility(View.VISIBLE);
                        dReviewauthor2.setText(movie_Review2_AuthorF);
                    }

                    movie_Review3F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW3);
                    if (movie_Review3F != "b"){
                        dReview3.setVisibility(View.VISIBLE);
                        dReview3.setText(movie_Review3F);
                    }

                    movie_Review3_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR3);
                    if (movie_Review3_AuthorF != "b"){
                        dReviewauthor3.setVisibility(View.VISIBLE);
                        dReviewauthor3.setText(movie_Review3_AuthorF);
                    }

                break;

                case TRAILER_LOADER:

                    String url_youtube;

                    data_d.moveToFirst();

                    movie_Trailer1 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER1);
                    if (movie_Trailer1 != "b"){
                        dTrailer1.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movie_Trailer1 + "/1.jpg";
                        Log.v("Gavin", url_youtube);
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer1);
            /**            dTrailer1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.v("Gavin", "Youtube video launched");
                            }
                        });
             **/
                    }


                    movie_Trailer2 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER2);
                    if (movie_Trailer2 != "b"){
                        dTrailer2.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movie_Trailer2 + "/1.jpg";
                        Log.v("Gavin", url_youtube);
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer2);

                    }

                    movie_Trailer3 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER3);
                    if (movie_Trailer3 != "b"){
                        dTrailer3.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movie_Trailer3 + "/1.jpg";
                        Log.v("Gavin", url_youtube);
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer3);

                    }

            case FAVOURITE_LOADER:

                data_d.moveToFirst();

                movie_id = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_ID);
                movie_EntryID = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_ENTRY_ID);
                movie_TitleF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TITLE_D);
                Log.v("Gavin", "Favourite_Loader" + movie_TitleF);
                movie_ReleaseF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RELEASE_DATE_D);
                movie_RatingF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RATING);
                movie_ImageF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_IMAGE_FILE);
                movie_InfoF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_INFO);
                movie_Review1F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW1);
                Log.v("Gavin", "Favourite_Loader" + movie_Review1F);
                movie_Review1_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR1);
                movie_Review2F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW2);
                movie_Review2_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR2);
                movie_Review3F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW3);
                movie_Review3_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR3);
                movie_Trailer1 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER1);
                movie_Trailer2 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER2);
                movie_Trailer3 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER3);
                movie_Favourite = data_d.getString(Detail_Movie_Fragment.COL_FAVOURITE);

                Movie_Favourite_db_Helper fdbHelper = new Movie_Favourite_db_Helper(getContext());

                SQLiteDatabase db = fdbHelper.getWritableDatabase();

                ContentValues favouriteValues = new ContentValues();

                Log.v("Gavin", "Favourite Loader upload ++" + movie_EntryID);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID, movie_id);

                Log.v("Gavin", "Favourite Loader upload ++" + movie_id);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_ENTRY_ID, "y");

                Log.v("Gavin", "Favourite Loader upload" + movie_TitleF);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE, movie_TitleF);

                Log.v("Gavin", "Favourite Loader upload" + movie_ReleaseF);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE, movie_ReleaseF);

                Log.v("Gavin", "Favourite Loader upload" + movie_RatingF);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING, movie_RatingF);

                Log.v("Gavin", "Favourite Loader upload" +  movie_ImageF);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE, movie_ImageF);

                Log.v("Gavin", "Favourite Loader upload" + movie_InfoF);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_INFO, movie_InfoF);

                Log.v("Gavin", "Favourite Loader upload" + movie_Review1F);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW1, movie_Review1F);

                Log.v("Gavin", "Favourite Loader upload" + movie_Review1_AuthorF);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR1, movie_Review1_AuthorF);

                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW2, movie_Review2F);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR2, movie_Review2_AuthorF);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW3, movie_Review3F);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR3, movie_Review3_AuthorF);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER1, movie_Trailer1);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER2, movie_Trailer2);
                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER3, movie_Trailer3);

                favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_FAVOURITE, "y");

                long id_insert = db.insert(Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F, null, favouriteValues);

                db.close();

        }






    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
/**
    public static Account CreateSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {

        }
        return newAccount;
    }
**/

}
