package mytunes.bll;

import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.DALcontroller;
import mytunes.dal.IDALFacade;
import mytunes.dal.exception.DALexception;

import java.util.List;

/**
 * author kuba czechowski
 */
public class BLLcontroller implements BLLFacade{

    private final IDALFacade idalFacade;

    public BLLcontroller() {
        idalFacade = new DALcontroller();
    }

    @Override
    public List<Playlist> getAllPlaylists() throws BLLexception {
        try {
            idalFacade.getAllPlaylists();
        }
        catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("Couldn't get all Playlists", daLexception);
        }
        return null;
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws BLLexception {
        try {
            idalFacade.deletePlaylist(playlist);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't delete playlist", daLexception);
        }

    }

    @Override
    public Playlist newPlaylist(String name) throws BLLexception {
        try {
            idalFacade.createPlaylist(name);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't create new playlist", daLexception);
        }
        return null;
    }

    @Override
    public List<Song> getAllSongs() throws BLLexception {
        try {
            idalFacade.getAllSongs();
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't get all songs", daLexception);
        }
        return null;
    }

    @Override
    public int getSongTime(String mediaStringUrl) {
        return 0;
    }

    @Override
    public void save(Song song) throws BLLexception {

    }


    @Override
    public void delete(Song songToBeDeleted) throws BLLexception {
        try {
            idalFacade.deleteSong(songToBeDeleted);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't delete song", daLexception);
        }
    }

    @Override
    public void updatePlaylist(String name, Playlist playlist) throws BLLexception {
        try {
            idalFacade.updatePlaylistName(playlist, name);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't update Playlist", daLexception);
        }

    }
}
