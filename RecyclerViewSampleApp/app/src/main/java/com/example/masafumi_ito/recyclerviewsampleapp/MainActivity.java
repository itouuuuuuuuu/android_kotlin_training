package com.example.masafumi_ito.recyclerviewsampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<String> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // レイアウトのidを取得
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        // アイテムは固定サイズ
        recyclerView.setHasFixedSize(true);

        // LinearLayoutを使用
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 表示するデータを作成
        dataset = new ArrayList<String>();
        for(int i = 0; i < 20; i++) {
            String str = "Data_0" + String.valueOf(i);
            dataset.add(str);
        }

        // アダプターに接続
        adapter = new MyAdapter(dataset);
        recyclerView.setAdapter(adapter);

        // 項目の長押し＋上下移動で項目移動
        // 項目の左右スワイプで項目削除
        ItemTouchHelper mIth  = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        adapter.notifyItemMoved(fromPos, toPos);
                        return true; // true if moved, false otherwise
                    }

                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        dataset.remove(fromPos);
                        adapter.notifyItemRemoved(fromPos);
                    }
                });
        mIth.attachToRecyclerView(recyclerView);
    }
}
