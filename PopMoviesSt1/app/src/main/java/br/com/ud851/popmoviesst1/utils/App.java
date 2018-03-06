package br.com.ud851.popmoviesst1.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Herlygenes Pinto on 17/02/2018.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}