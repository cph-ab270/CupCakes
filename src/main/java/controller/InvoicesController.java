package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ljurg on 3/5/17.
 */
public class InvoicesController extends BaseController {
    public InvoicesController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
    public void index(){ renderTemplate("invoices");}
}
