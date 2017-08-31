package com.example.masafumi_ito.splashapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends Activity {

    final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 指定時間後に実行
        mHandler.postDelayed(mSplashTask, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mSplashTask);
    }

    // 別スレッドで処理
    private Runnable mSplashTask = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);//画面遷移のためのIntentを準備
            startActivity(intent);//実際の画面遷移を開始
            finish();//現在のActivityを削除
        }
    };

    @Override
    public void finish() {
        super.finish();

        // 次の画面をフォードイン・スプラッシュ画面をフェードアウト
        overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
    }
}
