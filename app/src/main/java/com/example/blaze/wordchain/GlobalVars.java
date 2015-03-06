package com.example.blaze.wordchain;

import android.app.Application;
import android.content.Context;

/**
 * Created by Blaze on 3/4/2015.
 */
public class GlobalVars extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        GlobalVars.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return GlobalVars.context;
    }
}
