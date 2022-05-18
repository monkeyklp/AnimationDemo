package klp.com.animationdemo.media;


import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import java.util.function.Function;

public class QueueItem {
    String mSong;
    Context mContext;
    public QueueItem(Context context, String song) {
        this.mSong = song;
        mContext = context;
    }
     public void run(final Function function) {
         new Handler(Looper.getMainLooper()).post(new Runnable() {
             @Override
             public void run() {
                 final MediaPlayerManager manager = MediaPlayerManager.getInstance(mContext);
                 manager.setCallback(new MediaPlayerManager.Callback() {
                     @RequiresApi(api = Build.VERSION_CODES.N)
                     @Override
                     public void next() {
                         function.apply(this);
                     }

                     @Override
                     public void connectState(boolean connected) {
                         if (connected) {

                         }
                     }
                 });

                 manager.play(mSong);
             }
         });
     }
}
