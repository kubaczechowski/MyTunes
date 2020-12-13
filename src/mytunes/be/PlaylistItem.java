package mytunes.be;

public class PlaylistItem {

    private int songId;
    private int playlistId;

    public PlaylistItem(int songId, int playlistId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }
}
