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

    public Playlist getPlaylist(int id) throws BLLexception {
        try {
            return idalFacade.getPlaylist(id);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a playlist with id: " + id);
        }
    }

    public double getTotalTimeOnPlaylist(Playlist playlist) throws BLLexception {
        try {
            return idalFacade.getTotalTimeOnPlaylist(playlist);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get total time on playlist");
        }
    }



    public void updateTotalTimeOnPlaylistADD(Playlist playlist, int addedSongTime) throws BLLexception {
        //method called when adding a song
        try {
            idalFacade.updateTotalTimeOnPlaylistADD(playlist, addedSongTime);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't update playtime on playlist: " + playlist.getName());
        }
    }

    public void updateTotalTimeOnPlaylistRemove(Playlist playlist, int removedSongTime) {


    }

    public void decrementNumberOfSongsOnPlaylist(Playlist playlist) {
    }

    public void increamentNumberOfSongsOnPlaylist(Playlist playlist) throws BLLexception {
        try {
            idalFacade.incrementTheNumberOfSongsOnPlaylist(playlist);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't increament number of songs on the playlist" + playlist.getName() );

        }
    }
}
