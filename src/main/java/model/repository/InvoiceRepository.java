package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.Invoice;
import model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 02/03/2017.
 */
public class InvoiceRepository implements Repository<Invoice>{
    private final HyggeDb db;
    private OrderRepository orderRepository;
    private List<Invoice> persistedEntities = new ArrayList<>();
    private static Repository<Invoice> instance;

    public static Repository<Invoice> getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new InvoiceRepository(db);
        } else {
            return instance;
        }
    }
    private InvoiceRepository(HyggeDb db) {
        this.db = db;
        instance = this;
        orderRepository = OrderRepository.getInstance(db);
    }

    @Override
    public Invoice getById(int id) {
        Selection selection = new Selection("invoice");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapInvoice(rs);
    }
    private Invoice mapInvoice(ResultSet rs) {
        Invoice invoice = null;
        try {
            if (rs.next()) {
                invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setUserId(rs.getInt("user_id"));
                List<Order> orders = orderRepository.findByInvoiceId(rs.getInt("invoice_id"));
                invoice.setOrders(orders);
                invoice.setOrderedAt(rs.getDate("ordered_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    }

    @Override
    public List<Invoice> findAll() {
        Selection selection = new Selection("order");
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapInvoices(rs);
    }
    private List<Invoice> mapInvoices(ResultSet rs) {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice = mapInvoice(rs);
        while (invoice != null) {
            invoices.add(invoice);
            invoice = mapInvoice(rs);
        }
        return invoices;
    }

    @Override
    public List<Invoice> findBy(Condition condition) {
        Selection selection = new Selection("order");
        selection.setWhere(condition);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapInvoices(rs);
    }

    @Override
    public void persist(Invoice entity) {
        persistedEntities.add(entity);
    }

    @Override
    public void persistAndFlush(Invoice entity) {
        persist(entity);
        flush();
    }

    @Override
    public void flush() {
        Object[] objects;
        String sql;
        for (Invoice invoice : persistedEntities) {
            sql = "INSERT INTO `invoice`(`ordered_at`, `user_id`, `price`) VALUES (?,?,?)";
            objects = new Object[3];
            objects[0] = invoice.getOrderedAt();
            objects[1] = invoice.getUserId();
            calculateFinalPrice(invoice);
            objects[2] = invoice.getPrice();
            int id = db.getInsertionExecutor().insert(sql, objects);
            invoice.setId(id);

            sql = "INSERT INTO `invoice_order`(`invoice_id`, `order_id`) VALUES (?,?)";
            for (Order order : invoice.getOrders()) {
                objects = new Object[2];
                objects[0] = invoice.getId();
                objects[1] = order.getId();
                db.getInsertionExecutor().insert(sql, objects);
            }
        }
    }

    private void calculateFinalPrice(Invoice invoice) {
        int finalPrice = 0;
        for (Order order : invoice.getOrders()) {
            finalPrice += order.getBottom().getPrice();
            finalPrice += order.getTopping().getPrice();
            finalPrice *= order.getAmount();
        }
        invoice.setPrice(finalPrice);
    }
}
