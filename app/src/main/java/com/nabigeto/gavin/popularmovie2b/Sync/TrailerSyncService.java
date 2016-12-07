package com.nabigeto.gavin.popularmovie2b.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Gavin on 6/8/2016.
 */
public class TrailerSyncService extends Service {

        private static final Object sTSyncAdapterLock = new Object();
        private static TrailerSyncAdapter sTrailerSyncAdapter = null;

        @Override
        public void onCreate() {
            synchronized (sTSyncAdapterLock) {
                if (sTrailerSyncAdapter == null) {
                    sTrailerSyncAdapter = new TrailerSyncAdapter(getApplicationContext(), false, true);
                }
            }
        }

        @Override
        public IBinder onBind(Intent intent) {
            return sTrailerSyncAdapter.getSyncAdapterBinder();
        }
}
