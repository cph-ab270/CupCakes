package model.facade;

import model.entity.Topping;
import model.repository.Repository;

import java.util.Map;

/**
 * Created by adam on 03/03/2017.
 */
public class ToppingFacade {
    public void addTopping(Map<String,Object> parameters, Repository<Topping> toppingRepository) {
        Topping topping = new Topping();
        topping.setName(((String) parameters.get("name")));
        int price = ((Integer) parameters.get("price"));
        topping.setPrice(price);
        toppingRepository.persistAndFlush(topping);
    }
}
