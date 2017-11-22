package jp.co.conol.mediaplayersample;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button playBt;
    private Button stopBt;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBt = (Button)findViewById(R.id.button1);
        stopBt = (Button)findViewById(R.id.button2);
        seekBar = (SeekBar)findViewById(R.id.seekBar1);
        playBt.setOnClickListener(this);
        stopBt.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.sample);

        seekBar.setProgress(0);
        seekBar.setMax(mediaPlayer.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                mediaPlayer.start();
                handler.post(runnable);
            }
        });

        runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                int currentPosition = mediaPlayer.getCurrentPosition();
                Log.d("currentPosition", String.valueOf(currentPosition));
                seekBar.setProgress(currentPosition);
                handler.postDelayed(this, 100);
            }
        };
    }

    // Button-control
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button1:
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                    handler.post(runnable);
                break;
            case R.id.button2:
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                seekBar.setProgress(0);
                handler.removeCallbacks(runnable);
                break;
        }
    }

    //Key-control
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK || keyCode==KeyEvent.KEYCODE_HOME){
            mediaPlayer.stop();
            mediaPlayer.release();
            handler.removeCallbacks(runnable);
            finish();
            return true;
        }
        return false;
    }
}
