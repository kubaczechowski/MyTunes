package mytunes.bll;

import mytunes.be.Playlist;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.DALcontroller;
import mytunes.dal.IDALFacade;
import mytunes.dal.PlaylistDAO;
import mytunes.dal.exception.DALexception;

import java.util.List;

public class PlaylistManager {

    private IDALFacade idalFacade;

    public PlaylistManager() {
        idalFacade = new DALcontroller();
    }

    public List<Playlist> getAllPlaylists() throws BLLexception {
        try {
            return idalFacade.getAllPlaylists();
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception(" Couldn't get AllPlaylists");
        }
    }

    public void deletePlaylist(Playlist playlist) throws BLLexception {
        try {
            idalFacade.deletePlaylist(playlist);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't delete playlist");
        }
    }

    public Playlist newPlaylist(String name) throws BLLexception {
        try {
            return idalFacade.createPlaylist(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BLLexception("Couldn't newPlaylist");
        }
    }

    public void updatePlaylist(String name, Playlist playlist) throws BLLexception {
        try {
            idalFacade.updatePlaylistName(playlist, name);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't updatePlaylist");
        }
    }
}
