package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunes.be.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {

    private DatabaseConnector databaseConnector;

    public SongDAO()  {
        databaseConnector = new DatabaseConnector();
    }

    public List<Song> getAllSongs() {
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
        }
        return allSongs;
    }

    /**
     * Creates a new song which is added to the database
     */
   public Song createSong(String title, String artist, String category, String filePath) {

        Song song = null;

        try (Connection con = databaseConnector.getConnection()) {

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

            String sql = "insert into Songs (title, artist, category, time, filePath) values (?, ?, ?, ?, ?);";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setString(1, song.getTitle());
            pstat.setString(2, song.getArtist());
            pstat.setString(3, song.getCategory());
            pstat.setInt(4, song.getTime());
            pstat.setString(5, song.getFilePath());
            pstat.executeUpdate();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return song;
    }

    /**
     * Deletes given song
     */
    private void deleteSong(Song song) {

        try (Connection con = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Songs WHERE id=?;";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, song.getId());
            pstat.executeUpdate();
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates the given song with given values
     */
    private void updateSong(Song song, String title, String artist, String category, String filePath) {

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Returns a song with desired values
     */
    private Song getSong(int id) {

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
                int time = result.getInt("time");
                String filePath = result.getString("filePath");

                song = new Song(id, title, artist, category, filePath);
            }

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return song;
    }
}
