package model.facade;

import hyggedb.HyggeDb;
import hyggedb.select.Selection;
import model.Hasher;
import model.entity.User;
import model.exception.EmailTakenException;
import model.repository.Repository;
import model.repository.UserRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by adam on 28/02/2017.
 */
public class RegisterFacade {
    private final HyggeDb db;

    public RegisterFacade(HyggeDb db) {
        this.db = db;
    }

    public User registerUser(Map attributes) throws EmailTakenException {
        Repository<User> userRepository = UserRepository.getInstance(db);
        if (emailIsFree((attributes.get("email").toString()))) {
            User user = mapUser(attributes);
            userRepository.persistAndFlush(user);
            return user;
        } else {
            throw new EmailTakenException();
        }
    }

    private boolean emailIsFree(String email) {
        Selection selection = new Selection("user");
        selection.where("email=?", email);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        try {
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User mapUser(Map attributes) {
        Hasher hasher = new Hasher();
        String salt = hasher.generateSalt();
        String hashedPassword = hasher.hashPassword(((String) attributes.get("password")), salt);
        User user = new User();
        user.setName(((String) attributes.get("name")));
        user.setSurname(((String) attributes.get("surname")));
        user.setEmail(((String) attributes.get("email")));
        user.setType(1);
        user.setStatus(1);
        user.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setBalance(500);
        return user;
    }
}
