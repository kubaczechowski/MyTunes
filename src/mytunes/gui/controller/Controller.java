package mytunes.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import javafx.util.Callback;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.model.MusicPlayer;
import mytunes.gui.model.PlaylistItemModel;
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;
import mytunes.gui.util.AlertDisplayer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Controller of main view window (sample.fxml)
 * It enables the user to choose CRUD operations on both songs
 * and playlists. Also it provides the functionality of playing songs
 * and searching them based on a title
 *
 * @author Kjell&& && Kamila &&Kuba
 * */
public class Controller implements Initializable {

    //Tables and Lists
    public TableView<Playlist> playlistsTable;
    public TableView<Song> songsTable;
    public ListView<Song> songsOnPlaylistView;
    private List<Song> unsortedList;
    private Song songSelected;

    //used for the searching functionality
    public TextField searchBar;

    // Music Player
    private final MusicPlayer musicPlayer;
    public ImageView mainImage;
    @FXML private Slider volumeSlider;
    @FXML private Text nowPlaying;
    @FXML private Text nowPlayingArtist;
    @FXML private Text nowPlayingPlaylist;

    //TableView Columns Playlists
    @FXML private TableColumn<Playlist, String> columnName;
    @FXML private TableColumn<Playlist, Integer> columnSong;
    @FXML private TableColumn<Playlist, String> columnTime;

    //TableView Columns Songs
    @FXML private TableColumn<Song, ImageView> columnImage;
    @FXML private TableColumn<Song, String> columnTitle;
    @FXML private TableColumn<Song, String> columnArtist;
    @FXML private TableColumn<Song, String > columnCategory;
    @FXML private TableColumn<Song, String> columnTimeSong;

    //instances of models
    private final PlaylistModel playlistModel;
    private final PlaylistItemModel playlistItemModel;
    private final SongModel songModel;
    private final AlertDisplayer alertDisplayer;

    private javafx.scene.media.MediaPlayer mediaPlayer;
    private javafx.scene.media.MediaPlayer mediaPlayer2;

    private boolean filterButton;

    public Controller() {
        filterButton = true;
        songModel = SongModel.createOrGetInstance();
        playlistModel = PlaylistModel.createOrGetInstance();
        alertDisplayer = new AlertDisplayer();
        musicPlayer = new MusicPlayer();
        playlistItemModel = PlaylistItemModel.createOrGetInstance();
    }

