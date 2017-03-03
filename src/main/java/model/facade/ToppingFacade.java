package model.facade;

import model.entity.Topping;
import model.repository.Repository;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by adam on 03/03/2017.
 */
public class ToppingFacade {
    public void addTopping(HttpServletRequest request, Repository<Topping> toppingRepository) {
        Topping topping = new Topping();
        topping.setName(((String) request.getParameter("name")));
        int price = Integer.parseInt(request.getParameter("price"));
        topping.setPrice(price);
        toppingRepository.persistAndFlush(topping);
    }
}
