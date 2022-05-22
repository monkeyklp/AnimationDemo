package klp.com.animationdemo.media;


import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import java.util.function.Function;

public class QueueItem implements MediaPlayerManager.Callback{
    MusicBean mSong;
    Function mFunction;
    public QueueItem(MusicBean song) {
        this.mSong = song;
    }
     @RequiresApi(api = Build.VERSION_CODES.N)
     public void run(final Function function) {
         mFunction = function;
        if (MediaQueueCenter.getInstance().getPositionInList() <= MediaQueueCenter.getInstance().mUserSelectedPosition
                || MediaQueueCenter.getInstance().getCurrentListPlayStated() == MusicBean.MusicState.PLAYED) {
            function.apply(this);
        } else {
            new Handler(Looper.getMainLooper()).post(() -> {
                //发消息同步adater更新状态
               MediaPlayerManager.getInstance().registerCallback(this).play(mSong);
            });
        }
     }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void next(MusicBean song) {
        MediaPlayerManager.getInstance().unRegisterCallback(this);
        mFunction.apply(this);
    }

    @Override
    public void start(MusicBean song) {

    }

    @Override
    public void paused(MusicBean song) {

    }

    @Override
    public void connectState(boolean connected) {

    }
}
