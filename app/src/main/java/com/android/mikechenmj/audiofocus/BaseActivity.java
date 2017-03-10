package com.android.mikechenmj.audiofocus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by mikechenmj on 17-3-8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private String[] mMusicData = new String[2];

    protected Player mPlayerOne;

    protected Player mPlayerTwo;

    protected static final String REQUEST_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;

    protected static final int REQUEST_CODE = 93;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_layout);
        init();
    }

    private void init() {
        if (PackageManager.PERMISSION_GRANTED !=
                ActivityCompat.checkSelfPermission(this, REQUEST_PERMISSION)) {
            ActivityCompat.requestPermissions(this, new String[]{REQUEST_PERMISSION}, REQUEST_CODE);
        } else {
            getMusicPath();
            mPlayerOne = new Player(this,mMusicData[0]);
            mPlayerTwo = new Player(this,mMusicData[1]);
        }
    }

    public abstract void onClick(View view);


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_CODE:
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(BaseActivity.this, "需要先获取权限", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        getMusicPath();
                        mPlayerOne = new Player(this,mMusicData[0]);
                        mPlayerTwo = new Player(this,mMusicData[1]);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void getMusicPath() {
        String[] projection = new String[]{
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null,
                new StringBuilder(MediaStore.Audio.Media.TITLE).append(" ASC").toString());

        if (cursor.moveToFirst()) {
            mMusicData[0] = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        }
        if (cursor.moveToNext()) {
            mMusicData[1] = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        }
    }
}
