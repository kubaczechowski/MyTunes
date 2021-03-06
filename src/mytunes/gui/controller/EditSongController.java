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
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * class is a controller of editSong.fxml window
 * it provides methods used while adding or editing a song.
 * @author kuba
 */
public class EditSongController implements Initializable {
    @FXML
    private TextField imagePathField;
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

    private Path pathImageOrigin;
    private Path destinationImagePath;

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
        //imagePathField.setText(selectedItem.getImagePath());
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
        pathOrigin = null;
        destinationPath = null;

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

       // timeField.textProperty().bind(mediaPlayer.totalDurationProperty().asString());
        timeField.textProperty().bind(mediaPlayer.totalDurationProperty().asString());
    }

    //disabled functionality
    /*

    public void chooseImageAction(ActionEvent actionEvent) {
        pathImageOrigin=null;
        destinationImagePath=null;

        //open a file explorer
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        fileChooser.setTitle("Choose image");
        File file = fileChooser.showOpenDialog(stage);

        //create a copy of the chosen image in the application folder if the file was chosen .png / .jpg
        pathImageOrigin = Path.of(file.getAbsolutePath());
        //set a new path

        if (pathImageOrigin.toString().contains(".png")) {
            destinationImagePath = Path.of("/Images/" + titleField.getText() + ".png");
        } else if (pathImageOrigin.toString().contains(".jpg")) {
            destinationImagePath = Path.of("/Images/" + titleField.getText() + ".jpg");
        } else {
            destinationImagePath = Path.of("/Images/default.png");
        }

        //show it in the text field
        imagePathField.setText(String.valueOf(destinationImagePath));
    }

     */

    /**
     * method based on the inserted information to the
     * window creates a song and then returns it.
     * @return Song
     */
    public Song getSongFromTable()
    {
        int id=-1; //id will be changed in the dal layer
        String title = titleField.getText();
        String artist = artistField.getText();
        String category = this.categoryMenu.getText();
        String filepath = filepathField.getText();
        String imagePath;

            imagePath = "src/Images/default.png";

       // int time = Integer.parseInt(timeField.getText());
        String preparedTimeField = timeField.getText().substring(0, timeField.getText().length()-3);
        int timeInMillis = (int) Float.parseFloat(preparedTimeField);

        Song song = new Song(id, title, artist, category, timeInMillis, filepath, imagePath);
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
        //when creating a new row in a tableview
        if(selectedItem==null) {


            //create a copy of the song and image in the program package song
            try {
                // here it creates a copy in the shown destination
                Files.copy(pathOrigin, destinationPath, COPY_ATTRIBUTES, REPLACE_EXISTING);

                //functionality disabled
            /*   if(pathImageOrigin!=null)
                    Files.copy(pathImageOrigin, destinationImagePath, COPY_ATTRIBUTES, REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

             */


            } catch (IOException e) {
                e.printStackTrace();
            }
            //song goes down in the 3-layer architecture
            songModel.save(getSongFromTable());
        }

            //editing existing row
        else if(selectedItem != null)
        {
            destinationPath = Path.of( filepathField.getText());
    /*
            try {
                Files.delete(destinationPath);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            if(pathOrigin!=null) {
                try {
                    Files.copy(pathOrigin, destinationPath, COPY_ATTRIBUTES, REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //update song not save another one
            int id = selectedItem.getId();
            String title = titleField.getText();
            String artist = artistField.getText();
            String category = this.categoryMenu.getText();
            String filepath = filepathField.getText();
           // String imagePath = imagePathField.getText();
            String preparedtime = timeField.getText().substring(0, timeField.getText().length()-3);
            int time = (int) Float.parseFloat(preparedtime);
            Song songToUpdate = new Song(id, title, artist, category, time, filepath,
                    "src/Images/default.png");
            songModel.update(songToUpdate);
            songModel.load();
        }

        //Close the window if everything went correctly
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    // categories to choose

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

