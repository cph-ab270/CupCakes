package controller;

import hyggedb.HyggeDb;
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
        redirectIfNotAdmin();
        if (request.getMethod().equals("POST")) {
            HyggeDb db = getDatabase();
            Repository<Topping> toppingRepository = ToppingRepository.getInstance(db);
            ToppingFacade toppingFacade = new ToppingFacade();
            toppingFacade.addTopping(request,toppingRepository);
        }
        renderTemplate("topping/add");
    }


}
