package com.soundtrack.bart.soundtrack;

import android.app.Application;
import com.parse.Parse;
import com.facebook.FacebookSdk;

/**
 * Created by Bart on 07/12/2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //enable Parse connection
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "1sftv7BRaPviSonGJHRbqp4lQcWC4v1Yac0KQC8P", "73XeRNJ41nxiPKpnrYivVjxoXxljqDvADDAt9gRU");
    }
}
