package com.example.masafumi_ito.mycountdowntimer;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTimerText;
    SoundPool mSoundPool;
    int mSoundResId;

    // カウントダウンタイマークラスの実装
    public class MyCountdownTimer extends CountDownTimer {
        public boolean isRunning = false;

        // コンストラクタ
        // millisInFuture： 残り時間（ミリ秒）
        // countDownInterval： onTickメソッドを実行する間隔
        public MyCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        // コンストラクタで指定した間隔で実行されるメソッド
        @Override
        public void onTick(long l) {
            long minute = l / 1000 / 60;
            long second = l / 1000 % 60;
            mTimerText.setText(String.format("%1d:%02d", minute, second));
        }

        // タイマー終了時に呼び出されるメソッド
        @Override
        public void onFinish() {
            mTimerText.setText("0:00");
            mSoundPool.play(mSoundResId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerText = (TextView) findViewById(R.id.timer_text);
        mTimerText.setText("3:00");

        final MyCountdownTimer mTimer = new MyCountdownTimer(3 * 60 * 1000, 100);

        final FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mTimer.isRunning) {
                    mTimer.isRunning = false;
                    mTimer.cancel(); // タイマーの停止
                    mFab.setImageResource(R.drawable.ic_play_arrow_black_24dp); // 画像差し替え
                } else {
                    mTimer.isRunning = true;
                    mTimer.start(); // タイマーの開始
                    mFab.setImageResource(R.drawable.ic_stop_black_24dp);  // 画像差し替え
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 音の設定（androidバージョンごとに設定）
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //noinspection deprecation
            mSoundPool = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
        } else {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }

        // 音の読み込み
        mSoundResId = mSoundPool.load(this, R.raw.bellsound, 1);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSoundPool.release();
    }
}
