package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.PlaylistModel;

/**
 * Class responsible for creating a new playlist or
 * editing an existing one
 * @author kuba
 */
public class EditPlaylistController {
    private  Controller mainCtrl;
    public TextField txtField;

    private PlaylistModel playlistModel;
    private Playlist sentPlaylist;
    //private boolean newSong;

    public EditPlaylistController()
    {
        playlistModel = PlaylistModel.createOrGetInstance();
    }


    /**
     * method sets the playlist that should
     * @param selectedItem
     */
    public void sendPlaylist(Playlist selectedItem) {
        sentPlaylist = selectedItem;
    }


    public void closeWindow(ActionEvent actionEvent) {
        sentPlaylist=null;
        closeStage();
    }

    private void closeStage()
    {
        Stage s = (Stage) txtField.getScene().getWindow();
        s.close();
    }

    public void saveAction(ActionEvent actionEvent) {
        if(sentPlaylist!=null)
        { playlistModel.updatePlaylist(txtField.getText(), sentPlaylist);
            playlistModel.load();
        }
        else
            playlistModel.newPlaylist(txtField.getText());


        sentPlaylist=null;
        closeStage();
    }


}
