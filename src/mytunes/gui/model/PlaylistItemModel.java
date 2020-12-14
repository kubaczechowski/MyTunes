package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.PlaylistItem;
import mytunes.bll.PlaylistItemManager;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.PlaylistItemDAO;

public class PlaylistItemModel {

    private PlaylistItemManager playlistItemManager;
    private ObservableList<PlaylistItem> playlistItems;

    public PlaylistItemModel(){
        playlistItemManager = new PlaylistItemManager();
        playlistItems = FXCollections.observableArrayList();
    }


    public ObservableList<PlaylistItem> getPlaylistItems() {
        return playlistItems;
    }

    public void deleteSong(PlaylistItem playlistItem){
        try {
            playlistItemManager.deleteSong(playlistItem);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        playlistItems.remove(playlistItem);

    }
}
