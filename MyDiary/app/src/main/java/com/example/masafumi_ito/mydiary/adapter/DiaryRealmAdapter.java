package com.example.masafumi_ito.mydiary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.masafumi_ito.mydiary.MyUtils;
import com.example.masafumi_ito.mydiary.R;
import com.example.masafumi_ito.mydiary.model.Diary;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class DiaryRealmAdapter extends RealmRecyclerViewAdapter<Diary, DiaryRealmAdapter.DiaryViewHolder> {

    Context context;

    // ViewHolder（onCreateViewHolder）から使用される
    public static class DiaryViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView bodyText;
        protected TextView date;
        protected ImageView photo;

        // コンストラクタ
        public DiaryViewHolder(View itemView) {
            super(itemView);

            title    = (TextView) itemView.findViewById(R.id.title);
            bodyText = (TextView) itemView.findViewById(R.id.body);
            date     = (TextView) itemView.findViewById(R.id.date);
            photo    = (ImageView) itemView.findViewById(R.id.diary_photo);
        }
    }

    // コンストラクタ
    // data: アダプターにセットするデータ
    // autoUpdate: 自動更新のON / OFF
    public DiaryRealmAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Diary> data, boolean autoUpdate) {
        super(data, autoUpdate);

        this.context = context;
    }

    // 新しいセルを作成するするためのViewHolderを呼び出し
    @Override
    public DiaryRealmAdapter.DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // カードビューを取得
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        final DiaryViewHolder holder = new DiaryViewHolder(itemView);

        return holder;
    }

    // 行の内容を表示する前に呼び出される
    // ViewHolderに行の内容をセット
    @Override
    public void onBindViewHolder(DiaryRealmAdapter.DiaryViewHolder holder, int position) {

        // Realmオブジェクトから行に表示するオブジェクトを取得
        Diary diary = getData().get(position);

        // ViewHolderにdiaryインスタンスの内容をセット
        holder.title.setText(diary.title);
        holder.bodyText.setText(diary.bodyText);
        holder.date.setText(diary.date);
        holder.date.setText(diary.date);
        if (diary.image != null && diary.image.length != 0) {
            Bitmap bmp = MyUtils.getImageFromByte(diary.image);
            holder.photo.setImageBitmap(bmp);
        }
    }
}
