package com.example.masafumi_ito.mydiary;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by masafumi_ito on 2017/08/27.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
