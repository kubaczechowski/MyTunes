package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< Updated upstream
=======
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
>>>>>>> Stashed changes
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mytunes.gui.model.SongModel;

import java.io.IOException;

public class Controller {
    private SongModel songModel;
<<<<<<< Updated upstream
=======
    private boolean filterButton;

    public Controller()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //preparing
        playlistModel = new PlaylistModel();
        songModel = new SongModel();
        //tablePlaylist.addAll(playlistModel.getAllPlaylists());

        //I don't remember what it means
        filterButton = true;

        //TableView Playlists
        columnName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("name"));
        columnSong.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("numberOfSongs"));
        columnName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("totalPlaytime"));
        playlistsTable.setItems(playlistModel.getAllPlaylists());


        //songModel.loadSongs();
        //playlistsTable.setItems(songModel.getAllSongs());




    }

    public void newPlaylist(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/myTunes/gui/view/editPlaylist.fxml"));
            Parent root = loader.load();

            EditPlaylistController controller = loader.getController();
            controller.newOrEdit(true, playlistModel, null);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add new playlist");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editPlaylist(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/myTunes/gui/view/editPlaylist.fxml"));
            Parent root = loader.load();
>>>>>>> Stashed changes


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

    public void DeleteSongButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/deleteSongPrompt.fxml"));
        Parent root = loader.load();
        DeleteSongPrompt deleteSongPrompt = loader.getController();
        //send the song to another controller
        deleteSongPrompt.getSong(songsTable.getSelectionModel().getSelectedItem());

<<<<<<< Updated upstream
=======
        Stage stage = new Stage();
        stage.setTitle("Prompt");
        stage.setScene(new Scene(root));
        stage.show();

     //data has to be passed back
>>>>>>> Stashed changes
    }
}
