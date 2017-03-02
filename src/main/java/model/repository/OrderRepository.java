package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.Bottom;
import model.entity.Order;
import model.entity.Topping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 02/03/2017.
 */
public class OrderRepository implements Repository<Order>{
    private final HyggeDb db;
    private Repository<Bottom> bottomRepository;
    private Repository<Topping> toppingRepository;
    private List<Order> persistedEntities = new ArrayList<>();
    private static OrderRepository instance;

    public static OrderRepository getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new OrderRepository(db);
        } else {
            return instance;
        }
    }
    private OrderRepository(HyggeDb db) {
        this.db = db;
        instance = this;
        bottomRepository = BottomRepository.getInstance(db);
        toppingRepository = ToppingRepository.getInstance(db);
    }

    @Override
    public Order getById(int id) {
        Selection selection = new Selection("order");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapOrder(rs);
    }
    private Order mapOrder(ResultSet rs) {
        Order user = null;
        try {
            if (rs.next()) {
                user = new Order();
                user.setId(rs.getInt("id"));
                user.setUserId(rs.getInt("user_id"));
                user.setStatus(rs.getInt("status"));
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
    public List<Order> findAll() {
        Selection selection = new Selection("order");
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapOrders(rs);
    }
    public List<Order> findByInvoiceId(int invoiceId) {
        Selection selection = new Selection("order","*");
        selection
                .join("invoice_order","id","order_id")
                .join("invoice")
                    .where("id=?",invoiceId);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapOrders(rs);
    }
    private List<Order> mapOrders(ResultSet rs) {
        List<Order> orders = new ArrayList<>();
        Order order = mapOrder(rs);
        while (order != null) {
            orders.add(order);
            order = mapOrder(rs);
        }
        return orders;
    }

    @Override
    public List<Order> findBy(Condition condition) {
        Selection selection = new Selection("order");
        selection.setWhere(condition);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapOrders(rs);
    }

    @Override
    public void persist(Order entity) {
        persistedEntities.add(entity);
    }

    @Override
    public void persistAndFlush(Order entity) {
        persist(entity);
        flush();
    }

    @Override
    public void flush() {
        Object[] objects;
        String sql;
        for (Order order : persistedEntities) {
            sql = "INSERT INTO `order`(`topping_id`, `botom_id`, `user_id`, `status`) VALUES (?,?,?,?)";
            objects = new Object[4];
            objects[0] = order.getTopping().getId();
            objects[1] = order.getBottom().getId();
            objects[2] = order.getUserId();
            objects[2] = order.getStatus();
            int id = db.getInsertionExecutor().insert(sql, objects);
            order.setId(id);
        }
    }
}
