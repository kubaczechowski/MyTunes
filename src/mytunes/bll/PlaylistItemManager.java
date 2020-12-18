package mytunes.bll;


import mytunes.be.Playlist;
import mytunes.be.PlaylistItem;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.DALcontroller;
import mytunes.dal.IDALFacade;
import mytunes.dal.exception.DALexception;

import java.util.List;


public class PlaylistItemManager {
    private IDALFacade idalFacade;

    public PlaylistItemManager() {
        idalFacade = new DALcontroller();
    }


    public void add(int songId, int playlistId) throws BLLexception {
        try {
            idalFacade.createPlaylistItem(songId, playlistId);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't add new playlist item");
        }

    }

    public void remove(int songId, int playlistId) throws BLLexception {
        try {
            idalFacade.deletePlaylistItem(songId, playlistId);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("couldn't delete playlistItem");
        }
    }
        public List<PlaylistItem> getAllPlaylistItems() throws BLLexception{
            try {
                return idalFacade.getAllPlaylistItems();
            }catch (DALexception daLexception){
                daLexception.printStackTrace();
                throw new BLLexception( "Couldn't get AllPlaylistItem");
            }

        }

    public void safePlaylistDelete(Playlist playlist) throws DALexception {
            idalFacade.safePlaylistDelete(playlist);
    }
}



