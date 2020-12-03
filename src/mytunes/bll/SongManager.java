package mytunes.bll;

import javafx.scene.media.Media;
import javafx.util.Duration;
import mytunes.be.Song;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.SongDAO;
<<<<<<< Updated upstream

=======
import mytunes.dal.exception.DALexception;

import java.util.List;
>>>>>>> Stashed changes
import java.lang.Object;

public class SongManager {
    private SongDAO songDAO;

    public SongManager()
    {
        songDAO = new SongDAO();
    }

<<<<<<< Updated upstream
=======
    public List<Song> getAllSongs() throws BLLexception {
        try {
            return songDAO.getAllSongs();
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't get all songs");
        }
    }
>>>>>>> Stashed changes
    /**
     * Method that returns the time of the song in the seconds
     */

    public int getSongTime(String mediaStringUrl) {
        Media media = new Media(mediaStringUrl);
        int time = (int) media.getDuration().toSeconds();

        return time;


    }

<<<<<<< Updated upstream
    public void save(Song song) {
        songDAO.createSong(
                song.getTitle(),
                song.getArtist(),
                song.getCategory(),
                song.getFilePath());
=======
    public void save(Song song) throws BLLexception {
        try {
            songDAO.createSong(
                    song.getTitle(),
                    song.getArtist(),
                    song.getCategory(),
                    song.getFilePath());
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't save song");
        }

>>>>>>> Stashed changes
    }

    public void delete(Song songToBeDeleted) throws BLLexception {
        try {
            songDAO.deleteSong(songToBeDeleted);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't delete a song");
        }
    }
}
