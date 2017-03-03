package controller;

import hyggedb.HyggeDb;
import model.Connector;
import model.entity.Topping;
import model.facade.ToppingFacade;
import model.repository.Repository;
import model.repository.ToppingRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 03/03/2017.
 */
public class ToppingController extends BaseController {
    public ToppingController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void add() {
        checkAdmin();
        if (request.getMethod().equals("POST")) {
            HyggeDb db = new HyggeDb(new Connector());
            Repository<Topping> toppingRepository = ToppingRepository.getInstance(db);
            ToppingFacade toppingFacade = new ToppingFacade();
            toppingFacade.addTopping(request,toppingRepository);
        }
        renderTemplate("topping/add");
    }


}
