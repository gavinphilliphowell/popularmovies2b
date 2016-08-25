package com.nabigeto.gavin.popularmovie2b.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nabigeto.gavin.popularmovie2b.Detail_Movie_Fragment;
import com.nabigeto.gavin.popularmovie2b.MainActivityFragment;
import com.nabigeto.gavin.popularmovie2b.R;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Gavin on 8/19/2016.
 */
public class Movie_Review_Adapter extends CursorAdapter {


    private Context mContext;

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_DETAIL = 0;
    private static final int VIEW_TYPE_GRID_VIEW = 1;
    private static class ViewHolder {

        public final TextView  movieReview1;
        public final TextView  movieReview2;
        public final TextView  movieReview3;

        public ViewHolder(View view){

            movieReview1 = (TextView) view.findViewById(R.id.grid_movie_title);
            movieReview2 = (TextView) view.findViewById(R.id.grid_movie_title);
            movieReview3 = (TextView) view.findViewById(R.id.grid_movie_title);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int layoutID = R.layout.fragment_movie_grid_layout;

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;

    }

    public Movie_Review_Adapter(Context context, Cursor c, int flags){
        super(context, c, flags);
        mContext = context;
    }


    @Override
    public void bindView (View view, Context context, Cursor cursor) {





        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int viewType = getItemViewType(cursor.getPosition());
        String viewt = Integer.toString(viewType);
        Log.v("Gavin", viewt);
        int image_file_position = cursor.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE);

        String url = cursor.getString(image_file_position);

        Picasso.with(mContext).load(url).placeholder(R.drawable.worms_head).into(viewHolder.imagegridPoster);
        String movieTitles = cursor.getString(MainActivityFragment.COL_MOVIE_TITLE) + " " + cursor.getString(MainActivityFragment.COL_MOVIE_RATING);
        Log.v("Gavin", movieTitles + " loading this text");
        viewHolder.movieTitle.setText(movieTitles);

    }


    @Override
    public int getViewTypeCount(){
        return VIEW_TYPE_COUNT;
    }

}

