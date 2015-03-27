package com.example.blaze.wordchain;

import android.app.Application;
import android.content.Context;

/**
 * Created by Corey on 3/4/2015.
 */
public class GlobalVars extends Application {

    private static Context context;

    public void onCreate(){
        //Makes the MainActivity context global, giving access to files to all activities
        super.onCreate();
        GlobalVars.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return GlobalVars.context;
    }
}
