package com.android.mikechenmj.audiofocus;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.without_audio_focus:
                startActivity(new Intent(this, NormalActivity.class));
                break;
            case R.id.with_audio_focus:
                startActivity(new Intent(this, AudioFocusActivity.class));
                break;
            default:
                break;
        }
    }
}
