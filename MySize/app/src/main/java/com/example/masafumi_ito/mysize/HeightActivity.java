package com.example.masafumi_ito.mysize;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class HeightActivity extends AppCompatActivity {

    public static final String HEIGHT = "HEIGHT"; // 共有プリファレンスへ保存する時のキーとなる文字列
    private TextView mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);

        // レイアウトからidを取得
        mHeight = (TextView) findViewById(R.id.height);

        // 共有プリファレンスのインスタンスを作成
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        // 共有プリファレンスに保存されているデータを取得してセット（取得に失敗したら160をセット）
        int height = pref.getInt(HEIGHT, 160);
        mHeight.setText(String.valueOf(height));

        /// スピナーの取得
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // スピナーが選択されたときの処理
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 一覧から１つを選んだ時の処理
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 選ばれた項目を取得してTextViewに表示
                Spinner spinner = (Spinner) parent;
                String item = (String) spinner.getSelectedItem();
                if (!item.isEmpty()) {
                    mHeight.setText(item);
                }
            }

            // 一覧で何も選択されなかった時の処理
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // シークバーの取得
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setProgress(height);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // シークバーの値が変更された
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 変更された値をintから文字列に変換してTextViewに表示
                String value = String.valueOf(progress);
                mHeight.setText(value);
            }

            // シークバーのスライド開始
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            // シークバーのスライド終了
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // ラジオボタンの取得
        RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            // ラジオグループのチェック状態が変更された時に呼び出されます
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // 選択されたラジオボタンのリソースIDより、ラジオボタンを取得する
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                String value = radioButton.getText().toString();
                mHeight.setText(value);
            }
        });
    }

    // アクティビティが非表示になると呼ばれるメソッド
    @Override
    protected void onPause() {
        super.onPause();

        // 共有プリファレンスのインスタンスを作成
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        // 共有プリファレンスに保存するためのインスタンスを取得
        SharedPreferences.Editor editor = pref.edit();

        // 身長を保存
        editor.putInt(HEIGHT, Integer.parseInt(mHeight.getText().toString()));
        editor.apply();
    }
}
