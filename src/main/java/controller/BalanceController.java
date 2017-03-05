package controller;


import hyggedb.HyggeDb;
import hyggemvc.component.BootstrapAlerts;
import model.entity.User;
import model.repository.Repository;
import model.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lukas on 03/03/2017.
 */
public class BalanceController extends BaseController {
    public BalanceController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
    public void index() {
        redirectIfNotSignedIn();
        if (request.getMethod().equals("POST")) {
            int amount = Integer.parseInt(request.getParameter("amount"));
            user.setBalance(user.getBalance() + amount);
            HyggeDb db = getDatabase();
            Repository<User> userRepository = UserRepository.getInstance(db);
            userRepository.persistAndFlush(user);
            setAlert(BootstrapAlerts.Type.SUCCESS, "Successfully added.");
        }
        renderTemplate("balance/index");
    }
}
