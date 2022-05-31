package klp.com.animationdemo.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.concurrent.CopyOnWriteArrayList;

import klp.com.animationdemo.util.SdkApplicationUtils;

public class MediaPlayerManager implements PlayService.PlayStateChangeListener {
    private static final String TAG = MediaPlayerManager.class.getSimpleName();
    private Context mContext;
    private PlayService mService;
    private MusicBean mSong;
    private int mState = PlayService.STATE_IDLE;
    private AudioFocusRequest mAudioFocusRequest;
    private CopyOnWriteArrayList<Callback> observers = new CopyOnWriteArrayList<>();

    public MediaPlayerManager registerCallback(Callback callback) {
        observers.add(callback);
        return this;
    }

    public void unRegisterCallback(Callback callback) {
        observers.remove(callback);
    }
    private MediaPlayerManager(Context context) {
        mContext = context;
    }

    private static MediaPlayerManager sManager = null;

    public synchronized static MediaPlayerManager getInstance() {
        if (sManager == null) {
            sManager = new MediaPlayerManager(SdkApplicationUtils.getApplication());
        }
        return sManager;
    }

    public void init() {
        bindPlayService();
    }

    private void bindPlayService() {
        mContext.bindService(new Intent(mContext, PlayService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindPlayService() {
        if (mService != null) {
            mContext.unbindService(mConnection);
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((PlayService.PlayBinder) service).getService();
            mService.setPlayStateChangeListener(MediaPlayerManager.this);
            Log.v(TAG, "onServiceConnected ");
            if (!isPlaying()) {
                play(mSong);
            }
//            if (mCallback != null) {
//                mCallback.connectState(true);
//            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v(TAG, "onServiceDisconnected " + name);
            mService.setPlayStateChangeListener(null);
            mService = null;
//            if (mCallback != null) {
//                mCallback.connectState(false);
//            }
        }
    };

    public void play(MusicBean song) {
        if (mService != null) {
            if (song == null && mSong == null) { //播放下一首
                observers.forEach((callback) -> callback.next(mSong));
            } else if (mSong!= null && song.musicId == mSong.musicId) {
                if (mService.isStarted()) {
                    pause();
                } else if (mService.isPaused()) {
                    resume();
                } else {
                    mService.releasePlayer();
                    if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == requestAudioFocus()) {
                        mSong = song;
                        mService.startPlayer(song.musicUrl);
                    }
                }
            } else {
                mService.releasePlayer();
                if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == requestAudioFocus()) {
                    mSong = song;
                    mService.startPlayer(song.musicUrl);
                }
            }
        }  else {
            mSong = song;
            bindPlayService();
        }

    }


    public void resume() {
        if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == requestAudioFocus()) {
            mService.resumePlayer();
        }
    }

    public boolean isPlaying() {
        return mService != null && mService.isStarted();
    }

    public boolean isPaused() {
        return mService != null && mService.isPaused();
    }

    private void pause() {
        mService.pausePlayer();
    }

    public void stop() {
        mService.stopPlayer();
    }

    public void release() {
        if (mService != null) {
            mSong = null;
            mService.releasePlayer();
            unbindPlayService();
            mService.setPlayStateChangeListener(null);
            mService = null;
        }
    }

    public MusicBean getCurrentSong() {
        return mSong;
    }


    private int requestAudioFocus() {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT < 26) {
            return audioManager.requestAudioFocus(
                    mAfListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        } else {
            mAudioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                            .build())
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(mAfListener)
                    .build();
            return audioManager.requestAudioFocus(mAudioFocusRequest);
        }

    }

    private void releaseAudioFocus() {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT > 8 && Build.VERSION.SDK_INT <= 26 && mAfListener != null) {
            audioManager.abandonAudioFocus(mAfListener);

        } else {
            if (Build.VERSION.SDK_INT > 26 && mAudioFocusRequest != null) {
                audioManager.abandonAudioFocusRequest(mAudioFocusRequest);
            }
        }

    }

    private AudioManager.OnAudioFocusChangeListener mAfListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            Log.v(TAG, "onAudioFocusChange = " + focusChange);
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                if (isPlaying()) {
                    pause();
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                if (isPaused()) {
                    resume();
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStateChanged(int state) {
        mState = state;
        switch (state) {
            case PlayService.STATE_IDLE:

                break;
            case PlayService.STATE_INITIALIZED:

                break;
            case PlayService.STATE_PREPARING:

                break;
            case PlayService.STATE_PREPARED:

                break;
            case PlayService.STATE_STARTED:
                observers.forEach((callback) -> callback.start(mSong));
                break;
            case PlayService.STATE_PAUSED:
                observers.forEach((callback) -> callback.paused(mSong));
                break;
            case PlayService.STATE_ERROR:
                releaseAudioFocus();
                break;
            case PlayService.STATE_STOPPED:
                releaseAudioFocus();
                break;
            case PlayService.STATE_COMPLETED:
                releaseAudioFocus();
                observers.forEach((callback) -> callback.next(mSong));
                break;
            case PlayService.STATE_RELEASED:
                releaseAudioFocus();
                observers.forEach((callback) -> callback.release(mSong));
                break;
        }
    }

    @Override
    public void onShutdown() {
        releaseAudioFocus();
    }

   public interface Callback {
        void next(MusicBean song);
        void start(MusicBean song);
        void paused(MusicBean song);
        void release(MusicBean song);
        void connectState(boolean connected);

    }
}
