package ru.rasim.repositories;

import java.util.List;

public interface CrudRepository<T> {
    List<T> showAll();
    Long save(T model);
    T show(Long id);
    boolean update(Long id, T updatedModel);
    boolean delete(Long id);
}
