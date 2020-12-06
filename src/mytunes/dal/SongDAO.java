package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.scene.media.Media;
import mytunes.be.Song;
import mytunes.dal.exception.DALexception;
import mytunes.dal.interfaces.ISongRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongRepository {

    private DatabaseConnector databaseConnector;

    public SongDAO()  {
        databaseConnector = new DatabaseConnector();
    }

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
                int playTime = rs.getInt("playTime");
                String filePath = rs.getString("filePath");
                Song song = new Song(id, title, artist, category, playTime, filePath);
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

   public Song createSong(String title, String artist, String category,
                          String filePath) throws DALexception {
       try (Connection con = databaseConnector.getConnection()) {
             String sql= " INSERT INTO SONGS (title, artist," +
                     " category, playtime, filePath) values(?, ?, ?, ?, ?); ";
           PreparedStatement preparedStatement = con.prepareStatement(sql);
           preparedStatement.setString(1, title);
           preparedStatement.setString(2, artist);
           preparedStatement.setString(3, category);
           preparedStatement.setInt(4, getSongTime(filePath) );
           preparedStatement.setString(5, filePath);

           preparedStatement.executeUpdate();
           //song is created in the DB

           //pass it so that we can see it in the TableView
           //Thanks to Observable pattern

           String sql2 = "Select id FROM Songs WHERE title=?;";
           PreparedStatement preparedStatement1 = con.prepareStatement(sql2);
           ResultSet rs  = preparedStatement1.executeQuery();
           int id = rs.getInt("id");
           return new Song(id, title, artist, category, getSongTime(filePath), filePath);

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
            pstat.executeUpdate();
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
    public void updateSong(Song song, String title, String artist, String category, String filePath) throws DALexception {

        try (Connection con = databaseConnector.getConnection()) {
            String sql = "UPDATE Songs SET title=?, artist=?, " +
                    "category=?, filePath=? WHERE id=?;";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setString(1, title);
            pstat.setString(2, artist);
            pstat.setString(3, category);
            pstat.setString(4, filePath);
            pstat.setInt(5, song.getId());
            pstat.executeUpdate();
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

                song = new Song(id, title, artist, category, time, filePath);
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
    public int getSongTime(String mediaStringUrl) {
        Media media = new Media(mediaStringUrl);
        int time = (int) media.getDuration().toSeconds();
        return time;
    }
}
