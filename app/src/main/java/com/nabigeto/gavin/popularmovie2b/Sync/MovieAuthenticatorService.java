package com.nabigeto.gavin.popularmovie2b.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Gavin on 6/8/2016.
 */
public class MovieAuthenticatorService extends Service {
        // Instance field that stores the authenticator_m object
        private MovieAuthenticator mAuthenticator;

        @Override
        public void onCreate() {
            // Create a new authenticator_m object
            mAuthenticator = new MovieAuthenticator(this);
        }

        /*
         * When the system binds to this Service to make the RPC call
         * return the authenticator_m's IBinder.
         */
        @Override
        public IBinder onBind(Intent intent) {
            return mAuthenticator.getIBinder();
        }
}
