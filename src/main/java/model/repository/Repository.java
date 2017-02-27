package model.repository;

import hyggedb.select.Condition;
import model.entity.Entity;

import java.util.List;

/**
 * Created by adam on 26/02/2017.
 */
public interface Repository<K extends Entity> {
    public K getById(int id);

    public List<K> findAll();

    public List<K> findBy(Condition condition);

    public void persist(K entity);

    public void persistAndFlush(K entity);

    public void flush();


}