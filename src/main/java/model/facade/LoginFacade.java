package model.facade;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import model.Hasher;
import model.entity.User;
import model.exception.NonExistentEmailException;
import model.exception.WrongPasswordException;
import model.repository.Repository;
import model.repository.UserRepository;

import java.util.List;

/**
 * Created by adam on 28/02/2017.
 */
public class LoginFacade {
    private final HyggeDb db;

    public LoginFacade(HyggeDb db) {
        this.db = db;
    }

    public User loginUser(String email, String password) throws WrongPasswordException, NonExistentEmailException {
        Repository<User> userRepository = userRepository = UserRepository.getInstance(db);
        Condition condition = new Condition("", "user", "email=?", email);
        List<User> users = userRepository.findBy(condition);
        if (!users.isEmpty()) {
            User user = users.get(0);
            Hasher hasher = new Hasher();
            String insertedPassword = hasher.hashPassword(password,user.getSalt());
            if (insertedPassword.equals(user.getPassword())) {
                return user;
            } else {
                throw new WrongPasswordException();
            }
        } else {
            throw new NonExistentEmailException();
        }
    }
}
