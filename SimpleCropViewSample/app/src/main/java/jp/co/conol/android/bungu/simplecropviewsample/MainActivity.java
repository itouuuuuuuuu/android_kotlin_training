package jp.co.conol.android.bungu.simplecropviewsample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.isseiaoki.simplecropview.CropImageView;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGEFILE = 1001;
    private static final int RESULT_CAMERA = 1002;
    private CropImageView mCropImageView;
    private ImageView mCroppedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCropImageView = (CropImageView)findViewById(R.id.cropImageView);
        mCroppedImageView = (ImageView)findViewById(R.id.croppedImageView);
        Button readingButton = (Button)findViewById(R.id.readingButton);
        Button cameraButton = (Button)findViewById(R.id.cameraButton);
        Button cropButton = (Button)findViewById(R.id.cropbutton);
        Button leftRotatebutton = (Button)findViewById(R.id.leftRotatebutton);
        Button rightRotatebutton = (Button)findViewById(R.id.rightRotatebutton);

        // 読み込みボタンがクリックされた時
        readingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 端末に保存されている画像を取得する
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);   // 暗黙的インテントの作成
                intent.setType("image/*");

                // インテントの開始
                startActivityForResult(
                        Intent.createChooser(intent, "画像を選択します"),
                        RESULT_PICK_IMAGEFILE); // 起動したアクティビティを特定する定数
            }
        });

        // カメラボタンがクリックされた時
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_CAMERA);
            }
        });

        // リサイズボタンがクリックされた時
        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // フレームに合わせてトリミング
                mCroppedImageView.setImageBitmap(mCropImageView.getCroppedBitmap());
            }
        });

        // 左回転ボタンがクリックされた時
        leftRotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 左回転
                mCropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
            }
        });

        // 右回転ボタンがクリックされた時
        rightRotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 右回転
                mCropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            }
        });
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // 画像を空欄にする
//        mCropImageView.setImageBitmap(null);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        // 画像読み込みからの呼び出し または カメラからの呼び出し
        if (( requestCode == RESULT_PICK_IMAGEFILE || requestCode == RESULT_CAMERA ) && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("", "Uri: " + uri.toString());

                Picasso.with(this)
                        .load(uri)
                        .fit()
                        .centerInside()
                        .into(mCropImageView);
            }
        }
    }
}
