package dao.interfaces;

import beans.Exhibitions;

import java.util.List;

public interface ExhibitionDAO {
    public void create(Exhibitions exhibition);

    public void update(Exhibitions exhibition);

    public void delete(int id);

    public Exhibitions getById(int id);

    public List<Exhibitions> getAll();
}
