package klp.com.animationdemo.media;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.RequiresApi;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class MediaQueueCenter {
    private LinkedBlockingQueue mQueue = new LinkedBlockingQueue<QueueItem>();
    private HandlerThread mHandlerThread = new HandlerThread("MediaLooper");
    private Handler mWorkHandler;
    private int MSG_EXPENSE_TASK = 1;
    private QueueItem mCurrentQueueItem;
    private Boolean isPlayRunning = false;

    private MediaQueueCenter() {}
    private static class SingletonInstance {
        private static final MediaQueueCenter instance = new MediaQueueCenter();
    }
    public static MediaQueueCenter getInstance() {
        return SingletonInstance.instance;
    }

    public void enqueue(String song) {
        mQueue.offer(song);
        if (mWorkHandler != null) {
            mWorkHandler.removeMessages(MSG_EXPENSE_TASK);
            mWorkHandler.sendEmptyMessage(MSG_EXPENSE_TASK);
        }
    }

    public void start() {
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

    public void stop() {

    }

    public void quit() {
        isPlayRunning = false;
        mQueue.clear();
        mWorkHandler.removeCallbacksAndMessages(null);
        mHandlerThread.quit();
    }




}
