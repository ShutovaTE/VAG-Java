package dao.classes;

import beans.Artist;
import beans.Artwork;
import beans.Exhibition;

import java.util.HashMap;
import java.util.Map;

public class DBImpl {
    public Map<Integer, Artist> artists;
    public Map<Integer, Artwork> artworks;
    public Map<Integer, Exhibition> exhibitions;

    public DBImpl() {
        this.artists = new HashMap<Integer, Artist>();
        this.artworks = new HashMap<Integer, Artwork>();
        this.exhibitions = new HashMap<Integer, Exhibition>();
    }

}
