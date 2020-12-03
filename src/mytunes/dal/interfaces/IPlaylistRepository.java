package mytunes.dal.interfaces;

import mytunes.be.Playlist;
import mytunes.dal.exception.DALexception;

import java.util.List;

public interface IPlaylistRepository {
    List<Playlist> getAllPlaylists() throws DALexception;

    Playlist createPlaylist(String playName) throws DALexception;

    void deletePlaylist(Playlist playlist) throws DALexception;

    void updatePlaylistName(Playlist playlist, String newPlaylistName) throws DALexception;
}
