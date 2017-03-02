package model.facade;

import hyggedb.HyggeDb;
import hyggedb.select.Selection;
import model.Hasher;
import model.entity.User;
import model.exception.EmailTakenException;
import model.repository.Repository;
import model.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by adam on 28/02/2017.
 */
public class RegisterFacade {
    private final HyggeDb db;
    private final HttpServletRequest request;

    public RegisterFacade(HyggeDb db, HttpServletRequest request) {
        this.db = db;
        this.request = request;
    }

    public User registerUser() throws EmailTakenException {
        Repository<User> userRepository = UserRepository.getInstance(db);
        String email = request.getParameter("email");
        if (emailIsFree(email)) {
            User user = mapUser(email);
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
    private User mapUser(String email) {
        Hasher hasher = new Hasher();
        String salt = hasher.generateSalt();
        String hashedPassword = hasher.hashPassword(request.getParameter("password"), salt);
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setEmail(email);
        user.setType(1);
        user.setStatus(1);
        user.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setBalance(500);
        return user;
    }
}
