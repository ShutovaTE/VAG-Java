package dao.interfaces;

import beans.Admin;

import java.util.List;

public interface AdminDAO {
    void create(Admin admin);

    void update(Admin admin);

    void delete(int id);

    Admin getById(int id);

    List<Admin> getAll();
}
