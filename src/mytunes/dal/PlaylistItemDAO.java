package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunes.be.Playlist;
import mytunes.be.PlaylistItem;
import mytunes.be.Song;
import mytunes.dal.exception.DALexception;
import mytunes.dal.interfaces.IPlaylistItemRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
     * most important method in this class. it retrives all songs that are
     * on the playlist
     * @param playlistID
     * @return
     * @throws DALexception
     */
    public List<Song> getSongsFromSpecificPlaylist(int playlistID) throws DALexception {
        List<Integer> songIds = new ArrayList<>();

        //get list of songIds and save it to the list
        String sql1 = "SELECT songID FROM PlaylistItems WHERE playlistID=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql1))
        {
         preparedStatement.setInt(1, playlistID);
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next())
         {
             int songID = resultSet.getInt("songID");
             songIds.add(songID);
         }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Song> songsOnPlaylist = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            for(Integer integer: songIds)

            {  // String sql2 = "SELECT * FROM Songs WHERE id=" + integer+ ";" ;

                String sql2 = "SELECT * FROM Songs WHERE id=?;" ;

                PreparedStatement preparedStatement = con.prepareStatement(sql2);
                preparedStatement.setInt(1, integer);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String artist = rs.getString("artist");
                    String category = rs.getString("category");
                    int playTime = rs.getInt("playtime");
                    String filePath = rs.getString("filePath");
                    String imagePath = rs.getString("imagePath");
                    Song song = new Song(id, title, artist, category, playTime, filePath, imagePath);
                    songsOnPlaylist.add(song);
                }
            }

        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("coudlnt get songs on teh playlist", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("coudlnt get songs on teh playlist", throwables);
        }
        return songsOnPlaylist;
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
    public void deletePlaylistItem(int songId, int playlistId) throws DALexception {

        try (Connection con = databaseConnector.getConnection()) {
            String sql = "DELETE FROM PlaylistItems WHERE (songID=? AND playlistID=?);";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, songId);
            pstat.setInt(2, playlistId);

            pstat.executeUpdate();
        } catch (SQLServerException throwables) {
            throw new DALexception("Couldn't delete song", throwables);
           // throwables.printStackTrace();
        } catch (SQLException throwables) {
            throw new DALexception("Couldn't deleteSong", throwables);
            //throwables.printStackTrace();

        }
    }

    /**
     * Deletes all playlistItems with a given playlistID (deleting a whole playlist)
     */

    public void safePlaylistDelete(Playlist playlist) throws DALexception{
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "DELETE FROM PlaylistItems WHERE (playlistID=?);";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, playlist.getId());

            pstat.executeUpdate();
        } catch (SQLServerException throwables) {
            throw new DALexception("Couldn't delete song", throwables);
            // throwables.printStackTrace();
        } catch (SQLException throwables) {
            throw new DALexception("Couldn't deleteSong", throwables);
            //throwables.printStackTrace();

        }
    }

    public void safeSongDelete(Song song) throws DALexception{
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "DELETE FROM PlaylistItems WHERE (songID=?);";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, song.getId());

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
