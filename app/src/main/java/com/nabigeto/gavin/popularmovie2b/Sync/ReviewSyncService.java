package com.nabigeto.gavin.popularmovie2b.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Gavin on 6/8/2016.
 */
public class ReviewSyncService extends Service {

        private static final Object sRSyncAdapterLock = new Object();
        private static ReviewSyncAdapter sReviewSyncAdapter = null;

        @Override
        public void onCreate() {
            Log.v("Gavin", "onCreate - ReviewSyncService");
            synchronized (sRSyncAdapterLock) {
                if (sReviewSyncAdapter == null) {
                    sReviewSyncAdapter = new ReviewSyncAdapter(getApplicationContext(), false, true);
                }
            }
        }

        @Override
        public IBinder onBind(Intent intent) {
            return sReviewSyncAdapter.getSyncAdapterBinder();
        }
}
