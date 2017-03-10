package com.android.mikechenmj.audiofocus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by mikechenmj on 17-3-8.
 */

public class NormalActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_one:
                mPlayerOne.startMedia();
                break;
            case R.id.button_pause_one:
                mPlayerOne.pauseMedia();
                break;
            case R.id.button_start_two:
                mPlayerTwo.startMedia();
                break;
            case R.id.button_pause_two:
                mPlayerTwo.pauseMedia();
                break;
            default:
                break;
        }
    }
}
