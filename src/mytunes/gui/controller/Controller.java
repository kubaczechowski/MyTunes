package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


import javafx.stage.FileChooser;

import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TableView<Playlist> playlistsTable;
    public ListView<Song> songsOnPlaylistView;
    public TableView<Song> songsTable;
    public TextField searchBar;
    public TableColumn pName;

    private PlaylistModel playlistModel;
    private SongModel songModel;
    private boolean filterButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playlistModel = new PlaylistModel();
        songModel = new SongModel();
        playlistsTable.setItems(playlistModel.getAllPlaylists());
        songsTable.setItems(songModel.getAllSongs());
        filterButton = true;
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

            EditPlaylistController controller = loader.getController();
            controller.newOrEdit(false, playlistModel, playlistsTable.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit playlist");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePlaylist(ActionEvent actionEvent) {
        playlistModel.deletePlaylist(playlistsTable.getSelectionModel().getSelectedItem());
    }

    public void playlistSelected(MouseEvent mouseEvent) {
        Playlist p = playlistsTable.getSelectionModel().getSelectedItem();
        songsOnPlaylistView.setItems(p.getSongs());
    }

    public void searchAction(ActionEvent actionEvent) {
        if(filterButton) {
            songsTable.setItems(songModel.searchSongs(searchBar.getText()));
            filterButton = false;
        } else {
            songsTable.setItems(songModel.getAllSongs());
            searchBar.clear();
            filterButton = true;
        }
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


        Stage stage = new Stage();
        stage.setTitle("Prompt");
        stage.setScene(new Scene(root));
        stage.show();


    }

    public void sortAscending(ActionEvent event) {
        //get all songs in the table
        ArrayList<Song> songArrayList= new ArrayList<>();
        songArrayList = (ArrayList<Song>) songsOnPlaylistView.getItems() ;

        songArrayList.sort(Comparator.comparing(Song::getTitle));
        songsOnPlaylistView.getItems().removeAll();

        songsOnPlaylistView.getItems().addAll(songArrayList);
       // songsOnPlaylistView
    }

    public void sortDescending(ActionEvent event) {

        Comparator comparatorDesc = Collections.reverseOrder();

        //get all songs in the table
        ArrayList<Song> songArrayList= new ArrayList<>();
        songArrayList = (ArrayList<Song>) songsOnPlaylistView.getItems() ;

        songArrayList.sort(Comparator.comparing(Song::getTitle).reversed());
        songsOnPlaylistView.getItems().removeAll();

        songsOnPlaylistView.getItems().addAll(songArrayList);
        // songsOnPlaylistView
    }
}


