package controller;


import hyggemvc.component.BootstrapAlerts;
import hyggemvc.controller.Controller;
import model.entity.User;

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
        if (request.getMethod().equals("POST")) {
            int amount = Integer.parseInt(request.getParameter("amount"));
            user.setBalance(amount);
            setAlert(BootstrapAlerts.Type.SUCCESS, "Successfully added.");
        }
        renderTemplate("balance/index");
    }
}
