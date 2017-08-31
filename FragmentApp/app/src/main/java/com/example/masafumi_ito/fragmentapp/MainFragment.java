package com.example.masafumi_ito.fragmentapp;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Masafumi_Ito on 2017/08/30.
 */

public class MainFragment extends Fragment {

    private TextView mTextView;

    private final static String KEY_NAME = "key_name";
    private final static String KEY_BACKGROUND = "key_background_color";

    private String mName = "";
    private @ColorInt int mBackgroundColor = Color.TRANSPARENT;

    // このメソッドからFragmentを作成することもできる（newでなく）
    public static MainFragment createInstance(String name, @ColorInt int color) {
        // Fragmentを作成して返すメソッド
        // createInstanceメソッドを使用することで、そのクラスを作成する際にどのような値が必要になるか制約を設けることができる
        MainFragment fragment = new MainFragment();

        // Fragmentに渡す値はBundleという型でやり取りする
        Bundle args = new Bundle();

        // Key/Pairの形で値をセットする
        args.putString(KEY_NAME, name);
        args.putInt(KEY_BACKGROUND, color);

        // Fragmentに値をセットする
        fragment.setArguments(args);

        return fragment;
    }

    // Fragmentで表示するViewを作成するメソッド
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Bundleの値を受け取る際はonCreateメソッド内で行う
        Bundle args = getArguments();

        // Bundleがセットされていなかった時はNullなのでNullチェックをする
        if (args != null) {

            // String型でNameの値を受け取る
            mName = args.getString(KEY_NAME);

            // int型で背景色を受け取る
            mBackgroundColor = args.getInt(KEY_BACKGROUND, Color.TRANSPARENT);
        }

        // レイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TextViewをひも付けます
        mTextView = (TextView) view.findViewById(R.id.textView);

        // 背景色をセットする
        view.setBackgroundColor(mBackgroundColor);

        // onCreateで受け取った値をセットする
        mTextView.setText(mName);

        // Buttonのクリックした時の処理を書きます
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(mTextView.getText() + "!");
            }
        });
    }
}
