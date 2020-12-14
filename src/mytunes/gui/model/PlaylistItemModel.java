package mytunes.gui.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.PlaylistItem;
import mytunes.bll.PlaylistItemManager;
import mytunes.bll.exeption.BLLexception;

public class PlaylistItemModel {

    private PlaylistItemManager playlistItemManager;
    private ObservableList PlaylistItem;


    public PlaylistItemModel(){
        playlistItemManager = new PlaylistItemManager();


    }

    public ObservableList<PlaylistItem> getPlaylistItems(){
        return getPlaylistItems();
    }


    public void deleteSong(PlaylistItem playlistItem){
        try {
            playlistItemManager.deleteSong(playlistItem);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        getPlaylistItems().remove(playlistItem);




    }
}
