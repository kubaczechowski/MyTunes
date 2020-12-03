package mytunes.bll;

import mytunes.be.Playlist;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.PlaylistDAO;
import mytunes.dal.exception.DALexception;

import java.util.List;

public class PlaylistManager {

    private PlaylistDAO playlistDAO;

    public PlaylistManager() {
        playlistDAO = new PlaylistDAO();
    }

    public List<Playlist> getAllPlaylists() throws BLLexception {
        try {
            return playlistDAO.getAllPlaylists();
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception(" Couldn't get AllPlaylists");
        }
    }

    public void deletePlaylist(Playlist playlist) throws BLLexception {
        try {
            playlistDAO.deletePlaylist(playlist);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't delete playlist");
        }
    }

    public Playlist newPlaylist(String name) throws BLLexception {
        try {
            return playlistDAO.createPlaylist(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BLLexception("Couldn't newPlaylist");
        }
    }

    public void updatePlaylist(String name, Playlist playlist) throws BLLexception {
        try {
            playlistDAO.updatePlaylistName(playlist, name);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't updatePlaylist");
        }
    }
}
