package mytunes.bll;

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


        public void deleteSong(PlaylistItem playlistItem) throws BLLexception {
            try {
                idalFacade.deleteSong(playlistItem);
            }catch (DALexception daLexception) {
                daLexception.printStackTrace();
                throw new BLLexception("Couldn't delete Song");
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
}



