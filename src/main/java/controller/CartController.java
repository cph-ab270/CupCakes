package controller;

import hyggedb.HyggeDb;
import hyggemvc.component.BootstrapAlerts;
import model.entity.Cupcake;
import model.exception.NotEnoughBalanceException;
import model.facade.OrderFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

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
            HyggeDb db = getDatabase();
            OrderFacade orderFacade = new OrderFacade(db);
            List<Cupcake> cupcakes = ((List<Cupcake>) session.getAttribute("cupcakes"));
            try {
                orderFacade.createOrder(cupcakes,user);
                session.removeAttribute("cupcakes");
                setAlert(BootstrapAlerts.Type.SUCCESS, "Cupcakes were bought successfully.");
            } catch (NotEnoughBalanceException e) {
                setAlert(BootstrapAlerts.Type.ERROR,e.getMessage());
            }
        }
        redirect(ROOT + "cart");
    }
}
