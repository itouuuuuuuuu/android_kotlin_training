package com.example.masafumi_ito.retrofitsampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.masafumi_ito.retrofitsampleapp.data.Bookmark;
import com.example.masafumi_ito.retrofitsampleapp.data.BookmarkEntry;
import com.example.masafumi_ito.retrofitsampleapp.data.Token;
import com.example.masafumi_ito.retrofitsampleapp.data.User;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private HatenaApiInterface mApiInterface;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // listViewの取得
        mListView = (ListView) findViewById(R.id.listView);

//        // 必ず指定するヘッダーは以下のように記述
//        OkHttpClient client = new OkHttpClient();
//        client.interceptors().add(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//
//                Request request = original.newBuilder()
//                        .header("Authorization", " NBRAarWBWRQHc8M0s-Nvaw 6YCI38bBRc8eR2dBXNIxCg")
////                        .header("foo", "foo")
//                        .method(original.method(), original.body())
//                        .build();
//
//                return chain.proceed(request);
//            }
//        });

        // Retrofitの設定
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.112.147.249")              // エンドポイント
                .addConverterFactory(GsonConverterFactory.create()) // json変換方法（gsonを使用に設定）
                .build();

        // インターフェースを実装したインスタンスの作成
        mApiInterface = retrofit.create(HatenaApiInterface.class);

        // HatenaAPI呼び出し
//        Call<BookmarkEntry> call = mApiInterface.getBookmarkEntry(HatenaApiInterface.TARGET_URL);
        Call<User> call = mApiInterface.getReq();

        // 非同期で実行
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // ログの出力
                Log.d(TAG, "onResponse: " + response.isSuccessful());

                // レスポンスをentryに格納
                User entry = response.body();


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getCause() + ", " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

}
