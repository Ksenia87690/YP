package com.example.yp.ui.notifications;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.yp.R;

public class SoundClick {

    public static void soundClick(Context context) {
        MediaPlayer click = MediaPlayer.create(context, R.raw.tuk);
        if (click.isPlaying()) {
            click.pause();
            click.seekTo(0);
            click.setLooping(false);
        }
        click.setVolume(0.5f, 0.5f);
        click.start();
    }
}
