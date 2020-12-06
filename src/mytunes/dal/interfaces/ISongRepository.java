package mytunes.dal.interfaces;

import mytunes.be.Song;
import mytunes.dal.exception.DALexception;

import java.util.List;

public interface ISongRepository {

    List<Song> getAllSongs() throws DALexception;

    Song createSong(String title, String artist,
                    String category, String filePath) throws DALexception;

    void deleteSong(Song song) throws DALexception;

    void updateSong(Song song, String title,
                    String artist, String category, String filePath) throws DALexception;

    Song getSong(int id) throws DALexception;

    void updateSong(int id);

    int getSongTime(String mediaStringUrl);
}
