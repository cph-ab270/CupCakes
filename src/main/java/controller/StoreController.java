package controller;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import model.Connector;
import model.entity.Bottom;
import model.entity.Cupcake;
import model.entity.Topping;
import model.facade.CupcakeFacade;
import model.repository.BottomRepository;
import model.repository.CupcakeRepository;
import model.repository.Repository;
import model.repository.ToppingRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 05/03/2017.
 */
public class StoreController extends BaseController {
    public StoreController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void index() {
        HyggeDb db = new HyggeDb(new Connector());
        Repository<Topping> toppingRepository = ToppingRepository.getInstance(db);
        request.setAttribute("toppings",toppingRepository.findAll());
        Repository<Bottom> bottomRepository = BottomRepository.getInstance(db);
        request.setAttribute("bottoms",bottomRepository.findAll());
        renderTemplate("store" );
    }

    public void add() {
        if (request.getMethod().equals("POST")) {
            HyggeDb db = new HyggeDb(new Connector());
            CupcakeFacade cupcakeFacade = new CupcakeFacade(db);
            if (isLoggedIn()) {
                Cupcake cupcake = cupcakeFacade.mapCupcake(getParameters(),user.getId());
                Repository<Cupcake> orderRepository = CupcakeRepository.getInstance(db);
                orderRepository.persistAndFlush(cupcake);
                updateLoggedUserOrdersInSession(orderRepository);
            } else {
                Cupcake cupcake = cupcakeFacade.mapCupcake(getParameters(),0);
                addCupcakeToSession(cupcake);
            }
        } else {
            redirect(ROOT + "store");
        }
    }

    private void updateLoggedUserOrdersInSession(Repository<Cupcake> orderRepository) {
        List<Cupcake> cupcakes = orderRepository.findBy(
                new Condition("","status=?",1)
                        .and("user_id=?",user.getId())
        );
        HttpSession session = request.getSession();
        session.setAttribute("cupcakes", cupcakes);
    }

    private void addCupcakeToSession(Cupcake cupcake) {
        HttpSession session = request.getSession();
        if (session.getAttribute("cupcakes") != null) {
            List<Cupcake> cupcakes = ((List<Cupcake>) session.getAttribute("cupcakes"));
            cupcakes.add(cupcake);
        } else {
            List<Cupcake> cupcakes = new ArrayList<>();
            cupcakes.add(cupcake);
            session.setAttribute("cupcakes", cupcakes);
        }
    }
}
