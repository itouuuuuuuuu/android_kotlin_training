package com.example.masafumi_ito.mysize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // 共有プリファレンスに保存する際のキー名
    private static final String NECK = "NECK";
    private static final String SLEEVE = "SLEEVE";
    private static final String WAIST = "WAIST";
    private static final String INSEAM = "INSEAM";

    private EditText editNeck;
    private EditText editSleeve;
    private EditText editWaist;
    private EditText editInseam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 共有プリファレンスのインスタンスを取得
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        // 共有プリファレンスから保存された値を取得
        String neck = pref.getString(NECK, "");
        String sleeve = pref.getString(SLEEVE, "");
        String waist = pref.getString(WAIST, "");
        String inseam = pref.getString(INSEAM, "");

        // レイアウトからidを取得
        editNeck = (EditText) findViewById(R.id.neck);
        editSleeve = (EditText) findViewById(R.id.sleeve);
        editWaist = (EditText) findViewById(R.id.waist);
        editInseam = (EditText) findViewById(R.id.inseam);

        // レイアウトに保存されているデータを反映
        editNeck.setText(neck);
        editSleeve.setText(sleeve);
        editWaist.setText(waist);
        editInseam.setText(inseam);

        // 身長入力ボタンが押された場合
        findViewById(R.id.height_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HeightActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onSaveTapped(View v) {

        // 共有プリファレンスのインスタンスを取得
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        // 共有プリファレンスに保存するためのインスタンスを取得
        SharedPreferences.Editor editor = pref.edit();

        // エディタに入力された文字を保存
        editor.putString(NECK, editNeck.getText().toString());
        editor.putString(SLEEVE, editSleeve.getText().toString());
        editor.putString(WAIST, editWaist.getText().toString());
        editor.putString(INSEAM, editInseam.getText().toString());

        editor.apply();
    }
}
