/**
 * @author kjell
 */

package mytunes.bll;

import mytunes.be.Song;
import mytunes.dal.SongsDAO_DB;

import java.util.List;

public class MusicManager {
    private SongsDAO_DB songDao;

    public MusicManager() {
        songDao = new SongsDAO_DB();
    }

    /**
     * @return list of all songs from the database
     */
    public List<Song> getAllSongs() {
        return songDao.getAllSongs();
    }
}
