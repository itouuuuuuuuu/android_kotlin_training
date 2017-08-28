package com.example.masafumi_ito.mydiary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.masafumi_ito.mydiary.adapter.DiaryRealmAdapter;
import com.example.masafumi_ito.mydiary.model.Diary;

import io.realm.Realm;
import io.realm.RealmResults;


public class DiaryListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Realm mRealm;

    // コンストラクタ（フラグメントはコンストラクタ作成が必須かつコンストラクタに引数は渡さない）
    public DiaryListFragment() {
    }

    // フラグメントのインスタンスを作成するファクトリーメソッド
    public static DiaryListFragment newInstance() {

        // インスタンスを作成
        DiaryListFragment fragment = new DiaryListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Realm処理開始
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Realm処理終了
        mRealm.close();
    }

    // フラグメントが表示される直前に呼び出されるメソッド
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // インフレーターに表示するフラグメントを設定
        View v = inflater.inflate(R.layout.fragment_diary_list, container, false);

        // fragment上に作ったRecyclerViewを取得
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler);

        // RecyclerViewの設定（どのような表示形式にするか）
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());   // LinearLayoutで
        llm.setOrientation(LinearLayoutManager.VERTICAL);                   // 縦スクロールで
        recyclerView.setLayoutManager(llm);                                 // 設定する

        // DBからDiaryクラスを全て取得
        RealmResults<Diary> diaries = mRealm.where(Diary.class).findAll();

        // アダプターを生成
        DiaryRealmAdapter adapter = new DiaryRealmAdapter(getActivity(), diaries, true);

        // fragment上に作ったRecyclerViewにアダプターをセット
        recyclerView.setAdapter(adapter);

        return v;
    }

    // このフラグメントがアクティビティに関連付けられたときに一度だけ呼ばれるメソッド
    // context: 呼び出し元のアクティビティ
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // contextがOnFragmentInteractionListenerインターフェイスを実装している場合
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    // このフラグメントがアクティビティから切り離されたときに呼ばれるメソッド
    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    // このフラグメントを表示するアクティビティで実装するインターフェイス
    // 今回はMainActivityにフラグメントを表示するため、MainActivityで実装
    public interface OnFragmentInteractionListener {
        void onAddDiarySelected();
    }

    // onCreateの実行後に呼ばれるメソッド
    // オプションメニューはここで表示の設定を行う
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // オプションメニューの表示（onCreateOptionMenuが呼ばれる）
        setHasOptionsMenu(true);
    }

    // setHasOptionsMenu(true);することで呼ばれるメソッド
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // メニュー項目を設定
        // 第一引数：メニューXMLファイルのリソースID
        // 第二引数：項目を追加する対象
        inflater.inflate(R.menu.menu_diary_list, menu);

        // メニュー項目のidを取得
        MenuItem addDiary = menu.findItem(R.id.menu_item_add_diary);
        MenuItem deleteAll = menu.findItem(R.id.menu_item_delete_all);

        // メニュー項目の色を設定
        MyUtils.tintMenuIcon(getContext(), addDiary, android.R.color.white);
        MyUtils.tintMenuIcon(getContext(), deleteAll, android.R.color.white);
    }

    // オプションメニューの項目がタップされたときに呼ばれるメソッド
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_add_diary:
                if (mListener != null) mListener.onAddDiarySelected();
                return true;
            case R.id.menu_item_delete_all:
                final RealmResults<Diary> diaries = mRealm.where(Diary.class).findAll();
                mRealm.executeTransaction(
                        new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                diaries.deleteAllFromRealm();
                            }
                        });
                return true;
        }
        return false;
    }
}
