package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunes.be.Playlist;
import mytunes.be.PlaylistItem;
import mytunes.be.Song;
import mytunes.dal.exception.DALexception;
import mytunes.dal.interfaces.IPlaylistRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistRepository {
    private DatabaseConnector databaseConnector;
    private PlaylistItemDAO playlistItemDAO;

    public PlaylistDAO()  {
        databaseConnector = new DatabaseConnector();
        playlistItemDAO = new PlaylistItemDAO();
    }

    /**
     * for each playlist we simply get information about everything except
     * the list of songs which has to be rertived from table
     * PlaylistItems and theere WHERE PlaylistID=? we need to select all
     * the songs and assign them to this very playlist
     *
     * instead if that ^ we will use a method form PlaylistItemDAO
     * getSongsFromSpecificPlaylist(int playlistID)
     * @return
     * @throws DALexception
     */
    public List<Playlist> getAllPlaylists() throws DALexception
    {
        ArrayList<Playlist> allPlaylists = new ArrayList<>();
        try(Connection con = databaseConnector.getConnection())
        {
            String sql = "SELECT * FROM Playlists;";
            PreparedStatement pstat = con.prepareStatement(sql);
            ResultSet resultSet = pstat.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("playName");
                List<Song> songsList = playlistItemDAO.getSongsFromSpecificPlaylist(id);
                int numberOfSongs = resultSet.getInt("numberOfSongs");
                int totalPlaytime = resultSet.getInt("totalPlaytime");
                Playlist playlist = new Playlist(id, name, songsList,
                        numberOfSongs, totalPlaytime );
                allPlaylists.add(playlist);
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allPlaylists;
    }


    public Playlist createPlaylist(String playName) throws DALexception {
        //create object later
        Playlist newPlaylist = null;

        try (Connection con = databaseConnector.getConnection()) {
            String sqlCommand = "INSERT INTO Playlists (playName) VALUES(?)";
            PreparedStatement preparedStatement = con.prepareStatement(sqlCommand,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, playName);
            int checkIfExecuted = preparedStatement.executeUpdate();

            if(checkIfExecuted<1)
            {
                throw new DALexception("Couldn't create a playlist");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);

            //this are initial values when a song is created
            newPlaylist = new Playlist(id, playName, null,
                    0, 0);
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't create a playlist", throwables);
        } catch (SQLException | DALexception throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't create a playlist", throwables);
        }

        if(newPlaylist==null)
                throw new DALexception("Playlist is still empty. The name of the playlist: "+
                        playName);
        return  newPlaylist;
    }

    /**
     * I deliberately pass the object because it will be selected in the ListView.
     * I believe its a better idea.
     * @param playlist
     */
    public void deletePlaylist(Playlist playlist) throws DALexception {
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Playlists WHERE id=?;";
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1, playlist.getId());
            pstat.execute();
            //ResultSet result = pstat.executeUpdate(sql);


        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete playlist", throwables);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete playlist", throwables);
        }
    }

    public void updatePlaylistName(Playlist playlist, String newPlaylistName) throws DALexception {
        String sql = "UPDATE Playlists SET playName=? WHERE id=?";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql);) {

            preparedStatement.setString(1, newPlaylistName);
            preparedStatement.setInt(2, playlist.getId());

            preparedStatement.execute();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update playlist name", throwables);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update playlist name", throwables);
        }
    }

    public Playlist getPlaylist(int id) throws DALexception {
        String sql = "SELECT * FROM Playlists WHERE id=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql);) {
                preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                String name = resultSet.getString("playName");
                List<Song> songsList = playlistItemDAO.getSongsFromSpecificPlaylist(id);
                int numberOfSongs = resultSet.getInt("numberOfSongs");
                int totalPlaytime = resultSet.getInt("totalPlaytime");
                Playlist playlist = new Playlist(id, name, songsList,
                        numberOfSongs, totalPlaytime);
                return playlist;
            }
        } catch (SQLServerException throwables) {
            throw new DALexception("Couldn't get get the playlist", throwables);
        } catch (SQLException throwables) {
            throw new DALexception("Couldn't get get the playlist", throwables);
        }

        return null;
    }


    public int getNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception {

        try (Connection con = databaseConnector.getConnection()) {
            Statement statement = con.createStatement();
            String sql = "SELECT COUNT(*) AS row_count FROM Playlists;";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            int numberOfSongs = resultSet.getInt("row_count");
            return  numberOfSongs;

        } catch (SQLServerException throwables) {
           // throwables.printStackTrace();
            throw new DALexception("Couldn't get the number of songs on the playlist", throwables);
        } catch (SQLException throwables) {
           // throwables.printStackTrace();
            throw new DALexception("Couldn't get the number of songs on the playlist", throwables);
        }

    }

    public double getTotalTimeOnPlaylist(Playlist playlist) throws DALexception {
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT SUM(playlistID) FROM PlaylistItems WHERE songID=?; ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, playlist.getId());

            double totalTime = preparedStatement.executeUpdate();
            return totalTime;

        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't get total time on the playlist", throwables);
        } catch (SQLException throwables) {
           // throwables.printStackTrace();
            throw new DALexception("Couldn't get total time on the playlist", throwables);
        }
    }

    /**
     * method will be called when adding a new item to the Playlist ListView
     * @param playlist
     * @param addedSongTime
     * @return
     */
    public void updateTotalTimeOnPlaylistADD(Playlist playlist, int addedSongTime) throws DALexception {
        String sql = "UPDATE Playlists SET totalPlaytime=? WHERE id=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setInt(1, addedSongTime+playlist.getTotalPlaytime());
            preparedStatement.setInt(2, playlist.getId());

            preparedStatement.execute();
        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't update total playtime of playlist", throwables);
        } catch (SQLException throwables) {
            throw new DALexception("Couldn't update total playtime of playlist", throwables);
        }
      //  return playlist.getTotalPlaytime() +addedSongTime;
    }

    /**
     * method will be called when adding a new item to the Playlist ListView
     * @param playlist
     * @return
     */
    public void incrementTheNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception {
        String sql = "UPDATE playlists SET numberOfSongs=? WHERE id=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, playlist.getNumberOfSongs()+1);
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.execute();

        } catch (SQLServerException throwables) {
           // throwables.printStackTrace();
            throw new DALexception("Couldn't increament (it means refresh) the number of songs on" +
                    "the playlist:" + playlist.getName(), throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't increament (it means refresh) the number of songs on" +
                    "the playlist:" + playlist.getName(), throwables);
        }
       // return playlist.getNumberOfSongs()+1;
    }

    /**
     * method will be called when removing an item from Playlist ListView
     * then row in Playlits table is updated with current number of songs
     * @param playlist
     * @param deletedSongTime
     */
    public void updateTotalTimeOnPlaylistREMOVE(Playlist playlist, int deletedSongTime) throws DALexception {
        String sql = "UPDATE Playlists SET totalPlaytime=? WHERE id=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setInt(1, playlist.getTotalPlaytime() - deletedSongTime);
            preparedStatement.setInt(2, playlist.getId());

            preparedStatement.execute();
        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't update total playtime of playlist", throwables);
        } catch (SQLException throwables) {
            throw new DALexception("Couldn't update total playtime of playlist", throwables);
        }
    }

    /**
     * method will be called when removing an item from Playlist ListView.
     * then row in Playlits table is updated with current number of songs
     * @param playlist
     * @return
     */
    public void decrementTheNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception {
        String sql = "UPDATE playlists SET numberOfSongs=? WHERE id=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, playlist.getNumberOfSongs()-1);
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.execute();

        } catch (SQLServerException throwables) {
            // throwables.printStackTrace();
            throw new DALexception("Couldn't increament (it means refresh) the number of songs on" +
                    "the playlist:" + playlist.getName(), throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't increament (it means refresh) the number of songs on" +
                    "the playlist:" + playlist.getName(), throwables);
        }

    }



}


