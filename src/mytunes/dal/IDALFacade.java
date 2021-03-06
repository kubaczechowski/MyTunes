package mytunes.dal;

import mytunes.be.Playlist;
import mytunes.be.PlaylistItem;
import mytunes.be.Song;
import mytunes.dal.exception.DALexception;

import java.util.List;

/**
 * facade in DAO package

 */

public interface IDALFacade {

    //PlaylistDAO
    List<Playlist> getAllPlaylists() throws DALexception;

    Playlist createPlaylist(String playName) throws DALexception;

    void deletePlaylist(Playlist playlist) throws DALexception;

    void updatePlaylistName(Playlist playlist, String newPlaylistName) throws DALexception;

    int getNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception;

    double getTotalTimeOnPlaylist(Playlist playlist) throws DALexception;

    //updating a playlist when we add a new item
   void updateTotalTimeOnPlaylistADD(Playlist playlist, int addedSongTime) throws DALexception;

    void incrementTheNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception;

    void decrementTheNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception;
    void updateTotalTimeOnPlaylistREMOVE(Playlist playlist, int addedSongTime) throws DALexception;

    Playlist getPlaylist(int id) throws DALexception;

    //songDAO
    List<Song> getAllSongs() throws DALexception;

    void createSong(String title, String artist,
                    String category, int time, String filePath, String imagePath) throws DALexception;

    void deleteSong(Song song) throws DALexception;

    void updateSong(Song song, String title,
               String artist, String category, String filePath, String imagePath) throws DALexception;

    Song getSong(int id) throws DALexception;

    int getSongTime(String mediaStringUrl);

    //PlaylistItemDAO
    List<PlaylistItem> getAllPlaylistItems() throws DALexception;

    PlaylistItem createPlaylistItem(int songId, int playlistId) throws DALexception;

    void deletePlaylistItem(int songId, int playlistId) throws DALexception;

    void safePlaylistDelete(Playlist playlist) throws DALexception;

    void safeSongDelete(Song song) throws DALexception;
}
