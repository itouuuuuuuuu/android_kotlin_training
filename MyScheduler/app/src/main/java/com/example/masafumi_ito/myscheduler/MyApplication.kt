package com.example.masafumi_ito.myscheduler

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration



class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Realmの初期化
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().build())
    }
}