    /**
     * Method is called then insance Controller is created
     * it is used to prepare the TableViews and set initial values
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setObservableTableSongs(songModel);
        setObservableTablePlaylists(playlistModel);

        songsTable.getItems().addListener(new ListChangeListener<Song>() {
            @Override
            public void onChanged(Change<? extends Song> change) {
                musicPlayer.setSongList(songsTable.getItems());
            }
        });

        //changing the volume
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
             @Override
             public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                 musicPlayer.setVolume(volumeSlider.getValue());
             }
         });

        //set songs from the playlist in ListView
        playlistsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (newSelection.getSongs() == null) songsOnPlaylistView.getItems().clear();
                else songsOnPlaylistView.setItems(FXCollections.observableArrayList(newSelection.getSongs()));
            }
        });

        mainImage.setImage(new Image("/Images/default.png"));
        //loadSongsFromThePlaylists();

        //playing songs from the Playlist if double-clicked on the songs on Playlist ListView
        songsOnPlaylistView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    musicPlayer.setSongList(songsOnPlaylistView.getItems());
                    nowPlayingPlaylist.setText("playing from "+playlistsTable.getSelectionModel().getSelectedItem().getName());
                    try {
                        musicPlayer.loadMedia(songsOnPlaylistView.getSelectionModel().getSelectedItem());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    musicPlayer.getAudioPlayer().setOnEndOfMedia(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println("bla bla");
                            Song s = musicPlayer.getNextSongInList();

                            musicPlayer.pause();
                            try {
                                musicPlayer.loadMedia(s);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            songsOnPlaylistView.getSelectionModel().select(s);

                            updateSongInfo(s);
                            musicPlayer.setVolume(volumeSlider.getValue());
                            musicPlayer.play();

                        }
                    });
                    musicPlayer.setVolume(volumeSlider.getValue());
                    musicPlayer.play();
                    updateSongInfo(musicPlayer.getSong());
                }
            }
        });

        //if double clicked on songs TableView play song
        songsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    musicPlayer.setSongList(songsTable.getItems());
                    nowPlayingPlaylist.setText("");
                    try {
                        musicPlayer.loadMedia(songsTable.getSelectionModel().getSelectedItem());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    musicPlayer.getAudioPlayer().setOnEndOfMedia(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println("bla bla");
                            Song s = musicPlayer.getNextSongInList();

                            musicPlayer.pause();
                            try {
                                musicPlayer.loadMedia(s);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            songsTable.getSelectionModel().select(s);

                            updateSongInfo(s);
                            musicPlayer.setVolume(volumeSlider.getValue());
                            musicPlayer.play();

                        }
                    });

                    musicPlayer.setVolume(volumeSlider.getValue());
                    musicPlayer.play();
                    updateSongInfo(musicPlayer.getSong());
                }
            }
        });

        mainImage.setImage(new Image("/Images/default.png"));
    }

    /**
     * Method sets the TableView Songs so that whenever change happen
     * in Songs table in Database it is visible for the user
     * @param songModel
     */
    private void setObservableTableSongs(SongModel songModel) {
        //Initialize TableView Songs
        columnImage.setCellValueFactory(new PropertyValueFactory<Song, ImageView>("image"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        columnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<Song, String>("category"));
        columnTimeSong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Song, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Song, String> p) {
                return new ReadOnlyObjectWrapper(songModel.convertToString(  p.getValue().getPlaytime()));
            }
        });
        songModel.load();
        songsTable.setItems(songModel.getAllSongs());
    }

    /**
     * Method sets the TableView Playlists so that whenever change happen
     * in Playlists table in Database it is visible for the user
     * @param playlistModel
     */
    private void setObservableTablePlaylists(PlaylistModel playlistModel) {
        //Initialize TableView Playlists
        columnName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("name"));
        columnSong.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("numberOfSongs"));
        //columnTime.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("totalPlaytime"));

        columnTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Playlist, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Playlist, String> p) {
                return new ReadOnlyObjectWrapper(songModel.convertToString(  p.getValue().getTotalPlaytime()));
            }
        });

        playlistModel.load();
        playlistsTable.setItems(playlistModel.getAllPlaylists());
    }

    public void createPlaylist(ActionEvent actionEvent) {
        openCreateOrEditPlaylistWindow(null);
    }

    public void editPlaylist(ActionEvent actionEvent) {
        Playlist playlist = playlistsTable.getSelectionModel().getSelectedItem();

        if (playlist == null)
            alertDisplayer.displayInformationAlert("Playlist", "No playlist selected",
                    "Select playlist");
        else
            openCreateOrEditPlaylistWindow(playlistsTable.getSelectionModel().getSelectedItem());
    }

    private void openCreateOrEditPlaylistWindow(Playlist selectedItem) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/myTunes/gui/view/editPlaylist.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EditPlaylistController editPlaylistController = loader.getController();
        editPlaylistController.sendPlaylist(selectedItem);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("create/edit playlist");
        stage.show();
    }

    public void deletePlaylist(ActionEvent actionEvent) {
        playlistItemModel.safePlaylistDelete(playlistsTable.getSelectionModel().getSelectedItem());
        playlistModel.deletePlaylist(playlistsTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Button used for the searching functionality
     * @param actionEvent
     */
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
     * Method opens a new window when button new is pressed.
     * When selectedItem is null it is the signal in the later part of the
     * code that user wants to add new song. In the opposite case it indicates that
     * user wants to edit selected song from the TableView
     */
    @FXML
    private void openAddWindow() {
        loadOpenAddSongWindow(null);
    }

    /**
     * Method at first gets the selected item from the TableView
     * and then assures that selected item is null
     * if yes: it shows a prompt with information for the user
     * if no(isn't null): it executes the request
     * @param event
     */
    public void openEditSongButton(ActionEvent event) {
        songSelected = songsTable.getSelectionModel().getSelectedItem();

        if (songSelected == null)
            alertDisplayer.displayInformationAlert("Song",
                    "song isn't selected", "please choose song");

        loadOpenAddSongWindow(songSelected);
    }

    /**
     * Window opened when user creates or updates a song
     * it opens a new window and sends the necessary data to other controller
     * @param selectedItem
     */
    private void loadOpenAddSongWindow(Song selectedItem) {
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
     * Method opens confirmation window that is used to ensure
     * that user wants to delete a song
     * it also sends the selected item to the newly opened window
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

    /**
     * Method used to sort the ListView containing Playlists.
     * It sorts alfabeticaly at the top is A and at the bottom is Z
     * @param event
     */
    public void sortAscending(ActionEvent event) {
        unsortedList = songsOnPlaylistView.getItems();

        Comparator<Song> compareByTitle = new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }
        };

        Collections.sort(unsortedList, compareByTitle);
        songsOnPlaylistView.setItems(FXCollections.observableList(unsortedList));
    }

    /**
     * Method used to sort the ListView.
     * It sorts in reversed order. at the top is Z and at the bottom is A
     * @param event
     */
    public void sortDescending(ActionEvent event) {
        unsortedList = songsOnPlaylistView.getItems();

        Comparator<Song> compareByTitle = new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o2.getTitle().toLowerCase().compareTo(o1.getTitle().toLowerCase());
            }
        };

        Collections.sort(unsortedList, compareByTitle);
        songsOnPlaylistView.setItems(FXCollections.observableList(unsortedList));
    }

    /**
     * Adds the selected song to the selected playlist
     * @param actionEvent
     */
    public void addSongToPlaylist(ActionEvent actionEvent) {
        try {
            songSelected = songsTable.getSelectionModel().getSelectedItem();
            Playlist playlistSelected = playlistsTable.getSelectionModel().getSelectedItem();

            addingNewSongToPlaylist(playlistSelected, songSelected);
            songsOnPlaylistView.setItems(FXCollections.observableList(playlistSelected.getSongs()));
        } catch (Exception e) {
            System.out.println("No song or playlist selected");
        }
    }

    /**
     * Adds a new song to PlaylistView
     * and updates number of songs and playtime in TableView Playlist
     */
    private void addingNewSongToPlaylist(Playlist playlist, Song song) {
        //updating DB
        playlistModel.updateTotalTimeOnPlaylistADD(playlist, song.getPlaytime());
        playlistModel.incrementNumberOfSongsOnPlaylist(playlist);
        //update observable list of playlist in PlaylistModel
        playlistModel.addSongToPlaylist(playlist, song);
        //force TableView Playlists to refresh
        playlistModel.load();
    }

    /**
     * Removes a song in the PlaylistView list
     * and updates number of songs and playtime in TableView Playlist
     */
    public void btnDeleteSongsFromPlaylist(ActionEvent event) {
        songSelected = songsOnPlaylistView.getSelectionModel().getSelectedItem();
        Playlist playlistSelected = playlistsTable.getSelectionModel().getSelectedItem();
        playlistItemModel.deletePlaylistItem(playlistSelected, songSelected);

        //updating DB
        playlistModel.updateTotalTimeOnPlaylistRemove(playlistSelected, songSelected.getPlaytime());
        playlistModel.decrementNumberOfSongsOnPlaylist(playlistSelected);

        //forces TableView Playlists to refresh
        playlistModel.load();
        songsOnPlaylistView.setItems(FXCollections.observableList(playlistSelected.getSongs()));
    }

    /**
     * Closes the program
     * @param event
     */
    public void btnClose(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Plays or pauses the song depending on the media players state.
     * @param actionEvent
     */
    public void play(ActionEvent actionEvent) {
        if(!musicPlayer.isPaused()){
            musicPlayer.pause();
        } else if(musicPlayer.isPaused()) {
            musicPlayer.setVolume(volumeSlider.getValue());
            musicPlayer.play();
        }
    }

    /**
     * Loads and plays the previouis song in the list
     * @param actionEvent
     * @throws MalformedURLException
     */
    public void previousSong(ActionEvent actionEvent) throws MalformedURLException {
        Song s = musicPlayer.getPreviousSongInList();

        musicPlayer.pause();
        musicPlayer.loadMedia(s);
        songsTable.getSelectionModel().select(s);

        musicPlayer.setVolume(volumeSlider.getValue());
        updateSongInfo(s);
        musicPlayer.play();
    }

    /**
     * Loads and plays the next song in the list
     * @param actionEvent
     * @throws MalformedURLException
     */
    public void nextSong(ActionEvent actionEvent) throws MalformedURLException {
        Song s = musicPlayer.getNextSongInList();

        musicPlayer.pause();
        musicPlayer.loadMedia(s);
        songsTable.getSelectionModel().select(s);

        updateSongInfo(s);
        musicPlayer.setVolume(volumeSlider.getValue());
        musicPlayer.play();
    }

    /**
     * Updates the songs title, artist and image
     * @param song song that is playing
     */
    public void updateSongInfo(Song song) {
        nowPlaying.setText(song.getTitle());
        nowPlayingArtist.setText(song.getArtist());
        mainImage.setImage(new Image(song.getImagePath().replace("src", "")));
    }
}