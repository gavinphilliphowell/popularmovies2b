package com.nabigeto.gavin.popularmovie2b.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Gavin on 6/8/2016.
 */
public class MovieSyncService extends Service {

        private static final Object sSyncAdapterLock = new Object();
        private static MovieSyncAdapter sMovieSyncAdapter = null;

        @Override
        public void onCreate() {
            synchronized (sSyncAdapterLock) {
                if (sMovieSyncAdapter == null) {
                    sMovieSyncAdapter = new MovieSyncAdapter(getApplicationContext(), true);
                }
            }
        }

        @Override
        public IBinder onBind(Intent intent) {
            return sMovieSyncAdapter.getSyncAdapterBinder();
        }
}
