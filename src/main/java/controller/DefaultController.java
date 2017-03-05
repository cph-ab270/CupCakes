package controller;

import hyggedb.HyggeDb;
import model.Connector;
import model.entity.Topping;
import model.repository.Repository;
import model.repository.ToppingRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by adam on 25/02/2017.
 */
public class DefaultController extends BaseController {
    public DefaultController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void index(){
        renderTemplate("home");
    }
    public void store() {

        HyggeDb db = new HyggeDb(new Connector());
        Repository<Topping> toppingRepository = ToppingRepository.getInstance(db);
        List<Topping> toppings = toppingRepository.findAll();
        request.setAttribute("toppings",toppings);
        renderTemplate("store" );
    }


}
