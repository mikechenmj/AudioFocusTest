package com.android.mikechenmj.audiofocus;

import android.view.View;

/**
 * Created by mikechenmj on 17-3-8.
 */

public class AudioFocusActivity extends BaseActivity {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_one:
                mPlayerOne.startMediaWithAudioFocus();
                break;
            case R.id.button_pause_one:
                mPlayerOne.pauseMediaWithAudioFocus();
                break;
            case R.id.button_start_two:
                mPlayerTwo.startMediaWithAudioFocus();
                break;
            case R.id.button_pause_two:
                mPlayerTwo.pauseMediaWithAudioFocus();
                break;
            default:
                break;
        }
    }
}
