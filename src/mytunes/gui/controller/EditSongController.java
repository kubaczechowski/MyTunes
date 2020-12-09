package mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;

import javafx.scene.control.TextField;

import javax.sound.midi.Track;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

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
    private SplitMenuButton category;

    @FXML
    private Button chooseButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private FileChooser fileChooser;

    private SongModel songModel;


    public void setModel(SongModel songModel, FileChooser fileChooser) {
        this.songModel = songModel;
        this.fileChooser = fileChooser; //its set in the controller
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category.setText("choose:");
        category.getItems().addAll(
                new javafx.scene.control.MenuItem("rap"),
                new javafx.scene.control.MenuItem("pop"),
                new javafx.scene.control.MenuItem("country"),
                new javafx.scene.control.MenuItem("rock"),
                new javafx.scene.control.MenuItem("jazz"),
                new javafx.scene.control.MenuItem("blues"),
                new javafx.scene.control.MenuItem("bednarek")
                );
        //category.getItems();

    }

    //Buttons below

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
        //open a file explorer
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        fileChooser.setTitle("Choose song");
        File file = fileChooser.showOpenDialog(stage);

        //create a copy of the chosen song in the application folder IF the file was chosen .mp3 / .wav
        Path pathOrigin = Path.of(file.getAbsolutePath());
        //set a new path
        Path destinationPath;
        if (pathOrigin.toString().contains(".wav")) {
            destinationPath = Path.of("src/Music/" + titleField.getText() + ".wav");
        } else if (pathOrigin.toString().contains(".mp3")) ;
        {
            destinationPath = Path.of("src/Music/" + titleField.getText() + ".mp3");
        }

        try {
            // here it creates a copy in the shown destination
            Files.copy(pathOrigin, destinationPath, COPY_ATTRIBUTES);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //show it in the filepathFiled
        filepathField.setText(String.valueOf(destinationPath));

        //calcutate the time of the song
        int time = songModel.getSongTime(pathOrigin.toUri().toString());

        //show it in the timeField
        timeField.setText(String.valueOf(time));

    }


    public void saveButtonAction(ActionEvent event) {
        //save the object in the DB
        int id=-1; //id will be changed in the dal layer
        String title = titleField.getText();
        String artist = artistField.getText();
        String category = this.category.getText();
        String filepath = filepathField.getText();
        int time = Integer.parseInt(timeField.getText());
        //URL url = getClass().getResource(filePath2);
        //String mediaStringUrl = url.toExternalForm();
        //int time = songModel.getSongTime(mediaStringUrl);
        //show time in the titleField
        Song song = new Song(id, title, artist, category,time , filepath);

        //song goes down in the 3-layer architecture
        songModel.save(song);

        //Close the window if everything went correctly
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

    }



}
