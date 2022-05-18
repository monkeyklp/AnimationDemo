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

    public void enqueue(String song) {
        try {
            mQueue.put(song);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper()){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_EXPENSE_TASK) {
                    if (mQueue.isEmpty()) return;
                    QueueItem item = null;
                    try {
                        item = (QueueItem) mQueue.take();
                        item.run(new Function() {
                            @Override
                            public Object apply(Object o) {
                                queryNext();
                                return null;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    private void queryNext() {
        mWorkHandler.sendEmptyMessage(MSG_EXPENSE_TASK);
    }

    public void quit() {
        mQueue.clear();
        mWorkHandler.removeMessages(MSG_EXPENSE_TASK);
        mHandlerThread.quit();
    }




}
