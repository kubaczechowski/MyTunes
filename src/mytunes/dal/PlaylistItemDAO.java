package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunes.be.PlaylistItem;
import mytunes.dal.exception.DALexception;
import mytunes.dal.interfaces.IPlaylistItemRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistItemDAO implements IPlaylistItemRepository {

    private DatabaseConnector databaseConnector;

    public PlaylistItemDAO()  {
        databaseConnector = new DatabaseConnector();
    }

    public List<PlaylistItem> getAllPlaylistItems() throws DALexception {

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
            throw new DALexception("Couldn't getAllPlaylistItems", ex);
        }
        return all;
    }

    /**
     * Creates a new playlistItem (adds a song to the playlist)
     */
    public PlaylistItem createPlaylistItem(int songId, int playlistId) throws DALexception {

        PlaylistItem playlistItem = null;

        try (Connection con = databaseConnector.getConnection()) {

            playlistItem = new PlaylistItem(songId, playlistId);

            String sql = "insert into PlaylistItems (songID, playlistID) values (?, ?);";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, playlistItem.getSongId());
            pstat.setInt(2, playlistItem.getPlaylistId());
            pstat.executeUpdate();

        } catch (SQLServerException throwables) {
            //hrowables.printStackTrace();
            throw new DALexception("Couldn't create playlistItem", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't create playlistItem", throwables);
        }

        return playlistItem;
    }

    /**
     * Deletes given playlistItem (deleting song from a playlist)
     */
    public void deleteSong(PlaylistItem playlistItem) throws DALexception {

        try (Connection con = databaseConnector.getConnection()) {
            String sql = "DELETE FROM PlaylistItems WHERE songID=?, playlistID=?;";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, playlistItem.getSongId());
            pstat.setInt(2, playlistItem.getPlaylistId());

            pstat.executeUpdate();
        } catch (SQLServerException throwables) {
            throw new DALexception("Couldn't delete song", throwables);
           // throwables.printStackTrace();
        } catch (SQLException throwables) {
            throw new DALexception("Couldn't deleteSong", throwables);
            //throwables.printStackTrace();

        }
    }

}
