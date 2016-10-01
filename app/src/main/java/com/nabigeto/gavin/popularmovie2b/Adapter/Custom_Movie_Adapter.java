package com.nabigeto.gavin.popularmovie2b.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nabigeto.gavin.popularmovie2b.MainActivityFragment;
import com.nabigeto.gavin.popularmovie2b.R;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


/**
 * Created by Gavin on 1/16/2016.
 */
public class Custom_Movie_Adapter extends CursorAdapter {



        private Context mContext;

        public boolean mUseCaseLayout = true;

        private static final int VIEW_TYPE_COUNT = 2;
        private static final int VIEW_TYPE_DETAIL = 0;
        private static final int VIEW_TYPE_GRID_VIEW = 1;

    private static class ViewHolder {

       /** public final ImageView imagePoster;
       **/  public final ImageView imagegridPoster;
        public final TextView  movieTitle;


        public ViewHolder(View view){
        /**    imagePoster = (ImageView) view.findViewById(R.id.movie_image_poster);
           **/ imagegridPoster = (ImageView) view.findViewById(R.id.movie_grid_image);
            movieTitle = (TextView) view.findViewById(R.id.grid_movie_title);

        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int viewType = getItemViewType(cursor.getPosition());

        int layoutId = 1;
   /**     switch (viewType) {

            case VIEW_TYPE_DETAIL: {
                layoutId = R.layout.fragment_details_layout;
                break;
            }
            case VIEW_TYPE_GRID_VIEW: {
    layoutId = R.layout.fragment_movie_grid_layout;
                break;
            }

    **/




            int layoutID = R.layout.fragment_movie_grid_layout;

            View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

            return view;

        }

    public Custom_Movie_Adapter(Context context, Cursor c, int flags){
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


            if ( ) {
                Picasso.with(mContext).load(url).placeholder(R.drawable.worms_head).into(viewHolder.imagegridPoster);

            }
            else {
                Picasso.with(mContext).load(url).placeholder(R.drawable.worms_head).into(viewHolder.imagegridPoster);

            }

                    String movieTitles = cursor.getString(MainActivityFragment.COL_MOVIE_TITLE) + " " + cursor.getString(MainActivityFragment.COL_MOVIE_RATING);
                    Log.v("Gavin", movieTitles + " loading this text");
                    viewHolder.movieTitle.setText(movieTitles);
        /**       break;
         **/
    }

    public boolean isOnline() {
        Context context;
        context = get;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }




    @Override
    public int getViewTypeCount(){
        return VIEW_TYPE_COUNT;
    }

}





