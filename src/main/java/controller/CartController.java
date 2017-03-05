package controller;

import hyggemvc.component.BootstrapAlerts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by ljurg on 3/5/17.
 */
public class CartController extends BaseController {
    public CartController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void index(){
        renderTemplate("cart");
    }

    public void emptyCart(){
        if (request.getMethod().equals("POST")) {
            HttpSession session = request.getSession();
            session.removeAttribute("cupcakes");
            setAlert(BootstrapAlerts.Type.SUCCESS, "Cupcakes were bought successfully.");
        }
        redirect("cart");
    }
}
