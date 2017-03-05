package model.facade;

import hyggedb.HyggeDb;
import model.entity.Cupcake;
import model.entity.Invoice;
import model.entity.User;
import model.exception.NotEnoughBalanceException;
import model.repository.InvoiceRepository;
import model.repository.Repository;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by adam on 05/03/2017.
 */
public class OrderFacade {
    private final HyggeDb db;

    public OrderFacade(HyggeDb db) {
        this.db = db;
    }

    public Invoice createOrder(List<Cupcake> cupcakes, User signedUser) throws NotEnoughBalanceException {
        int finalPrice = calculateFinalPrice(cupcakes);
        Invoice invoice = new Invoice();
        if (signedUser != null) {
            if (signedUser.getBalance() < finalPrice) {
                throw new NotEnoughBalanceException();
            }
            invoice.setUserId(signedUser.getId());
        } else {
            invoice.setUserId(0);
        }
        invoice.setCupcakes(cupcakes);
        invoice.setOrderedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        invoice.setPrice(finalPrice);
        Repository<Invoice> invoiceRepository = InvoiceRepository.getInstance(db);
        invoiceRepository.persistAndFlush(invoice);
        return invoice;
    }

    private int calculateFinalPrice(List<Cupcake> cupcakes) {
        int finalPrice = 0;
        for (Cupcake cupcake : cupcakes) {
            finalPrice += cupcake.getBottom().getPrice();
            finalPrice += cupcake.getTopping().getPrice();
            finalPrice *= cupcake.getAmount();
        }
        return finalPrice;
    }
}
