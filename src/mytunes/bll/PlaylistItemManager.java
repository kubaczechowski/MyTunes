package mytunes.bll;

import mytunes.bll.exeption.BLLexception;
import mytunes.dal.DALcontroller;
import mytunes.dal.IDALFacade;
import mytunes.dal.exception.DALexception;

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
}
