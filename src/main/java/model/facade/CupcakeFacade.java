package model.facade;

import hyggedb.HyggeDb;
import model.entity.Bottom;
import model.entity.Cupcake;
import model.entity.Topping;
import model.repository.BottomRepository;
import model.repository.Repository;
import model.repository.ToppingRepository;

import java.util.Map;

/**
 * Created by adam on 05/03/2017.
 */
public class CupcakeFacade {

    private final HyggeDb db;

    public CupcakeFacade(HyggeDb db) {
        this.db = db;
    }

    public Cupcake mapCupcake(Map<String, Object> parameters, int userId) {
        Repository<Topping> toppingRepository = ToppingRepository.getInstance(db);
        Repository<Bottom> bottomRepository = BottomRepository.getInstance(db);
        Topping topping = toppingRepository.getById(((Integer) parameters.get("topping")));
        Bottom bottom = bottomRepository.getById(((Integer) parameters.get("bottom")));
        Cupcake cupcake = new Cupcake();
        cupcake.setStatus(1);
        cupcake.setTopping(topping);
        cupcake.setBottom(bottom);
        cupcake.setUserId(userId);
        cupcake.setAmount(((Integer) parameters.get("amount")));
        return cupcake;
    }
}
