package mytunes.be;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URISyntaxException;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String category;
    private int playtime;
    private String filePath;
    private String imagePath;
    private ImageView image;

    public Song(int id, String title, String artist, String category, int playTime, String filePath, String imagePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.playtime = playTime;
        this.filePath = filePath;
        this.imagePath = imagePath;

        //image = new ImageView("/Images/default.png");


        if(imagePath==null) {
            image = new ImageView("/Images/default.png");
        } else {


            String imgp = imagePath.replace("src", "").replace("\\", "/");
            // image = new ImageView(new Image(imgp));

            //experimenting here
            try {
                image = new ImageView(new Image(getClass().getResource(imgp).toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        // String imgp = imagePath.replace("src/", "").replace("\\", "/");
           // String imgp = imagePath.replace("src", "");
           // image = new ImageView(new Image(imgp));

            //experimenting
            //you can add but you cant see images
            //image = new ImageView( new Image( new File(imgp).toURI().toString()));


            //now i have null pointer exception
            /*
            try {
                image = new ImageView( new Image( getClass().getResource(imgp).toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

             */
            //getClass().getResource("za.jpg").toURI().toString()




        }




        this.image.setFitHeight(20);
        this.image.setFitWidth(20);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPlaytime() {
        return playtime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return title;
    }
}
