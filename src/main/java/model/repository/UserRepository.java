package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by adam on 26/02/2017.
 */
public class UserRepository implements Repository<User> {
    private final HyggeDb db;
    private static Repository<User> instance;

    private Collection<User> persistedEntities = new ArrayList<>();

    public static Repository<User> getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new UserRepository(db);
        } else {
            return instance;
        }
    }
    private UserRepository(HyggeDb db) {
        this.db = db;
        instance = this;
    }

    public User getById(int id) {
        Selection selection = new Selection("user");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapUser(rs);
    }

    private User mapUser(ResultSet rs) {
        User user = null;
        try {
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCreatedAt(rs.getDate("created_at"));
                user.setStatus(rs.getInt("status"));
                user.setType(rs.getInt("type"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> findAll() {
        return null;
    }

    public List<User> findBy(Condition condition) {
        Selection selection = new Selection("user");
        selection.setWhere(condition);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapUsers(rs);
    }

    private List<User> mapUsers(ResultSet rs) {
        List<User> users = new ArrayList<User>();
        User user = mapUser(rs);
        while (user != null) {
            users.add(user);
            user = mapUser(rs);
        }
        return users;
    }

    public void persist(User entity) {
        persistedEntities.add(entity);
    }

    public void persistAndFlush(User entity) {
        persist(entity);
        flush();
    }

    public void flush() {
        Object[] objects;
        String sql;
        for (User user : persistedEntities) {
            sql = "INSERT INTO `user`(`name`, `surname`, `created_at`, `status`, `type`, `email`, `password`, `salt`) VALUES (?,?,?,?,?,?,?,?)";
            objects = new Object[8];
            objects[0] = user.getName();
            objects[1] = user.getSurname();
            objects[2] = user.getCreatedAt();
            objects[3] = user.getStatus();
            objects[4] = user.getType();
            objects[5] = user.getEmail();
            objects[6] = user.getPassword();
            objects[7] = user.getSalt();
            int id = db.getInsertionExecutor().insert(sql, objects);
            user.setId(id);
        }
    }
}
