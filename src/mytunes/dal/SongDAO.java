package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.binding.StringBinding;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mytunes.be.Song;
import mytunes.dal.exception.DALexception;
import mytunes.dal.interfaces.ISongRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * class used for communication with DB
 * provides CRUD functionality
 * @author kuba
 */
public class SongDAO implements ISongRepository {

    private DatabaseConnector databaseConnector;

    public SongDAO()  {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * method returns all songs that are in DB
     * @return
     * @throws DALexception
     */
    public List<Song> getAllSongs() throws DALexception {
        ArrayList<Song> allSongs = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Songs;";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                int playTime = rs.getInt("playtime");
                String filePath = rs.getString("filePath");
                String imagePath = rs.getString("imagePath");
                Song song = new Song(id, title, artist, category, playTime, filePath, imagePath);
                allSongs.add(song);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DALexception("Couldn't get all songs");
        }
        return allSongs;
    }

    /**
     * Creates a new song which is added to the database
     */

   public void createSong(String title, String artist, String category, int time ,
                          String filePath, String imagePath) throws DALexception {
       String sql= " INSERT INTO SONGS (title, artist," +
           " category, playtime, filePath, imagePath) values(?, ?, ?, ?, ?, ?); ";

      // String sql2 = "Select id FROM Songs WHERE title=?;";

       try (Connection con = databaseConnector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
           // PreparedStatement preparedStatement1 = con.prepareStatement(sql2)
       ) {
            //create song in DB
           preparedStatement.setString(1, title);
           preparedStatement.setString(2, artist);
           preparedStatement.setString(3, category);
           preparedStatement.setInt(4,  time);
           preparedStatement.setString(5, filePath);
           preparedStatement.setString(6, imagePath);
           preparedStatement.executeUpdate();

/*
           ResultSet rs  = preparedStatement1.executeQuery();
           int id = rs.getInt("id");
           int songTime = rs.getInt("playTime");
           return new Song(id, title, artist, category, songTime, filePath);

 */

       } catch (SQLServerException throwables) {
           throw new DALexception("Couldn't crate song", throwables);
       } catch (SQLException throwables) {
           throw new DALexception("Couldn't create song", throwables);
       }
   }

    /**
     * Deletes given song
     */
    public void deleteSong(Song song) throws DALexception {

        try (Connection con = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Songs WHERE id=?;";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, song.getId());
            pstat.execute();
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete song");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete song");
        }
    }

    /**
     * Updates the given song with given values
     */
    public void updateSong(Song song, String title, String artist,
                           String category, String filePath, String imagePath) throws DALexception {
        String sql = "UPDATE Songs SET title=?, artist=?, " +
                "category=?, filePath=?, imagePath=? WHERE id=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(sql)) {


            pstat.setString(1, title);
            pstat.setString(2, artist);
            pstat.setString(3, category);
            pstat.setString(4, filePath);
            pstat.setString(5, imagePath);
            pstat.setInt(6, song.getId());
            pstat.execute();
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update song");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update song");
        }
    }

    /**
     * Returns a song with desired values
     */
    public Song getSong(int id) throws DALexception {

        Song song = null;

        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Songs WHERE id=?;";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet result = pstat.executeQuery(sql);

            while (result.next()){
                String title = result.getString("title");
                String artist = result.getString("artist");
                String category = result.getString("category");
                int time = result.getInt("playTime");
                String filePath = result.getString("filePath");
                String imagePath = result.getString("imagePath");

                song = new Song(id, title, artist, category, time, filePath, imagePath);
            }

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a song");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a song");
        }

        return song;
    }

    //I don't know if this method is used somewhere
    @Override
    public void updateSong(int id) {

    }




    /**
     * Method that returns the time of the song in the seconds
     */
    /*
    public int getSongTime(String mediaStringUrl) {
        Media media = new Media(mediaStringUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        new StringBinding() {


            @Override
            protected String computeValue() {
                String form = String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long)mediaPlayer.getCurrentTime().toMillis()),
                        TimeUnit.MILLISECONDS.toSeconds((long)mediaPlayer.getCurrentTime().toMillis()) -
                                TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(
                                                (long)mediaPlayer.getCurrentTime().toMillis()
                                        )
                                )
                );

                return form;
            }
        };

            return -1;
        }

     */

    public int getSongTime(String mediaStringUrl){
        Media media = new Media(mediaStringUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
       // mediaPlayer.play();

        if(mediaPlayer.getStatus()==MediaPlayer.Status.READY)
            return (int) mediaPlayer.getStopTime().toSeconds();

         else
            System.out.println("player is not ready");
            return -1;
    }
}
