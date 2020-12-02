package mytunes.bll;

import javafx.scene.media.Media;
import javafx.util.Duration;
import mytunes.be.Song;
import mytunes.dal.SongDAO;

import java.lang.Object;

public class SongManager {
    private SongDAO songDAO;

    public SongManager()
    {
        songDAO = new SongDAO();
    }

    /**
     * Method that returns the time of the song in the seconds
     */

    public int getSongTime(String mediaStringUrl) {
        Media media = new Media(mediaStringUrl);
        int time = (int) media.getDuration().toSeconds();

        return time;


    }

    public void save(Song song) {
        songDAO.createSong(
                song.getTitle(),
                song.getArtist(),
                song.getCategory(),
                song.getFilePath());
    }
}
