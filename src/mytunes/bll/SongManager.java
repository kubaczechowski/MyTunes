package mytunes.bll;

import mytunes.be.Song;
import mytunes.dal.SongDAO;

import java.util.List;

public class SongManager {

    private SongDAO songDAO;

    public SongManager() {
        songDAO = new SongDAO();
    }

    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
    }
}
