package com.bms.rabbit.tools;
// Created by Konstantin on 08.10.2018.

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class SoundPlayer {
    private boolean isPLAYING = false;
    private boolean isReady = false;
    private MediaPlayer mp = new MediaPlayer();

    public void play(String url) {
        Log.d("SoundPlayer", url);
        if (mp == null) {
            mp = new MediaPlayer();
        }
        if (!isPLAYING) {
            isPLAYING = true;

            try {
                if (!isReady) {
                    mp.setDataSource(url);
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Log.d("SoundPlayer", "onPrepared");
                            mp.start();
                            isReady = true;
                        }
                    });

                }

                if (isReady) {
                    mp.start();
                }


                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Log.d("SoundPlayer", "onPrepared");
                        isPLAYING = false;
                    }
                });
                mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.d("SoundPlayer", "onError");
                        reset();
                        return false;
                    }
                });
            } catch (IOException e) {
//                Log.e(LOG_TAG, "prepare() failed");
            }
        } else {
            isPLAYING = false;
//            stopPlaying();
        }
    }

    public void reset() {
        Log.d("SoundPlayer", "reset");
        isReady = false;
        if (mp != null) {
            mp.reset();
        }
    }

    public void stopPlaying() {
        Log.d("SoundPlayer", "stopPlaying");
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }
}
