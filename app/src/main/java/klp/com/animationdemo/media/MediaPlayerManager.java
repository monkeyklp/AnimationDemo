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

public class MediaPlayerManager implements PlayService.PlayStateChangeListener {
    private static final String TAG = MediaPlayerManager.class.getSimpleName();
    private Context mContext;
    private PlayService mService;
    private MusicBean mSong;
    private int mState = PlayService.STATE_IDLE;
    private AudioFocusRequest mAudioFocusRequest;

    private Callback mCallback;

    public MediaPlayerManager setCallback(Callback mCallback) {
        this.mCallback = mCallback;
        return this;
    }

    private MediaPlayerManager(Context context) {
        mContext = context;
    }

    private static MediaPlayerManager sManager = null;

    public synchronized static MediaPlayerManager getInstance(Context context) {
        if (sManager == null) {
            sManager = new MediaPlayerManager(context.getApplicationContext());
        }
        return sManager;
    }

    public void init() {
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
            if (mCallback != null) {
                mCallback.connectState(true);
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v(TAG, "onServiceDisconnected " + name);
            mService.setPlayStateChangeListener(null);
            mService = null;
            if (mCallback != null) {
                mCallback.connectState(false);
            }
        }
    };

    public void play(MusicBean song) {
        if (mService != null) {
            if (song == null && mSong == null) { //播放下一首
                if (mCallback != null) {
                    mCallback.next();
                }
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
        mSong = null;
        mService.releasePlayer();
        unbindPlayService();
        mService.setPlayStateChangeListener(null);
        mService = null;
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

                break;
            case PlayService.STATE_PAUSED:

                break;
            case PlayService.STATE_ERROR:
                releaseAudioFocus();
                break;
            case PlayService.STATE_STOPPED:
                releaseAudioFocus();
                break;
            case PlayService.STATE_COMPLETED:
                releaseAudioFocus();
                if (mCallback != null) {
                    mCallback.next();
                }
                break;
            case PlayService.STATE_RELEASED:
                releaseAudioFocus();
                break;
        }
    }

    @Override
    public void onShutdown() {
        releaseAudioFocus();
    }

   public interface Callback {
        void next();
        void connectState(boolean connected);
    }
}
