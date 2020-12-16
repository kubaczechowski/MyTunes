package mytunes.dal.interfaces;

import mytunes.be.Playlist;
import mytunes.be.PlaylistItem;
import mytunes.be.Song;
import mytunes.dal.exception.DALexception;

import java.util.List;

public interface IPlaylistItemRepository {

    List<PlaylistItem> getAllPlaylistItems() throws DALexception;

    PlaylistItem createPlaylistItem(int songId, int playlistId) throws DALexception;

    void deletePlaylistItem(int songId, int playlistId) throws DALexception;

    void safePlaylistDelete(Playlist playlist) throws DALexception;

    void safeSongDelete(Song song) throws DALexception;
}
