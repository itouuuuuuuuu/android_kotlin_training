package com.example.masafumi_ito.mydiary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.masafumi_ito.mydiary.model.Diary;

import java.io.IOException;

import io.realm.Realm;

import static android.app.Activity.RESULT_OK;


public class InputDiaryFragment extends Fragment {

    private static final String DIARY_ID = "DIARY_ID";
    private static final int REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private long mDiaryId;
    private Realm mRealm;
    private EditText mTitleEdit;
    private EditText mBodyEdit;
    private ImageView mDiaryImage;

    // フラグメントのインスタンスを作成するファクトリーメソッド
    public static InputDiaryFragment newInstance(long diaryId) {

        // インスタンスを作成
        InputDiaryFragment fragment = new InputDiaryFragment();

        // フラグメントの状態を保存するためにBundleクラスのインスタンスを作成
        Bundle args = new Bundle();

        // DiaryのIDをフラグメントに保存
        args.putLong(DIARY_ID, diaryId);
        fragment.setArguments(args);

        // DiaryのIDが保存されたフラグメントを返す
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDiaryId = getArguments().getLong(DIARY_ID);
        }

        // Realm開始
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Realm終了
        mRealm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // インフレーターに表示するフラグメントを設定
        View v = inflater.inflate(R.layout.fragment_input_diary, container, false);

        // フラグメント上のレイアウトを取得
        mTitleEdit = (EditText) v.findViewById(R.id.title);
        mBodyEdit = (EditText) v.findViewById(R.id.bodyEditText);
        mDiaryImage = (ImageView) v.findViewById(R.id.diary_photo);

        // 画像がタップされた時の処理
        mDiaryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 権限の確認ウィンドウを表示
                requestReadStorage(view);
            }
        });

        // タイトルのテキストが変更された時の処理
        mTitleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {

                // DBに保存
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Diary diary = realm.where(Diary.class).equalTo("id", mDiaryId).findFirst();

                        diary.title = s.toString();
                    }
                });
            }
        });

        // 本文のテキストが変更された時の処理
        mBodyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {

                // DBに保存
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Diary diary = realm.where(Diary.class).equalTo("id", mDiaryId).findFirst();
                        diary.bodyText = s.toString();
                    }
                });
            }
        });
        return v;
    }

    // 権限の確認ウィンドウを表示
    private void requestReadStorage(View view) {

        // パーミッションが許可されていない場合
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            // パーミッションの許可をしないと選択したことがある場合
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // パーミッションの許可を求める理由を表示
                Snackbar.make(view, R.string.rationale, Snackbar.LENGTH_LONG).show();
            }

            // パーミッションの許可を行うダイアログを表示
            // （許可or許可しない で onRequestPermissionsResultが呼ばれる）
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        // パーミッションが許可されている場合
        else {

            // 端末に保存されている画像を取得する
            pickImage();
        }
    }

    // 端末に保存されている画像を取得するメソッド
    private void pickImage() {

        // 暗黙的インテントの作成
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        // インテントの開始
        startActivityForResult(
                Intent.createChooser(intent, getString(R.string.pick_image)),
                REQUEST_CODE); // 起動したアクティビティを特定する定数
    }

    // 呼び出し先のアクティビティが終了したときに呼ばれるメソッド（今回は画像が選択し終わった時）
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // リクエストコードが一致し、結果が正しかった場合
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            // 画像の場所を示すURIを取得
            Uri uri = (data == null) ? null : data.getData();

            if (uri != null) {
                try {

                    // URIからBmpイメージを作成
                    Bitmap img = MyUtils.getImageFromStream(getActivity().getContentResolver(), uri);

                    // Bmpイメージを画面に表示
                    mDiaryImage.setImageBitmap(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 画像をDBに保存
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        // Diaryインスタンスの取得
                        Diary diary = realm.where(Diary.class).equalTo("id", mDiaryId).findFirst();

                        // 画像の取得
                        BitmapDrawable bitmap = (BitmapDrawable) mDiaryImage.getDrawable();

                        // DB保存用にbyte配列に変換
                        byte[] bytes = MyUtils.getByteFromImage(bitmap.getBitmap());

                        if (bytes != null && bytes.length > 0) {
                            diary.image = bytes;
                        }

                    }
                });
            }
        }
    }

    // ユーザーがパーミッションの許可or許可しないをタップしたときに呼ばれるメソッド
    // requestCode： requestPermissionsで第二引数に渡した定数
    // permissions： requestPermissionsの第一引数で渡した文字列
    // grantResults： 許可or許可しないの結果
    //               許可： PERMISSION_GRANTED / 許可しない:PERMISSION_DENIED
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {

            // パーミッションを許可しない場合
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mDiaryImage, R.string.permission_deny, Snackbar.LENGTH_LONG).show();
            }
            // パーミッションを許可した場合
            else {
                pickImage();
            }
        }
    }


}
