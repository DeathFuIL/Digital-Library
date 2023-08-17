package ru.rasim.repositories;

import java.util.List;

public interface CrudRepository<T> {
    List<T> showAll();
    boolean save(T model);
    T show(Integer id);
    boolean update(Integer id, T updatedModel);
    boolean delete(Integer id);
}
