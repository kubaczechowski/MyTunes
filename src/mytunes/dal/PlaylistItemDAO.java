package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
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

    public List<Song> getSongsFromSpecificPlaylist(int playlistID)
    {
        List<Song> songsOnPlaylist = null;

        try (Connection con = databaseConnector.getConnection()) {
            //at first we need to collect all songIDs that match one playlist
            //the key: SongID the value: Song
            HashMap<Integer, Song> map = new HashMap<>();

            String sql1 = "SELECT songID FROM PlaylistItems WHERE playlistID=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql1);
            preparedStatement.setInt(1, playlistID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int songID = resultSet.getInt("songID");
                map.keySet().add(songID);
            }
            //finished adding keys: SongID

            //add values --> songs to the hashmap
            //retrieve songs from the list of songs that have the SongID
            //we need to iterate through it

            //piszesz komende i zamiast while dajesz for i cyk iterujesz
            String sql2 = "Select * Songs WHERE SongID=?;";
            PreparedStatement preparedStatement2 = con.prepareStatement(sql2);

            /*
            for( condition)
            {
            reparedStatement.setInt(1, playlistID);
            ResultSet resultSet = preparedStatement.executeQuery();
            }
             */
            for(int key: map.keySet())
            {
                preparedStatement2.setInt(1, key);
                ResultSet resultSet1 = preparedStatement2.executeQuery();

                int id = resultSet1.getInt("id");
                String title = resultSet1.getString("title");
                String artist = resultSet1.getString("artist");
                String category = resultSet1.getString("category");
                int playTime = resultSet1.getInt("playTime");
                String filePath = resultSet1.getString("filePath");
                String imagePath = resultSet1.getString("imagePath");
                Song song = new Song(id, title, artist, category, playTime, filePath, imagePath);

                map.put(key, song);

            }

            //cast Hashmap values to a list and return it
             songsOnPlaylist = new ArrayList<Song>(map.values());


        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  songsOnPlaylist;

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
