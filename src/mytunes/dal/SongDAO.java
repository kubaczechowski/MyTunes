package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
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
                String filePath = rs.getString("filePath");
                Song song = new Song(id, title, artist, category, filePath);
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

   public Song createSong(String title, String artist, String category, String filePath) throws DALexception {


        Song song = null;

        try (Connection con = databaseConnector.getConnection()) {


            String sql = "insert into Songs (title, artist, category, playTime, filePath) values (?, ?, ?, ?, ?);";

            /*
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM Songs");
            r.next();
            int id = r.getInt("rowcount") + 1;
            r.close();

             */

            //i dont know what about id
            int id = 0;

            song = new Song(id, title, artist, category, filePath);

         

            /*PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setString(1, song.getTitle());
            pstat.setString(2, song.getArtist());
            pstat.setString(3, song.getCategory());
            pstat.setInt(4, song.getTime());
            pstat.setString(5, song.getFilePath());
            pstat.executeUpdate();

            */


            String getID = "SELECT id FROM Songs WHERE filePath=?;";
            PreparedStatement preparedStatement2 = con.prepareStatement(getID);
            preparedStatement2.setString(1, filePath);
            ResultSet resultSet = preparedStatement2.executeQuery(getID);


            id = resultSet.getInt("id");

            song = new Song(id, title, artist, category, filePath);


        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("couldn't create s song");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("couldn't create a song");
        }

        return song;
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
            String sql = "UPDATE Songs SET title=?, artist=?, category=?, filePath=? WHERE id=?";
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
            String sql = "SELECT * FROM Songs WHERE id=?";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet result = pstat.executeQuery(sql);

            while (result.next()){
                String title = result.getString("title");
                String artist = result.getString("artist");
                String category = result.getString("category");

                int time = result.getInt("playTime");

                String filePath = result.getString("filePath");

                song = new Song(id, title, artist, category, filePath);
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
}
