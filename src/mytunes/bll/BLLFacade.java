package mytunes.bll;


import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.exeption.BLLexception;
import mytunes.dal.exception.DALexception;

import java.util.List;

public interface BLLFacade {
    //PlaylistManager
    List<Playlist> getAllPlaylists() throws BLLexception;

    void deletePlaylist(Playlist playlist) throws BLLexception;

    Playlist newPlaylist(String name) throws BLLexception;

    void updatePlaylist(String name, Playlist playlist) throws BLLexception;

    int getNumberOfSongsOnPlaylist(Playlist playlist) throws BLLexception;

    double getTotalTimeOnPlaylist(Playlist playlist) throws BLLexception;

    //songManager
    List<Song> getAllSongs() throws BLLexception;

    int getSongTime(String mediaStringUrl);

    void save(Song song) throws BLLexception;

    void delete(Song songToBeDeleted) throws BLLexception;






}
