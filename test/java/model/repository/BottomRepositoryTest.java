package model.repository;

import hyggedb.HyggeDb;
import model.Connector;
import model.entity.Bottom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Created by adam on 02/03/2017.
 */
class BottomRepositoryTest {
    private HyggeDb db;
    private Repository<Bottom> bottomRepository;

    @BeforeEach
    void setUp() {
        db = new HyggeDb(new Connector("localhost","cba_cupcake_test","root","root"));
        bottomRepository = BottomRepository.getInstance(db);
    }

    @Test
    void getById() {
        Bottom bottom = bottomRepository.getById(1);
        Assertions.assertEquals("cherry",bottom.getName());
    }

    @Test
    void findAll() {
        List<Bottom> bottoms = bottomRepository.findAll();
        Bottom bottom = bottoms.get(1);
        Assertions.assertEquals("berry",bottom.getName());
    }

    @Test
    void flush() {
        Bottom bottom = new Bottom();
        bottom.setName("test");
        bottom.setPrice(100);
        bottomRepository.persistAndFlush(bottom);

        bottom = bottomRepository.getById(bottom.getId());
        Assertions.assertEquals("test",bottom.getName());
    }
}