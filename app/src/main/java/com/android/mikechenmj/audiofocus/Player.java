package com.android.mikechenmj.audiofocus;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by mikechenmj on 17-3-6.
 */


public class Player {

    private Context mContext;

    private String mMusicPath;

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private MyAudioFocusChangeListener mFocusChangeListener;

    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.start();
        }
    };

    public Player(Context context, String path) {
        mContext = context;
        mMusicPath = path;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mFocusChangeListener = new MyAudioFocusChangeListener();
        initPlayer();
    }

    private void initPlayer() {
        Log.e("MCJ", "initPlayer");
        mMediaPlayer = new MediaPlayer();
        prepareMediaPlayer();
    }

    private void prepareMediaPlayer() {
        Log.e("MCJ", "prepareMediaPlayer");
        try {
            mMediaPlayer.setDataSource(mMusicPath);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startMedia() {
        mMediaPlayer.start();
    }

    public void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    public void startMediaWithAudioFocus() {
        if (mMediaPlayer == null) {
            initPlayer();
        }
        int result = mAudioManager.requestAudioFocus(mFocusChangeListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mMediaPlayer.start();
        }
    }

    public void pauseMediaWithAudioFocus() {
        mAudioManager.abandonAudioFocus(mFocusChangeListener);
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }


    class MyAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {

        private int mPreviousState;

        private boolean mShouldStart = true;

        @Override
        public void onAudioFocusChange(int focusChange) {
            Log.e("MCJ", "focusChange: " + focusChange);
            Log.e("MCJ", "mPreviousState: " + mPreviousState);
            handlerAudioFocus(focusChange);
            mPreviousState = focusChange;
        }

        private void handlerAudioFocus(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    handlerAudioFocusGain();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mMediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
//                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mOriginalVol / 2,
//                            AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    mMediaPlayer.setVolume(0.5f,0.5f);
                    break;
            }
        }

        private void handlerAudioFocusGain() {
            switch (mPreviousState) {
                case AudioManager.AUDIOFOCUS_LOSS:
                    initPlayer();
                    mShouldStart = false;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mShouldStart) {
                        mMediaPlayer.start();
                    } else {
                        mShouldStart = true;
                    }
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mMediaPlayer.setVolume(1,1);
                    break;
                default:
            }
        }
    }
}
