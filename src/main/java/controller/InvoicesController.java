package controller;

import hyggedb.select.Condition;
import model.entity.Invoice;
import model.repository.InvoiceRepository;
import model.repository.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ljurg on 3/5/17.
 */
public class InvoicesController extends BaseController {
    public InvoicesController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
    public void index(){
        redirectIfNotSignedIn();
        Repository<Invoice> invoiceRepository = InvoiceRepository.getInstance(getDatabase());
        List<Invoice> invoices = invoiceRepository.findBy(
                new Condition("invoice","user_id=?",user.getId())
        );
        request.setAttribute("invoices",invoices);
        renderTemplate("invoices/index");
    }
}
