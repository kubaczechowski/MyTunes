package mytunes.dal;

import mytunes.be.Playlist;
import mytunes.be.PlaylistItem;
import mytunes.be.Song;
import mytunes.dal.exception.DALexception;

import java.util.List;

public interface IDALFacade {

    //PlaylistDAO
    List<Playlist> getAllPlaylists() throws DALexception;

    Playlist createPlaylist(String playName) throws DALexception;

    void deletePlaylist(Playlist playlist) throws DALexception;

    void updatePlaylistName(Playlist playlist, String newPlaylistName) throws DALexception;


    //songDAO
    List<Song> getAllSongs() throws DALexception;

    Song createSong(String title, String artist,
                    String category, String filePath) throws DALexception;

    void deleteSong(Song song) throws DALexception;

    void updateSong(Song song, String title,
               String artist, String category, String filePath) throws DALexception;

    Song getSong(int id) throws DALexception;

    //PlaylistItemDAO
    List<PlaylistItem> getAllPlaylistItems();

    PlaylistItem createPlaylistItem(int songId, int playlistId);

    void deleteSong(PlaylistItem playlistItem);
}
