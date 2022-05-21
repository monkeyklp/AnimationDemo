package klp.com.animationdemo.media;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class MediaQueueCenter {
    private LinkedBlockingQueue mQueue = new LinkedBlockingQueue<QueueItem>();
    private HandlerThread mHandlerThread = new HandlerThread("MediaLooper");
    private Handler mWorkHandler;
    private int MSG_EXPENSE_TASK = 1;
    private QueueItem mCurrentQueueItem;
    private Boolean isPlayRunning = false;
    public int mUserSelectedPosition = 0;
    private ArrayList<MusicBean> mAllMusicList = new ArrayList<>();

    private MediaQueueCenter() {
        init();
    }
    private static class SingletonInstance {
        private static final MediaQueueCenter instance = new MediaQueueCenter();
    }
    public static MediaQueueCenter getInstance() {
        return SingletonInstance.instance;
    }

    public void enqueue(QueueItem song, boolean needStart) {
        try {
            mQueue.put(song);
            mAllMusicList.add(song.mSong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mWorkHandler != null && needStart) {
            mWorkHandler.removeMessages(MSG_EXPENSE_TASK);
            mWorkHandler.sendEmptyMessage(MSG_EXPENSE_TASK);
        }
    }

    private void init() {
        if (mWorkHandler != null) return;
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper()){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_EXPENSE_TASK) {
                    if (mQueue.isEmpty() || isPlayRunning) return;
                    QueueItem item = null;
                    try {
                        item = (QueueItem) mQueue.take();
                        mCurrentQueueItem = item;
                        isPlayRunning = true;
                        item.run(new Function() {
                            @Override
                            public Object apply(Object o) {
                                queryNext();
                                return null;
                            }
                        });
                        mCurrentQueueItem = null;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void queryNext() {
        isPlayRunning = false;
        mWorkHandler.sendEmptyMessage(MSG_EXPENSE_TASK);
    }

    public void startLooper(int startPosition) {
        mUserSelectedPosition = startPosition;
        changeListPlayState();
        isPlayRunning = false;
        mWorkHandler.sendEmptyMessage(MSG_EXPENSE_TASK);
    }

    private void changeListPlayState() {
        if (mAllMusicList.size() >= mUserSelectedPosition) {
            mAllMusicList.get(mUserSelectedPosition).playState = MusicBean.MusicState.PLAYED;
        }
    }

    public int getCurrentListPlayStated(){
        return mAllMusicList.get(getPositionInList()).playState;
    }

    public QueueItem getCurrentQueueItem() {
        return mCurrentQueueItem;
    }

    public int getPositionInList() {
        return mAllMusicList.indexOf(mCurrentQueueItem.mSong);
    }

    public void stop() {

    }

    public void quit() {
        isPlayRunning = false;
        mQueue.clear();
        mAllMusicList.clear();
        mWorkHandler.removeCallbacksAndMessages(null);
        mHandlerThread.quit();
    }




}
