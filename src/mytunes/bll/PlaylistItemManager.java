package mytunes.bll;

import mytunes.be.PlaylistItem;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.DALcontroller;
import mytunes.dal.IDALFacade;
import mytunes.dal.exception.DALexception;

public class PlaylistItemManager {

    private IDALFacade idalFacade;


    public PlaylistItemManager() {
        idalFacade = new DALcontroller();
    }

    public void deleteSong(PlaylistItem playlistItem) throws BLLexception {
        try {
            idalFacade.deleteSong(playlistItem);
        }catch (DALexception daLexception){
            throw new BLLexception("Couldn't delete Song");
        }



    }

}
