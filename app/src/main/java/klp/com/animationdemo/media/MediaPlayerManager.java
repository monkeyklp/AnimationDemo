package klp.com.animationdemo.media;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class MediaPlayerManager {
    private MediaPlayer mediaPlayer = null;

    private boolean isProcessAudioFocus = true;

    public void setProcessAudioFocus(boolean processAudioFocus) {
        isProcessAudioFocus = processAudioFocus;
    }
    public void init(String filePath, final PlayerCallback callback) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (callback != null) {
                        callback.onError(mp, what, extra);
                    }
                    mp.reset();
                    return false;
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (callback != null) {
                        callback.onPrepared();
                    }
                }
            });
            mediaPlayer.prepareAsync();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 2022/5/16 音频焦点
    public void play() {
        if (isProcessAudioFocus) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void pause() {

    }

    public interface PlayerCallback{
        void onPrepared();

        boolean onError(MediaPlayer mp, int what, int extra);

    }


}
