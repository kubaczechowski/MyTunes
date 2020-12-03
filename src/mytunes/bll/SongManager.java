package mytunes.bll;


import javafx.scene.media.Media;
import javafx.util.Duration;
import mytunes.be.Song;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.SongDAO;

import java.util.List;

import mytunes.dal.exception.DALexception;



import java.lang.Object;

public class SongManager {
    private SongDAO songDAO;

    public SongManager()
    {
        songDAO = new SongDAO();
    }


    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
	}
    public List<Song> getAllSongs() throws BLLexception {
        try {
            return songDAO.getAllSongs();
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't get all songs");
        }
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
