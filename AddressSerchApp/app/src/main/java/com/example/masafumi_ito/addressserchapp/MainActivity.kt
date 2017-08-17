package com.example.masafumi_ito.addressserchapp

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import org.json.JSONException
import java.io.IOException
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    open class MyAsyncTask : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String? {
            return null
        }
    }

    // 検索ボタンがクリックされた場合
    fun OnSearchButtonClicked(view: View) {
        object: MyAsyncTask() {
            override fun doInBackground(vararg params: Void): String {
                var res: String = ""
                try {
                    res = run("http://api.openweathermap.org/data/2.5/weather?APPID=<自身のAPPID>&q=Tokyo")
                    val resJson = JsonObject(res)
                    val weathers = resJson.getJSONArray("weather")
                    val weather = weathers.getJsonObject(0)
                    val description = weather.getString("description")
                    Log.i("MainActivity", description)
                    res = description
                } catch(e: IOException) {
                    e.printStackTrace()
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            }
        }.execute()
    }
}


