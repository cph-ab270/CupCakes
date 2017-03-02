package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.Bottom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 02/03/2017.
 */
public class BottomRepository implements Repository<Bottom> {
    private final HyggeDb db;
    private static Repository<Bottom> instance;
    private List<Bottom> persistedEntities = new ArrayList<>();

    public static Repository<Bottom> getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new BottomRepository(db);
        } else {
            return instance;
        }
    }
    private BottomRepository(HyggeDb db) {
        this.db = db;
        instance = this;
    }

    @Override
    public Bottom getById(int id) {
        Selection selection = new Selection("bottom");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapBottom(rs);
    }
    private Bottom mapBottom(ResultSet rs) {
        Bottom bottom = null;
        try {
            if (rs.next()) {
                bottom = new Bottom();
                bottom.setId(rs.getInt("id"));
                bottom.setName(rs.getString("name"));
                bottom.setPrice(rs.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bottom;
    }

    @Override
    public List<Bottom> findAll() {
        Selection selection = new Selection("bottom");
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapUsers(rs);
    }
    private List<Bottom> mapUsers(ResultSet rs) {
        List<Bottom> bottoms = new ArrayList<>();
        Bottom user = mapBottom(rs);
        while (user != null) {
            bottoms.add(user);
            user = mapBottom(rs);
        }
        return bottoms;
    }

    @Override
    public List<Bottom> findBy(Condition condition) {
        return null;
    }

    @Override
    public void persist(Bottom entity) {
        persistedEntities.add(entity);
    }

    @Override
    public void persistAndFlush(Bottom entity) {
        persist(entity);
        flush();
    }

    @Override
    public void flush() {
        Object[] objects;
        String sql;
        for (Bottom bottom : persistedEntities) {
            sql = "INSERT INTO `bottom`(`name`, `price`) VALUES (?,?)";
            objects = new Object[2];
            objects[0] = bottom.getName();
            objects[1] = bottom.getPrice();
            int id = db.getInsertionExecutor().insert(sql, objects);
            bottom.setId(id);
        }
    }
}
