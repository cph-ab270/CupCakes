package controller;

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
    private final OrderFacade orderFacade;
    public CartController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        orderFacade = new OrderFacade(getDatabase());
    }

    public void index(){
        HttpSession session = request.getSession();
        List<Cupcake> cupcakes = ((List<Cupcake>) session.getAttribute("cupcakes"));
        if (cupcakes != null) {
            int finalPrice = orderFacade.calculateFinalPrice(cupcakes);
            request.setAttribute("finalPrice",finalPrice);
        }
        renderTemplate("cart");
    }

    public void emptyCart(){
        if (request.getMethod().equals("POST")) {
            try {
                HttpSession session = request.getSession();
                List<Cupcake> cupcakes = ((List<Cupcake>) session.getAttribute("cupcakes"));
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
