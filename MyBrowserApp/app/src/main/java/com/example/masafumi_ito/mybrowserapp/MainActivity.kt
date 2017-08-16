package com.example.masafumi_ito.mybrowserapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val INITIAL_WEBSITE = "https://www.google.co.jp/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // jsを有効に設定
        myWebView.settings.javaScriptEnabled = true

        // WebViewClientをオーバーライド
        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {

                // ページのタイトルをペートルバーに表示
                supportActionBar!!.subtitle = view.title

                // urlを表示
                urlText.setText(url)
            }
        }

        // ウェブビューの初期値を設定
        myWebView.loadUrl(INITIAL_WEBSITE)
    }

    // Browseボタンを押した場合
    fun showWebsite(view: View) {

        // 入力されたurlを取得
        var url = urlText.text.trim().toString()

        // urlのチェック
        if(!Patterns.WEB_URL.matcher(url).matches()) {
            urlText.error = "Invalid URL"
        } else {

            // http or https が付いていない場合
            if(!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url
            }
            myWebView.loadUrl(url)
        }
    }

    fun clearUrl(view: View) {
        urlText.setText("")
    }

    // バックボタンが押された場合
    override fun onBackPressed() {

        // 履歴がある場合
        if(myWebView.canGoBack()) {

            // 前に戻る
            myWebView.goBack()

            return
        }
        // 履歴がない場合
        else {
            super.onBackPressed()
        }
    }

    // activityが終了する時の処理
    override fun onDestroy() {
        super.onDestroy()

        if(myWebView != null) {
            myWebView.stopLoading() // 読み込みの停止
            myWebView.webViewClient = null
            myWebView.destroy()
        }
    }

}

// java
//public class MainActivity extends AppCompatActivity {
//
//    private WebView myWebView;
//    private EditText urlText;
//
//    private static final String INITIAL_WEBSITE = "https://www.google.co.jp/";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        myWebView = (WebView) findViewById(R.id.myWebView);
//        urlText = (EditText) findViewById(R.id.urlText);
//
//        myWebView.getSettings().setJavaScriptEnabled(true);
//        myWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                getSupportActionBar().setSubtitle(view.getTitle());
//                urlText.setText(url);
//            }
//        });
//        myWebView.loadUrl(INITIAL_WEBSITE);
//    }
//}
