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
    }
    private User getUser() {
        HttpSession session = request.getSession();
        return ((User) session.getAttribute("user"));
    }

    protected boolean isLoggedIn() {
        HttpSession session = request.getSession();
        return session.getAttribute("user") != null;
    }

    @Override
    protected void renderTemplate(String template) {
        request.setAttribute("root", ROOT);
        request.setAttribute("assets", ASSETS);
        super.renderTemplate(template);
    }
}
