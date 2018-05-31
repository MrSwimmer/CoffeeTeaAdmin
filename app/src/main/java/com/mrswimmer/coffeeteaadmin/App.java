package com.mrswimmer.coffeeteaadmin;

import android.app.Application;

import com.mrswimmer.coffeeteaadmin.di.AppComponent;
import com.mrswimmer.coffeeteaadmin.di.DaggerAppComponent;
import com.mrswimmer.coffeeteaadmin.di.module.SharedPreferencesModule;

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(getApplicationContext()))
                .build();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

