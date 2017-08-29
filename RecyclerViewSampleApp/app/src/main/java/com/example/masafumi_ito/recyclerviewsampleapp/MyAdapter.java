package com.example.masafumi_ito.recyclerviewsampleapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masafumi_Ito on 2017/08/29.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> dataArray = new ArrayList<String>();;

    // ViewHolderの作成
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        // コンストラクタ
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.textView);
        }
    }

    // コンストラクタ
    public MyAdapter(List<String> dataset) {
        dataArray = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(dataArray.get(position));
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }
}
