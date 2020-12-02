package mytunes.gui.model;

import mytunes.be.Song;
import mytunes.bll.SongManager;

import java.net.URL;

public class SongModel {
    private SongManager songManager;

    public SongModel()
    {
        this.songManager = new SongManager();
    }

    public int getSongTime(String mediaStringUrl) {
       return songManager.getSongTime(mediaStringUrl);

    }

    public void save(Song song) {
        songManager.save(song);
    }
}
