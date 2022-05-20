package klp.com.animationdemo.media;

public class MusicBean {
    public long musicId;
    public String musicUrl;
    public int playState = MusicState.PLAY_DEFAULT;

    public MusicBean(long musicId, String musicUrl, int playState) {
        this.musicId = musicId;
        this.musicUrl = musicUrl;
        this.playState = playState;
    }

   public interface MusicState{
        int PLAY_DEFAULT = 0;
        int PLAYING = 1;
        int PLAYED = 2;
    }
}
