package mytunes.bll;


import javafx.scene.media.Media;
import javafx.util.Duration;
import mytunes.be.Song;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.DALcontroller;
import mytunes.dal.IDALFacade;
import mytunes.dal.SongDAO;

import java.util.List;

import mytunes.dal.exception.DALexception;



import java.lang.Object;

/**
 * Class is a kind of connector between methods in GUI
 * and DAO.
 */

public class SongManager {
    private IDALFacade idalFacade;

    public SongManager()
    {
        idalFacade = new DALcontroller();
    }

    public List<Song> getAllSongs() throws BLLexception {

        try {
            return idalFacade.getAllSongs();
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't get all songs", daLexception);
        }

    }
    /**
     * Method that returns the time of the song in the seconds
     */

    public int getSongTime(String mediaStringUrl) {
       return idalFacade.getSongTime(mediaStringUrl);
    }


    /**
     * method is called if new song is being created
     * it calls DAO facade
     * @param song
     * @throws BLLexception
     */
    public void save(Song song) throws BLLexception {
        try {
            idalFacade.createSong(
                    song.getTitle(),
                    song.getArtist(),
                    song.getCategory(),
                    song.getPlaytime(),
                    song.getFilePath(),
                    song.getImagePath());
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't save song");
        }
    }

    /**
     * method is called if song is to be deleted
     * @param songToBeDeleted
     * @throws BLLexception
     */
    public void delete(Song songToBeDeleted) throws BLLexception {
        try {
            idalFacade.deleteSong(songToBeDeleted);
        } catch (DALexception daLexception) {
           daLexception.printStackTrace();
            throw new BLLexception("couldn't delete a song");
        }
    }

    /**
     * if some properties of the song have been changed it u calls
     * the DAO layer to update them
     * @param song
     * @throws BLLexception
     */
    public void update(Song song) throws BLLexception {
        try {
            idalFacade.updateSong(song, song.getTitle(), song.getArtist(),
                    song.getCategory(), song.getFilePath(), song.getImagePath());
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't update a song");
        }
    }

    public void safeSongDelete(Song song) throws DALexception{
        idalFacade.safeSongDelete(song);
    }
}
