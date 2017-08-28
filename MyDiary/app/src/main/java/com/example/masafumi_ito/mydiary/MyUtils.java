package com.example.masafumi_ito.mydiary;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyUtils {

    // byte配列をBitmapクラスのインスタンスに変換
    public static Bitmap getImageFromByte(byte[] bytes) {
        BitmapFactory.Options opt = new BitmapFactory.Options();

        // Bitmapをメモリ上に展開せずに画像の情報のみ取得する用に設定
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);

        int bitmapSize = 1;
        if ((opt.outHeight * opt.outWidth) > 500000) {
            double outSize = (double) (opt.outHeight * opt.outWidth) / 500000;
            bitmapSize = (int) (Math.sqrt(outSize) + 1);
        }
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = bitmapSize;
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
        return bmp;
    }

    // Bitmapをbyte配列に変換（realmに保存できるように）
    public static byte[] getByteFromImage(Bitmap bmp) {

        // ファイル出力用のストリーム
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Bitmapクラスのインスタンスを形式と画質を指定して圧縮
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

        // ストリームをbyte配列に変換
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    public static Bitmap getImageFromStream(ContentResolver resolver, Uri uri)
            throws IOException {

        InputStream in;
        BitmapFactory.Options opt = new BitmapFactory.Options();

        // Bitmapをメモリ上に展開せずに画像の情報のみ取得する用に設定
        opt.inJustDecodeBounds = true;

        // URIからストリームとして画像を取得
        in = resolver.openInputStream(uri);

        // ストリームをBitmapに変換
        BitmapFactory.decodeStream(in, null, opt);

        in.close();

        int bitmapSize = 1;
        if ((opt.outHeight * opt.outWidth) > 500000) {
            double outSize = (double) (opt.outHeight * opt.outWidth) / 500000;
            bitmapSize = (int) (Math.sqrt(outSize) + 1);
        }
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = bitmapSize;
        in = resolver.openInputStream(uri);
        Bitmap bmp = BitmapFactory.decodeStream(in, null, opt);
        in.close();
        return bmp;
    }

    // オプションメニュー項目の色を設定
    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {

        // アイコンを取得
        Drawable normalDrawable = item.getIcon();

        // アイコンに色付けできるようにラップする
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);

        // Drawableに色付け
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, color));

        // 色付けしたアイコンを設定
        item.setIcon(wrapDrawable);
    }
}
