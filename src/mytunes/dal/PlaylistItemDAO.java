package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunes.be.PlaylistItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistItemDAO {

    private DatabaseConnector databaseConnector;

    public PlaylistItemDAO()  {
        databaseConnector = new DatabaseConnector();
    }

    public List<PlaylistItem> getAllPlaylistItems() {

        ArrayList<PlaylistItem> all = new ArrayList<>();

        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM PlaylistItems;";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                int songId = rs.getInt("songID");
                int playlistId = rs.getInt("playlistID");

                PlaylistItem playlistItem = new PlaylistItem(songId, playlistId);
                all.add(playlistItem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return all;
    }

    /**
     * Creates a new playlistItem (adds a song to the playlist)
     */
    private PlaylistItem createPlaylistItem(int songId, int playlistId) {

        PlaylistItem playlistItem = null;

        try (Connection con = databaseConnector.getConnection()) {

            playlistItem = new PlaylistItem(songId, playlistId);

            String sql = "insert into PlaylistItems (songID, playlistID) values (?, ?);";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, playlistItem.getSongId());
            pstat.setInt(2, playlistItem.getPlaylistId());
            pstat.executeUpdate();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return playlistItem;
    }

    /**
     * Deletes given playlistItem (deleting song from a playlist)
     */
    private void deleteSong(PlaylistItem playlistItem) {

        try (Connection con = databaseConnector.getConnection()) {
            String sql = "DELETE FROM PlaylistItems WHERE songID=?, playlistID=?;";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, playlistItem.getSongId());
            pstat.setInt(2, playlistItem.getPlaylistId());

            pstat.executeUpdate();
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
