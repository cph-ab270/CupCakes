package controller;

import hyggedb.HyggeDb;
import model.Connector;
import model.entity.Bottom;
import model.entity.Topping;
import model.repository.BottomRepository;
import model.repository.Repository;
import model.repository.ToppingRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
