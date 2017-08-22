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



class MainActivity : AppCompatActivity() {

    internal var client = OkHttpClient()

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
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        return response.body()!!.string()
    }

    // OkHttpでは通信処理をメインスレッドで実行しようとするとエラーが発生するのでAsyncTaskを利用する
    open class MyAsyncTask(val textView: TextView) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String? {
            return null
        }

        // doInBackground実行後は、このメソッドに返ってくる
        override fun onPostExecute(res: String) {
            textView.text = res
        }
    }
}









