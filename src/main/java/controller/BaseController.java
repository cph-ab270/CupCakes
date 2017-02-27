package controller;

import hyggemvc.controller.Controller;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by adam on 26/02/2017.
 */
public abstract class BaseController extends Controller {
    protected final String ROOT = "/";
    protected final String ASSETS = "/assets/";
    protected User user;

    public BaseController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        if (isLoggedIn()) {
            user = getUser();
            setAlert("info","You're logged in as "+user.getName()+" "+user.getSurname());
        }
    }
    private User getUser() {
        HttpSession session = request.getSession();
        return ((User) session.getAttribute("user"));
    }

    protected boolean isLoggedIn() {
        HttpSession session = request.getSession();
        return session.getAttribute("user") != null;
    }

    protected void setAlert(String type, String message) {
        String alert = "";
        if (request.getAttribute("alert") != null) {
            alert = ((String) request.getAttribute("alert"));
        }
        alert += "<div class='alert alert-"+type+"' role='alert'>"+message+"</div>";
        request.setAttribute("alert", alert);
    }

    @Override
    protected void setTemplateOutput(String template) {
        super.setTemplateOutput(template);
        request.setAttribute("root", ROOT);
        request.setAttribute("assets", ASSETS);
    }
}
