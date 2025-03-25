package dao.interfaces;

import beans.Exhibition;

import java.util.List;

public interface ExhibitionDAO {
    public void create(Exhibition exhibition);

    public void update(Exhibition exhibition);

    public void delete(int id);

    public Exhibition getById(int id);

    public List<Exhibition> getAll();
}
