package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TableView<Playlist> playlistsTable;
    public ListView songsOnPlaylistView;
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
}
