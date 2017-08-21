package com.example.masafumi_ito.myscheduler.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*


open class Schedule(
        @PrimaryKey
        var id: Long = 0,
        var date: Date? = null,
        var title: String = "",
        var detail: String = ""
) : RealmObject()