package klp.com.animationdemo.media;


import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import java.util.function.Function;

public class QueueItem {
    MusicBean mSong;
    Context mContext;
    public QueueItem(Context context, MusicBean song) {
        this.mSong = song;
        mContext = context;
    }
     @RequiresApi(api = Build.VERSION_CODES.N)
     public void run(final Function function) {
        if (MediaQueueCenter.getInstance().getPositionInList() <= MediaQueueCenter.getInstance().mUserSelectedPosition
                || MediaQueueCenter.getInstance().getCurrentListPlayStated() == MusicBean.MusicState.PLAYED) {
            function.apply(this);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //发消息同步adater更新状态
                    final MediaPlayerManager manager = MediaPlayerManager.getInstance(mContext);
                    manager.setCallback(new MediaPlayerManager.Callback() {
                        @Override
                        public void next() {

                            function.apply(this);
                        }

                        @Override
                        public void connectState(boolean connected) {
                        }
                    });
                    manager.play(mSong);
                }
            });
        }
     }
}
