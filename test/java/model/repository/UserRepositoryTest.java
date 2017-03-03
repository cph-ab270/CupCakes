package model.repository;

import hyggedb.HyggeDb;
import model.Connector;
import model.entity.User;
import model.exception.EmailTakenException;
import model.facade.RegisterFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adam on 03/03/2017.
 */
class UserRepositoryTest {
    private HyggeDb db;
    private Repository<User> userRepository;

    @BeforeEach
    void setUp() {
        db = new HyggeDb(new Connector("localhost","cba_cupcake_test","root","root"));
        userRepository = UserRepository.getInstance(db);
    }

    @Test
    void testInsert() throws EmailTakenException {
        RegisterFacade registerFacade = new RegisterFacade(db);
        Map attributes = new HashMap<>();
        attributes.put("email","email@email.com");
        attributes.put("name","test");
        attributes.put("surname","test surname");
        attributes.put("password","password");
        User user = registerFacade.registerUser(attributes);
        User insertedUser = userRepository.getById(user.getId());
        Assertions.assertEquals("email@email.com",insertedUser.getEmail());
    }

    @Test
    void testUpdate() throws EmailTakenException {
        User user = userRepository.getById(1);
        user.setName("changed name");
        userRepository.persistAndFlush(user);
        User updatedUser = userRepository.getById(user.getId());
        Assertions.assertEquals("changed name",updatedUser.getName());
    }

    @AfterEach
    void tearDown() {
        db.getInsertionExecutor().insert("DELETE FROM user WHERE id >?",new Object[]{1});
        db.getInsertionExecutor().update("UPDATE user SET name=? WHERE id >?",new Object[]{"Adam",1});
    }
}