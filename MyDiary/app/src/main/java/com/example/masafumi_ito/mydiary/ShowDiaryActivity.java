package com.example.masafumi_ito.mydiary;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.masafumi_ito.mydiary.model.Diary;

import io.realm.Realm;

public class ShowDiaryActivity extends AppCompatActivity {

    public static final String DIARY_ID = "DIARY_ID";
    private static final long ERR_CD = -1;
    private String mBodyText;
    private Realm mRealm;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FABの取得
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // FABをタップした時の動作
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 暗黙的インテントの作成
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                // EXTRA_TEXTキーで、本文を送信
                shareIntent.putExtra(Intent.EXTRA_TEXT, mBodyText);
                shareIntent.setType("text/plain");

                // インテントの開始
                startActivity(shareIntent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Realm開始
        mRealm = Realm.getDefaultInstance();

        // このアクティビティを起動したインテントの取得（アダプターに起動処理）
        Intent intent = getIntent();

        // DiaryのIDを取得（エラーならERR_CDを返す）
        final long diaryId = intent.getLongExtra(DIARY_ID, ERR_CD);

        // レイアウトのidの取得
        TextView body = (TextView) findViewById(R.id.body);
        ImageView imageView = (ImageView) findViewById(R.id.toolbar_image);
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroll_view);

        // 日記データの取得
        Diary diary = mRealm.where(Diary.class).equalTo("id", diaryId).findFirst();

        // ツールバーの取得（スクロールに酔って折りたたまれるツールバー）
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        // ツールバーにタイトルを設定
        layout.setTitle(diary.title);

        // mBodyTextに日記の本文を格納（setOnClickListenerで使用）
        mBodyText = diary.bodyText;

        // 取得した日記の本文を画面に表示
        body.setText(diary.bodyText);

        // 画像をbyte配列に格納
        byte[] bytes = diary.image;

        // 画像が格納できている場合
        if (bytes != null && bytes.length > 0) {

            // byte配列をBitmapに変換
            mBitmap = MyUtils.getImageFromByte(bytes);

            // 画像をセット
            imageView.setImageBitmap(mBitmap);

            // Paletteにより画像から色を取得・設定
            Palette palette = Palette.from(mBitmap).generate();
            int titleColor = palette.getLightVibrantColor(Color.WHITE); // 引数は取得できなかった時の色
            int bodyColor = palette.getDarkMutedColor(Color.BLACK);
            int scrimColor = palette.getMutedColor(Color.DKGRAY);
            int iconColor = palette.getLightMutedColor(Color.LTGRAY);

            // 各色を設定
            layout.setExpandedTitleColor(titleColor);
            layout.setContentScrimColor(scrimColor);
            scrollView.setBackgroundColor(bodyColor);
            body.setTextColor(titleColor);
            fab.setBackgroundTintList(ColorStateList.valueOf(iconColor));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
