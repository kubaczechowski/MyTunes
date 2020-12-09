package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    public TableView<Playlist> playlistsTable;
    public ListView<Song> songsOnPlaylistView;
    public TableView<Song> songsTable;
    public TextField searchBar;
    public TableColumn pName;


    //TableView Columns PLAYLIST
    @FXML
    private TableColumn<Playlist, String> columnName;
    @FXML
    private TableColumn<Playlist, Integer> columnSong;
    @FXML
    private TableColumn<Playlist, Integer> columnTime;

    //TableView Columns Songs
    @FXML
    private  TableColumn<Song, String> columnTitle;
    @FXML
    private  TableColumn<Song, String> columnArtist;
    @FXML
    private TableColumn<Song, String > columnCategory;

    @FXML TableColumn<Song, Integer> columnTimeSong;

    //private ObservableList<Playlist> tablePlaylist =
          // FXCollections.observableArrayList();

    private PlaylistModel playlistModel;
    private SongModel songModel;
    private boolean filterButton;

    public Controller()
    {
     songModel = SongModel.createOrGetInstance();
    }

    public void setObservableTableSongs(SongModel songModel)
    {
        songsTable.setItems(songModel.getAllSongs());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //preparing
        playlistModel = new PlaylistModel();
        setObservableTableSongs(songModel);

        //tablePlaylist.addAll(playlistModel.getAllPlaylists());

        //I don't remember what it means
        filterButton = true;

        //TableView Playlists
        columnName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("name"));
        columnSong.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("numberOfSongs"));
        columnTime.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("totalPlaytime"));
        playlistModel.load();
        playlistsTable.setItems(playlistModel.getAllPlaylists());

        //TableView Songs
        columnTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        columnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<Song, String>("category"));
        columnTimeSong.setCellValueFactory(new PropertyValueFactory<Song, Integer>("playtime"));
        songModel.load();
        songsTable.setItems(songModel.getAllSongs());

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
        //here is an exception
       // songsOnPlaylistView.setItems(p.getSongs());
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



    /**
     *method opens a new window when button new or edit are pressed
     */
    @FXML
    private void openAddWindow() {
        loadOpenAddSongWindow(null);
    }

    public void openEditSongButton(ActionEvent event) {
        //check if item is selected if yess open normally window
        //if not show a prompt
        Song song = songsTable.getSelectionModel().getSelectedItem();

        if(song!= null )
            loadOpenAddSongWindow(song);


    }


    private void loadOpenAddSongWindow(Song selectedItem)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/editSong.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditSongController editSongController= loader.getController();
        //Create a file chooser object which then will be send to the EditSongController
        FileChooser fileChooser = new FileChooser();
        editSongController.setModel(songModel, fileChooser, selectedItem);
        Stage stage = new Stage();
        stage.setTitle("New/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }



    /**
     * method opens an alert window when delete button is pressed
     */
    public void deleteSongButton(ActionEvent event) throws IOException {
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
        //get all songs from the ListView
        List<Song> allSongsOnPlaylistSorted = songsOnPlaylistView.getItems();
        //sort
        allSongsOnPlaylistSorted.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));

        //upload the ListView
        songsOnPlaylistView.getItems().removeAll(songsOnPlaylistView.getItems());
        songsOnPlaylistView.getItems().addAll(allSongsOnPlaylistSorted);

        /*
        //get all songs in the table
        ArrayList<Song> songArrayList= new ArrayList<>();
        songArrayList = (ArrayList<Song>) songsOnPlaylistView.getItems() ;

        songArrayList.sort(Comparator.comparing(Song::getTitle));
        songsOnPlaylistView.getItems().removeAll();

        songsOnPlaylistView.getItems().addAll(songArrayList);
       // songsOnPlaylistView

         */
    }

    public void sortDescending(ActionEvent event) {

        //get all songs from the ListView
        List<Song> allSongsOnPlaylistSorted = songsOnPlaylistView.getItems();
        //sort
        allSongsOnPlaylistSorted.sort((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()));

        //upload the ListView
        songsOnPlaylistView.getItems().removeAll(songsOnPlaylistView.getItems());
        songsOnPlaylistView.getItems().addAll(allSongsOnPlaylistSorted);

        /*

        Comparator comparatorDesc = Collections.reverseOrder();

        //get all songs in the table
        ArrayList<Song> songArrayList= new ArrayList<>();
        songArrayList = (ArrayList<Song>) songsOnPlaylistView.getItems() ;

        songArrayList.sort(Comparator.comparing(Song::getTitle).reversed());
        songsOnPlaylistView.getItems().removeAll();

        songsOnPlaylistView.getItems().addAll(songArrayList);
        // songsOnPlaylistView

         */
    }


}


