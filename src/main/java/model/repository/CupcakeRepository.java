package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.Bottom;
import model.entity.Cupcake;
import model.entity.Topping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 02/03/2017.
 */
public class CupcakeRepository implements Repository<Cupcake>{
    private final HyggeDb db;
    private Repository<Bottom> bottomRepository;
    private Repository<Topping> toppingRepository;
    private List<Cupcake> persistedEntities = new ArrayList<>();
    private static CupcakeRepository instance;

    public static CupcakeRepository getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new CupcakeRepository(db);
        } else {
            return instance;
        }
    }
    private CupcakeRepository(HyggeDb db) {
        this.db = db;
        instance = this;
        bottomRepository = BottomRepository.getInstance(db);
        toppingRepository = ToppingRepository.getInstance(db);
    }

    @Override
    public Cupcake getById(int id) {
        Selection selection = new Selection("cupcake");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapCupcake(rs);
    }
    private Cupcake mapCupcake(ResultSet rs) {
        Cupcake user = null;
        try {
            if (rs.next()) {
                user = new Cupcake();
                user.setId(rs.getInt("id"));
                user.setUserId(rs.getInt("user_id"));
                user.setStatus(rs.getInt("status"));
                user.setAmount(rs.getInt("amount"));
                Bottom bottom = bottomRepository.getById(rs.getInt("bottom_id"));
                Topping topping = toppingRepository.getById(rs.getInt("topping_id"));
                user.setBottom(bottom);
                user.setTopping(topping);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<Cupcake> findAll() {
        Selection selection = new Selection("cupcake");
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapCupcakes(rs);
    }
    public List<Cupcake> findByInvoiceId(int invoiceId) {
        Selection selection = new Selection("cupcake","*");
        selection
                .join("invoice_cupcake","id","cupcake_id")
                .join("invoice")
                    .where("id=?",invoiceId);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapCupcakes(rs);
    }
    private List<Cupcake> mapCupcakes(ResultSet rs) {
        List<Cupcake> cupcakes = new ArrayList<>();
        Cupcake cupcake = mapCupcake(rs);
        while (cupcake != null) {
            cupcakes.add(cupcake);
            cupcake = mapCupcake(rs);
        }
        return cupcakes;
    }

    @Override
    public List<Cupcake> findBy(Condition condition) {
        Selection selection = new Selection("cupcake");
        selection.setWhere(condition);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapCupcakes(rs);
    }

    @Override
    public void persist(Cupcake entity) {
        persistedEntities.add(entity);
    }

    @Override
    public void persistAndFlush(Cupcake entity) {
        persist(entity);
        flush();
    }

    @Override
    public void flush() {
        Object[] objects;
        String sql;
        for (Cupcake cupcake : persistedEntities) {
            sql = "INSERT INTO `cupcake`(`topping_id`, `bottom_id`, `user_id`, `status`, `amount`) VALUES (?,?,?,?,?)";
            objects = new Object[5];
            objects[0] = cupcake.getTopping().getId();
            objects[1] = cupcake.getBottom().getId();
            objects[2] = cupcake.getUserId();
            objects[3] = cupcake.getStatus();
            objects[4] = cupcake.getAmount();
            int id = db.getInsertionExecutor().insert(sql, objects);
            cupcake.setId(id);
        }
    }
}
