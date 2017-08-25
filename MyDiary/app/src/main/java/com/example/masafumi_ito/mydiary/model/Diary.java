package com.example.masafumi_ito.mydiary.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Diary extends RealmObject {
    @PrimaryKey
    public long id;
    public String title;
    public String bodyText;
    public String date;
    public byte[] image;
}
