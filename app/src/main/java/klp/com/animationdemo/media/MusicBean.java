package klp.com.animationdemo.media;

import android.annotation.SuppressLint;

import java.util.Objects;

public class MusicBean {
    public long musicId;
    public String musicUrl;
    public int playState = MusicState.PLAY_DEFAULT;

    public MusicBean(long musicId, String musicUrl, int playState) {
        this.musicId = musicId;
        this.musicUrl = musicUrl;
        this.playState = playState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicBean)) return false;
        MusicBean musicBean = (MusicBean) o;
        return musicId == musicBean.musicId;
    }

    @SuppressLint("NewApi")
    @Override
    public int hashCode() {
        return Objects.hash(musicId);
    }

    public interface MusicState{
        int PLAY_DEFAULT = 0;
        int PLAYING = 1;
        int PLAYED = 2;
    }
}
