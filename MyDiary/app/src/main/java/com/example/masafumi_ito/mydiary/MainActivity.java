package com.example.masafumi_ito.mydiary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.masafumi_ito.mydiary.model.Diary;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements DiaryListFragment.OnFragmentInteractionListener {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Realmを開始する
        mRealm = Realm.getDefaultInstance();

//        createTestData();
        showDiaryList();
    }

    // テスト用のデータをDBに追加
    private void createTestData() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // idフィールドの最大値を取得
                Number maxId = mRealm.where(Diary.class).max("id");
                long nextId = 0;
                if (maxId != null) nextId = maxId.longValue() + 1;

                // createObjectではIDを渡してオブジェクトを生成する
                Diary diary = realm.createObject(Diary.class, new Long(nextId));
                diary.title = "テストタイトル";
                diary.bodyText = "テスト本文です。";
                diary.date = "Feb 22";
            }
        });
    }

    // DiaryListフラグメントを表示する
    private void showDiaryList() {

        // Fragmentマネージャーの取得（fragmentを操作するクラスのインスタンス）
        FragmentManager manager = getSupportFragmentManager();

        // DiaryListFragmentが作成されているかを確認
        Fragment fragment = manager.findFragmentByTag("DiaryListFragment");

        // DiaryListFragmentが作成されていない場合は作成する
        if (fragment == null) {

            // fragmentの作成
            fragment = new DiaryListFragment();

            // fragmentに対する処理の開始
            FragmentTransaction transaction = manager.beginTransaction();

            // contentにfragmentを"DiaryListFragment"というタグを付けて設定
            transaction.add(R.id.content, fragment, "DiaryListFragment");

            // fragmentへの処理を反映して処理終了
            transaction.commit();
        }
    }

    // DiaryListFragmentのインターフェイスのメソッドをオーバーライド
    // 日記の追加が押された場合に実行されるメソッド
    @Override
    public void onAddDiarySelected() {

        // Realmトランザクションの開始
        mRealm.beginTransaction();

        // idの最大値を取得
        Number maxId = mRealm.where(Diary.class).max("id");
        long nextId = 0;

        if (maxId != null) nextId = maxId.longValue() + 1;

        // diaryオブジェクトの生成
        Diary diary = mRealm.createObject(Diary.class, new Long(nextId));

        diary.date = new SimpleDateFormat("MMM d", Locale.US).format(new Date());

        // Realmトランザクションの終了
        mRealm.commitTransaction();

        // InputDiaryFragmentフラグメントを作成
        InputDiaryFragment inputDiaryFragment = InputDiaryFragment.newInstance(nextId);

        // Fragmentマネージャーの取得（fragmentを操作するクラスのインスタンス）
        FragmentManager manager = getSupportFragmentManager();

        // fragmentに対する処理の開始
        FragmentTransaction transaction = manager.beginTransaction();

        // contentにfragmentを"InputDiaryFragment"というタグを付けて設定
        // replaceはaddと異なり、すでにフラグメントが存在する場合は、それを削除してから追加してくれる
        transaction.replace(R.id.content, inputDiaryFragment, "InputDiaryFragment");

        // 前のフラグメント（日記一覧画面）に戻れるように設定
        // 引数：状態の名前
        transaction.addToBackStack(null);

        // fragmentへの処理を反映して処理終了
        transaction.commit();
    }

}
