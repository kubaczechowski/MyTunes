package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;

public class DeleteSongPrompt {
    private SongModel songModel;

    public DeleteSongPrompt()
    {
        songModel = SongModel.createOrGetInstance();
    }

    private Song songToBeDeleted;

    /**
     * the selected Song is passed to model and then after some steps
     * deleted from the Database
     * @param event
     */
    public void yesButton(ActionEvent event) {
        songModel.delete(songToBeDeleted);

        // close the window
        closeTheWindow(event);
    }

    /**
     * method just closes the window
     * @param event
     */
    public void noButton(ActionEvent event) {
        //close the window
        closeTheWindow(event);
    }

    private void closeTheWindow(ActionEvent event)
    {
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void getSong(Song selectedItem) {
        songToBeDeleted = selectedItem;
    }
}
