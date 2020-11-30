package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunes.be.Song;

import java.sql.*;

public class SongDAO {

    private SQLServerDataSource dataSource = new SQLServerDataSource();

    /**
     * Creates a new song which is added to the database
     */
    private Song createSong(String title, String artist, String category, String filePath) {

        try (Connection con = dataSource.getConnection()) {

            Statement s = con.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM Songs");
            r.next();
            int id = r.getInt("rowcount") + 1;
            r.close() ;

            Song song = new Song(id, title, artist, category, filePath);

            String sql = "insert into Songs (title, artist, category, time, filePath) values (?, ?, ?, ?, ?);";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setString(1, song.getTitle());
            pstat.setString(2, song.getArtist());
            pstat.setString(3, song.getCategory());
            pstat.setDouble(4, song.getTime());
            pstat.setString(5, song.getFilePath());
            pstat.executeUpdate();

            return song;

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes given song
     */
    private void deleteSong(Song song) {

        try (Connection con = dataSource.getConnection()) {
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

        try (Connection con = dataSource.getConnection()) {
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

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM SONGS WHERE id=?";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet result = pstat.executeQuery(sql);

            while (result.next()){
                String title = result.getString("title");
                String artist = result.getString("artist");
                String category = result.getString("category");
                double time = result.getDouble("time");
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
