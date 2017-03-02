package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.Topping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 02/03/2017.
 */
public class ToppingRepository implements Repository<Topping> {
    private final HyggeDb db;
    private static Repository<Topping> instance;
    private List<Topping> persistedEntities = new ArrayList<>();

    public static Repository<Topping> getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new ToppingRepository(db);
        } else {
            return instance;
        }
    }
    private ToppingRepository(HyggeDb db) {
        this.db = db;
        instance = this;
    }

    @Override
    public Topping getById(int id) {
        Selection selection = new Selection("topping");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapTopping(rs);
    }
    private Topping mapTopping(ResultSet rs) {
        Topping topping = null;
        try {
            if (rs.next()) {
                topping = new Topping();
                topping.setId(rs.getInt("id"));
                topping.setName(rs.getString("name"));
                topping.setPrice(rs.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topping;
    }

    @Override
    public List<Topping> findAll() {
        Selection selection = new Selection("topping");
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapUsers(rs);
    }
    private List<Topping> mapUsers(ResultSet rs) {
        List<Topping> toppings = new ArrayList<>();
        Topping user = mapTopping(rs);
        while (user != null) {
            toppings.add(user);
            user = mapTopping(rs);
        }
        return toppings;
    }

    @Override
    public List<Topping> findBy(Condition condition) {
        return null;
    }

    @Override
    public void persist(Topping entity) {
        persistedEntities.add(entity);
    }

    @Override
    public void persistAndFlush(Topping entity) {
        persist(entity);
        flush();
    }

    @Override
    public void flush() {
        Object[] objects;
        String sql;
        for (Topping topping : persistedEntities) {
            sql = "INSERT INTO `order`(`name`, `price`) VALUES (?,?)";
            objects = new Object[2];
            objects[0] = topping.getName();
            objects[1] = topping.getPrice();
            int id = db.getInsertionExecutor().insert(sql, objects);
            topping.setId(id);
        }
    }
}
