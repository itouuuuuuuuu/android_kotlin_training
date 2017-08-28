package com.example.masafumi_ito.mydiary.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by masafumi_ito on 2017/08/27.
 */

public class Diary extends RealmObject {
    @PrimaryKey
    public long id;
    public String title;
    public String bodyText;
    public String date;
    public byte[] image;
}
