package mytunes.gui.model;

import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.PlaylistItemManager;
import mytunes.bll.exeption.BLLexception;



public class PlaylistItemModel {
    private static PlaylistItemModel playlistItemModel;
    //private ObservableMap<Playlist, List<Song>> observableMap;
    private PlaylistItemManager playlistItemManager;
    private  SongModel songModel;



    public PlaylistItemModel()
    {
        playlistItemManager = new PlaylistItemManager();

        songModel = SongModel.createOrGetInstance();
    }

    public static PlaylistItemModel createOrGetInstance() {
        if(  playlistItemModel == null)
        {
            playlistItemModel = new PlaylistItemModel();
            return playlistItemModel;
        }
        else
            return playlistItemModel;
    }
/*
    public ObservableMap<Playlist, List<Song>> getAllPlaylistItems(){
        return observableMap;
    }

 */

    /**
     * method loads the ObservableMap with the neccessary data
     */
    /*
    public void load()
    {
        //load playlists as keys
        for(Playlist playlist: playlistModel.getAllPlaylists())
        {
            observableMap.keySet().add(playlist);

            List<Song> songs = new ArrayList<>();
            for(Song song: playlist.getSongs())
            {


                //load  List<Song> as values
                observableMap.entrySet().add(playlist, songs )
            }

        }

    }

     */
    public void addPlaylistItem(Playlist playlist, Song song)
    {
        //add to DB
        try {
            playlistItemManager.add(song.getId(), playlist.getId());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }

        //AD to map


    }
    public void deletePlaylistItem(Playlist playlist, Song song)
    {
        //remove from DB
        try {
            playlistItemManager.remove(song.getId(), playlist.getId());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }

        //remove from map

    }


}
