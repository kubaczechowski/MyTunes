package mytunes.dal;

import mytunes.be.Playlist;

import mytunes.be.Song;
import mytunes.dal.exception.DALexception;

import mytunes.be.PlaylistItem;

import mytunes.dal.interfaces.IPlaylistItemRepository;

import mytunes.dal.interfaces.IPlaylistRepository;
import mytunes.dal.interfaces.ISongRepository;

import java.util.List;

public class DALcontroller implements IDALFacade {

    private IPlaylistRepository playlistAccess;
    private ISongRepository songAccess;
    private IPlaylistItemRepository playlistItemAccess;
    private IPlaylistItemRepository itemAccess;

    public DALcontroller() {
        playlistAccess = new PlaylistDAO();
        songAccess = new SongDAO();
        playlistItemAccess = new PlaylistItemDAO();
    }

    //PlaylistDAO

    @Override
    public List<Playlist> getAllPlaylists() throws DALexception {
        return  playlistAccess.getAllPlaylists();
    }

    @Override
    public Playlist createPlaylist(String playName) throws DALexception {
       return playlistAccess.createPlaylist(playName);
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws DALexception {
        playlistAccess.deletePlaylist(playlist);
    }

    @Override
    public void updatePlaylistName(Playlist playlist, String newPlaylistName) throws DALexception {
        playlistAccess.updatePlaylistName(playlist, newPlaylistName);
    }

    @Override
    public int getNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception {
        return  playlistAccess.getNumberOfSongsOnPlaylist(playlist);
    }

    @Override
    public double getTotalTimeOnPlaylist(Playlist playlist) throws DALexception {
        return playlistAccess.getTotalTimeOnPlaylist(playlist);
    }

    @Override
    public void updateTotalTimeOnPlaylistADD(Playlist playlist, int addedSongTime) throws DALexception {
        playlistAccess.updateTotalTimeOnPlaylistADD(playlist, addedSongTime);
    }

    @Override
    public void incrementTheNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception {
         playlistAccess.incrementTheNumberOfSongsOnPlaylist(playlist);
    }

    @Override
    public void decrementTheNumberOfSongsOnPlaylist(Playlist playlist) throws DALexception {
        playlistAccess.decrementTheNumberOfSongsOnPlaylist(playlist);
    }

    @Override
    public void updateTotalTimeOnPlaylistREMOVE(Playlist playlist, int addedSongTime) throws DALexception {
        playlistAccess.updateTotalTimeOnPlaylistREMOVE(playlist, addedSongTime);
    }

    @Override
    public Playlist getPlaylist(int id) throws DALexception {
        return playlistAccess.getPlaylist(id);
    }

    //songDAO
    @Override
    public List<Song> getAllSongs() throws DALexception {
        return  songAccess.getAllSongs();
    }

    @Override
    public void createSong(String title, String artist, String category, int time, String filePath, String imagePath) throws DALexception {
          songAccess.createSong(title, artist, category, time,  filePath, imagePath );
    }

    @Override
    public void deleteSong(Song song) throws DALexception {
        songAccess.deleteSong(song);

    }

    @Override
    public void updateSong(Song song, String title, String artist, String category, String filePath, String imagePath) throws DALexception {
        songAccess.updateSong(song, title, artist, category, filePath, imagePath);
    }

    @Override
    public Song getSong(int id) throws DALexception {
       return songAccess.getSong(id);
    }

    @Override
    public int getSongTime(String mediaStringUrl) {
        return songAccess.getSongTime(mediaStringUrl);
    }

    //PlaylistItemDAO
    @Override
    public List<PlaylistItem> getAllPlaylistItems() throws DALexception {
        return playlistItemAccess.getAllPlaylistItems();
    }

    @Override
    public PlaylistItem createPlaylistItem(int songId, int playlistId) throws DALexception {
        return playlistItemAccess.createPlaylistItem(songId, playlistId);
    }

    @Override
    public void deletePlaylistItem(int songId, int playlistId) throws DALexception {
            playlistItemAccess.deletePlaylistItem( songId,  playlistId);
    }

    @Override
    public void safePlaylistDelete(Playlist playlist) throws DALexception {
        playlistItemAccess.safePlaylistDelete(playlist);
    }

    @Override
    public void safeSongDelete(Song song) throws DALexception {
        playlistItemAccess.safeSongDelete(song);
    }

}
