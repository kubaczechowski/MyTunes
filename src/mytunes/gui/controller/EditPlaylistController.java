package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.PlaylistModel;

public class EditPlaylistController {
    public TextField txtField;

    private PlaylistModel playlistModel;
    private Playlist editingPlaylist;
    private boolean newSong;

    /**
     * true if new
     * false if edit
     */
    public void newOrEdit(boolean bool, PlaylistModel playlistModel, Playlist playlist) {
        this.playlistModel = playlistModel;
        this.editingPlaylist = playlist;
        this.newSong = bool;
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage s = (Stage) txtField.getScene().getWindow();
        s.close();
    }

    public void saveAction(ActionEvent actionEvent) {
        if(newSong) {
            playlistModel.newPlaylist(txtField.getText());
        }
        else {
            playlistModel.updatePlaylist(txtField.getText(), editingPlaylist);
        }

        Stage s = (Stage) txtField.getScene().getWindow();
        s.close();

    }
}
