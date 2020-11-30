package mytunes.dal;

import mytunes.be.Song;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongsDAO_DB {
    private DatabaseConnector databaseConnector;

    public SongsDAO_DB()  {
        databaseConnector = new DatabaseConnector();
    }

    public List<Song> getAllSongs() {
        ArrayList<Song> allSongs = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Movies;";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                String title = rs.getString("title");
                String artist = rs.getString("title");
                String category = rs.getString("title");
               double time = rs.getDouble("time");
                String filePath = rs.getString("filePath");
               Song song = new Song(title, artist, category, time, filePath);
                allSongs.add(song);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allSongs;
    }

}
