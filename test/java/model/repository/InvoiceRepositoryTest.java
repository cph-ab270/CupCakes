package model.repository;

import hyggedb.HyggeDb;
import model.Connector;
import model.entity.Cupcake;
import model.entity.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 05/03/2017.
 */
class InvoiceRepositoryTest {
    private HyggeDb db;
    private Repository<Invoice> invoiceRepository;
    private Repository<Cupcake> cupcakeRepository;

    @BeforeEach
    void setUp() {
        db = new HyggeDb(new Connector("localhost","cba_cupcake_test","root","root"));
        invoiceRepository = InvoiceRepository.getInstance(db);
        cupcakeRepository = CupcakeRepository.getInstance(db);
    }

    @Test
    void testInsertingInvoice() {
        Cupcake cupcake = cupcakeRepository.getById(1);
        List<Cupcake> cupcakes = new ArrayList<>();
        cupcakes.add(cupcake);
    }
}