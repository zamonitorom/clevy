package com.bms.rabbit.tools;
// Created by Konstantin on 08.10.2018.

import android.media.MediaPlayer;

import java.io.IOException;

public class SoundPlayer {
    private boolean isPLAYING = false;
    private boolean isReady = false;
    private MediaPlayer mp = new MediaPlayer();

    public void play(String url) {
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
                            mp.start();
                        }
                    });
                    isReady = true;
                }

                mp.start();

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        isPLAYING = false;
                    }
                });
                mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
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
        isReady = false;
        mp.reset();
    }

    public void stopPlaying() {
        if(mp!=null) {
            mp.release();
            mp = null;
        }
    }
}
