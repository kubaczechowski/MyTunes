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

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
    private String filepath;

    public void setModel(SongModel songModel, FileChooser fileChooser) {
        this.songModel = songModel;
        this.fileChooser = fileChooser;
        //Kamila's part for now i will live as it is
        // tableMovie.setItems(movieModel.getObservableMovies());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category.setText("choose:");
        category.getItems().addAll(new javafx.scene.control.MenuItem("rap"),
                new javafx.scene.control.MenuItem("pop"),
                new javafx.scene.control.MenuItem("country"),
                new javafx.scene.control.MenuItem("rock"),
                new javafx.scene.control.MenuItem("jazz"),
                new javafx.scene.control.MenuItem("blues"),
                new javafx.scene.control.MenuItem("bednarek")
                );
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

        fileChooser.setTitle("Add file");
        File file = fileChooser.showOpenDialog(stage);
        //set the file Path in the TextField
        if (file != null) {
            fileChooser.getExtensionFilters().addAll(
                   // new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                  //  new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));

            //show the file path to the user
             filepath = file.getAbsolutePath();
            filepathField.setText(filepath);
            //then we need to save a copy of the song in our program so that all the users will be able to listen to added song
            //only a local path will be stored in the DB

            //add a file to the program
            if(file!= null)
            {
                //Desktop desktop = Desktop.getDesktop();
                // it simply opens the file desktop.open(file);
               File file1;
               // fileChooser.setInitialDirectory(
               //         new File("D:\\onedrive2\\github\\Itunes on Kjell\\MyTunes\\src\\Music"));
               //file1 = fileChooser.showSaveDialog(stage);

                //save that file in the program
            }



        }



        }
    @FXML
    public void initialize() {
       //fileField.setText(file.getAbsolutePath());
    }

    public void saveButtonAction(ActionEvent event) {
        //save the object in the DB
        int id=-1; //id will be changed in the dal layer
        String title = titleField.getText();
        String artist = artistField.getText();
        String category = this.category.getText();
        URL url = getClass().getResource(filepath);
        String mediaStringUrl = url.toExternalForm();
        int time = songModel.getSongTime(mediaStringUrl);
        //show time in the titleField
        titleField.setText(String.valueOf(time));
        Song song = new Song(id, title, artist, category, filepath);

        //song goes down in the 3-layer architecture
        songModel.save(song);

        //Close the window if everything went correctly
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

    }


}
