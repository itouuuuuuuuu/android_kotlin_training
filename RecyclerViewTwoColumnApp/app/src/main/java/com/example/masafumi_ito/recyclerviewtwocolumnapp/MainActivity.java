package com.example.masafumi_ito.recyclerviewtwocolumnapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // idの取得
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // リスト項目のサイズが固定の場合はtureにすると処理速度アップ！
        recyclerView.setHasFixedSize(true);

        // RecyclerViewをLinearLayoutで表示
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // RecyclerViewをGridLayoutで表示
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        // RecyclerViewをStaggeredGridLayoutで表示
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));

        // 表示するデータを作成
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 30 ; i++ ) {
            list.add("item" + String.valueOf(i));
        }

        // アダプターをセット
        recyclerView.setAdapter(new RecyclerAdapter(list));
    }

    // アダプター
    private static final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private List<String> mItemList = new ArrayList<>();

        // ViewHolder（Viewへの参照を保存しておく）
        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;

            // ViewHolderのコンストラクタ
            private ViewHolder(View v) {
                super(v);

                // この時点ではitem_nameの参照がmTextViewにセットされるのみで、
                // 具体的な内容はセットされていない（具体的な内容はonBindViewHolderでセット）
                mTextView = (TextView) v.findViewById(R.id.item_name);
            }
        }

        // コンストラクタ
        private RecyclerAdapter (final List itemList) {
            mItemList = itemList;
        }

        // ViewHolder作成
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

            return new ViewHolder(view);
        }

        // ViewHolderから、画面に表示する内容をセット
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            // リストのどのデータを表示するかをセット
            holder.mTextView.setText(mItemList.get(position));
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }


    }
}
