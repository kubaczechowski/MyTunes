package mytunes.dal.interfaces;

import mytunes.be.PlaylistItem;
import mytunes.dal.exception.DALexception;

import java.util.List;

public interface IPlaylistItemRepository {

    List<PlaylistItem> getAllPlaylistItems() throws DALexception;

    PlaylistItem createPlaylistItem(int songId, int playlistId) throws DALexception;

    void deletePlaylistItem(int songId, int playlistId) throws DALexception;

}
