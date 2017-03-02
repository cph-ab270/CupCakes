package model.repository;

import hyggedb.select.Condition;
import model.entity.Entity;

import java.util.List;

/**
 * Created by adam on 26/02/2017.
 */
public interface Repository<K extends Entity> {
    K getById(int id);

    List<K> findAll();

    List<K> findBy(Condition condition);

    void persist(K entity);

    void persistAndFlush(K entity);

    void flush();


}