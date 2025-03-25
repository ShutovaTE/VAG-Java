package dao.classes;

import beans.User;
import beans.Artworks;
import beans.Exhibitions;

import java.util.HashMap;
import java.util.Map;

public class DBImpl {
    public Map<Integer, User> artists;
    public Map<Integer, Artworks> artworks;
    public Map<Integer, Exhibitions> exhibitions;

    public DBImpl() {
        this.artists = new HashMap<Integer, User>();
        this.artworks = new HashMap<Integer, Artworks>();
        this.exhibitions = new HashMap<Integer, Exhibitions>();
    }

}
