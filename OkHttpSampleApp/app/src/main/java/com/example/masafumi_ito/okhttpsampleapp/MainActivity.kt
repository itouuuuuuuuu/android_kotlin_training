package com.example.masafumi_ito.okhttpsampleapp

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Request
import org.json.JSONException
import java.io.IOException
import okhttp3.OkHttpClient
import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.FormBody






class MainActivity : AppCompatActivity() {

    private var client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            object: MyAsyncTask(textView) {
                override fun doInBackground(vararg params: Void): String {
                    var res: String = ""
                    try {
                        res = run("http://api.openweathermap.org/data/2.5/weather?APPID=62e13640d14de6ac5f1ad02597826474&q=Tokyo")
                        val resJson = JSONObject(res)
                        val weathers = resJson.getJSONArray("weather")
                        val weather = weathers.getJSONObject(0)
                        val description = weather.getString("description")
                        Log.i("MainActivity", description)
                        res = description
                    } catch(e: IOException) {
                        e.printStackTrace()
                    } catch(e: JSONException) {
                        e.printStackTrace()
                    }
                    return res
                }
            }.execute()
        }
    }

    fun run(url: String): String {

        // POSTリクエスト時のボディ
        val MIMEType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(MIMEType, "{}")

        val formBody = FormBody.Builder()
                .add("tokyo", "130010")
                .add("osaka", "270000")
                .add("name", "nanashinogonbei")
                .add("action", "hoge")
                .add("value", "fuga")
                .build()

        // リクエストを作成
        val request = Request.Builder()
                             .url(url)                                              // APIのURL
                             .header("User-Agent", "OkHttp Headers.java")           // ヘッダーを追加
                             .addHeader("Accept", "application/json; q=0.5")
                             .addHeader("Accept", "application/vnd.github.v3+json")
                             .post(requestBody)                                     // POSTリクエスト時は付加
                             .build()

        val response = client.newCall(request).execute()
        return response.body()!!.string()
    }

    // OkHttpでは通信処理をメインスレッドで実行しようとするとエラーが発生するのでAsyncTaskを利用する
    open class MyAsyncTask(val textView: TextView) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String? = null

        // doInBackground実行後は、このメソッドに返ってくる
        override fun onPostExecute(res: String) {
            textView.text = res
        }
    }
}









