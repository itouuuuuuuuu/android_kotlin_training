package com.example.masafumi_ito.retrofitsampleapp;

import com.example.masafumi_ito.retrofitsampleapp.data.BookmarkEntry;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by masafumi_ito on 2017/08/28.
 */

public interface HatenaApiInterface {
    String END_POINT = "http://b.hatena.ne.jp";
    String TARGET_URL = "http://b.hatena.ne.jp/ctop/it";

//    // headerの設定
//    @Headers({
//            "header: hogehoge",
//            "header: foofoo"
//    })

    // はてなブックマークエントリー情報取得API
    // http://developer.hatena.ne.jp/ja/documents/bookmark/apis/getinfo
    @GET("/entry/jsonlite/")
    Call<BookmarkEntry> getBookmarkEntry(@Query("url") String target);
}
