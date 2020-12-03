package mytunes.bll;

import mytunes.be.Playlist;
import mytunes.bll.exeption.BLLexception;

import java.util.List;

public class BLLcontroller implements BLLFacade{
    @Override
    public List<Playlist> getAllPlaylists() throws BLLexception {
        return null;
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws BLLexception {

    }

    @Override
    public Playlist newPlaylist(String name) throws BLLexception {
        return null;
    }

    @Override
    public void updatePlaylist(String name, Playlist playlist) throws BLLexception {

    }
}
