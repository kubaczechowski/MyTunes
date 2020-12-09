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
 * author kuba czechowski
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



    public void save(Song song) throws BLLexception {
        try {
            idalFacade.createSong(
                    song.getTitle(),
                    song.getArtist(),
                    song.getCategory(),
                    song.getPlaytime(),
                    song.getFilePath());
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't save song");
        }


    }

    public void delete(Song songToBeDeleted) throws BLLexception {
        try {
            idalFacade.deleteSong(songToBeDeleted);
        } catch (DALexception daLexception) {
           // daLexception.printStackTrace();
            throw new BLLexception("couldn't delete a song");
        }
    }
}
