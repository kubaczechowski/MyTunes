package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mytunes.gui.model.SongModel;

import java.io.IOException;

public class Controller {
    private SongModel songModel;


    public void setModel(SongModel songModel) {
        this.songModel = songModel;

        //Kamila's part for now i will live as it is
       // tableMovie.setItems(movieModel.getObservableMovies());
    }

    public void newSongButton(ActionEvent event) throws IOException {
       openAddEditWindow();
    }


    public void editSongButton(ActionEvent event) throws IOException {
        openAddEditWindow();
    }

    /**
     * method created in order to obey repetition
     * is used by the two methods newSongButton & editSongButton
     */
    private void openAddEditWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/editSong.fxml"));
        Parent root = loader.load();
        EditSongController editSongController= loader.getController();
        //Create a file chooser object which then will be send to the EditSongController
        FileChooser fileChooser = new FileChooser();
        editSongController.setModel(songModel, fileChooser);
        Stage stage = new Stage();
        stage.setTitle("New/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void DeleteSongButton(ActionEvent event) {

    }
}
