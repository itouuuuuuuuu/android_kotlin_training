package com.example.masafumi_ito.fragmentapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragmentを作成
        MainFragment fragment = new MainFragment();

        // fragmentマネージャーの作成
        FragmentManager manager = getSupportFragmentManager();

        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
        FragmentTransaction transaction = manager.beginTransaction();

        // 新しく追加を行うのでaddを使用します
        // 他にも、メソッドにはreplace removeがあります
        // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment, 3つ目はfragmentを識別するタグ
        transaction.add(R.id.content, MainFragment.createInstance("hoge", Color.RED), "TAG1");
        transaction.add(R.id.content, fragment, "TAG2");

        // 最後にcommitを使用することで変更を反映します
        transaction.commit();
    }
}
