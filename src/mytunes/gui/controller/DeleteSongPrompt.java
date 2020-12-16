package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        songModel.safeSongDelete(songToBeDeleted);
        songModel.delete(songToBeDeleted);
        songModel.load();
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

    public void yesSong(ActionEvent event) {
        songModel.safeSongDelete(songToBeDeleted);
        songModel.delete(songToBeDeleted);
        songModel.load();
        try {
            Files.delete(Path.of(songToBeDeleted.getFilePath()));
        } catch (IOException e) {
            System.out.println("couldn't delete song");
            e.printStackTrace();
        }
    }
}
