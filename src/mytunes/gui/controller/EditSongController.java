package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

/**
 * class is a controller of editSong.fxml window
 * it provides methods used while adding or editing a song.
 * @author kuba
 */
public class EditSongController implements Initializable {
    @FXML
    private TextField titleField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField filepathField;
    @FXML
    private SplitMenuButton categoryMenu;


    private FileChooser fileChooser;
    private SongModel songModel;
    private Song selectedItem;

    private Path pathOrigin;
    private Path destinationPath;


    public void setModel(SongModel songModel, FileChooser fileChooser, Song selectedItem) {
        this.songModel = songModel;
        this.fileChooser = fileChooser; //its set in the controller
        this.selectedItem = selectedItem;

        // if below used only while development
        if(selectedItem==null)
            System.out.println("in set model selected item is null");

        if(selectedItem!=null)
            setInformation(selectedItem);
    }

    /**
     * When a window new/edit song is opened
     * this method sets the information about the chosen song
     * in the corresponding fields
     * @param selectedItem
     */
    private void setInformation(Song selectedItem)
    {
        titleField.setText(selectedItem.getTitle());
        artistField.setText(selectedItem.getArtist());
        categoryMenu.setText(selectedItem.getCategory());
        timeField.setText(String.valueOf(selectedItem.getPlaytime()));
        filepathField.setText(selectedItem.getFilePath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryMenu.setText("genre");
    }

    /**
     * Method closes window and doesn't make any changes
     * @param event
     */
    public void cancelButtonAction(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }


    public void chooseButtonAction(ActionEvent event) {
        pathOrigin=null;
        destinationPath=null;

        //open a file explorer
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        fileChooser.setTitle("Choose song");
        File file = fileChooser.showOpenDialog(stage);

        //create a copy of the chosen song in the application folder if the file was chosen .mp3 / .wav
         pathOrigin = Path.of(file.getAbsolutePath());
        //set a new path

        if (pathOrigin.toString().contains(".wav")) {
            destinationPath = Path.of("src/Music/" + titleField.getText() + ".wav");
        } else if (pathOrigin.toString().contains(".mp3")) ;
        {
            destinationPath = Path.of("src/Music/" + titleField.getText() + ".mp3");
        }

        //show it in the filepathFiled
        filepathField.setText(String.valueOf(destinationPath));

        //int time = songModel.getSongTime(pathOrigin.toUri().toString());
        Media media = new Media( pathOrigin.toUri().toString());
        MediaPlayer mediaPlayer= new MediaPlayer(media);

        timeField.textProperty().bind(mediaPlayer.totalDurationProperty().asString());
    }

    /**
     * method based on the inserted information to the
     * window creates a song and then returns it.
     * @return Song
     */
    public Song getSong()
    {
        int id=-1; //id will be changed in the dal layer
        String title = titleField.getText();
        String artist = artistField.getText();
        String category = this.categoryMenu.getText();
        String filepath = filepathField.getText();
        int time = Integer.parseInt(timeField.getText());
        Song song = new Song(id, title, artist, category, time, filepath);
        return song;
    }

    /**
     * selectedItem==null means that we are creating a song
     * beforehand there was a data validation what means that if now
     * selectedItem is null we want to create a new song
     *
     * selectedItem!=null means that we are updating a song
     * @param event
     */
    public void saveButtonAction(ActionEvent event) {
        if(selectedItem==null) {
            //song goes down in the 3-layer architecture
            songModel.save(getSong());


            //create a copy of the song in the program package song
            try {
                // here it creates a copy in the shown destination
                Files.copy(pathOrigin, destinationPath, COPY_ATTRIBUTES);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(selectedItem!=null)
        {
            //update song not save another one
            songModel.update(selectedItem);
            songModel.load();
        }

        //Close the window if everything went correctly
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }


    public void rap(ActionEvent event) {
        categoryMenu.setText("rap");
    }

    public void trap(ActionEvent event) {
        categoryMenu.setText("trap");
    }

    public void pop(ActionEvent event) {
        categoryMenu.setText("pop");
    }

    public void jazz(ActionEvent event) {
        categoryMenu.setText("jazz");
    }

    public void rock(ActionEvent event) {
        categoryMenu.setText("rock");
    }
}
