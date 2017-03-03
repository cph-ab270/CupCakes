package controller;

import hyggemvc.component.BootstrapAlerts;
import hyggemvc.controller.Controller;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by adam on 26/02/2017.
 */
public abstract class BaseController extends Controller {
    public static final int ADMIN_TYPE = 2;
    protected final String ROOT = "/";
    protected final String ASSETS = "/assets/";
    protected User user;

    public BaseController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        user = getUser();
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

    protected boolean checkSignIn() {
        if (!isLoggedIn()) {
            setAlert(BootstrapAlerts.Type.ERROR, "First sign in mate.");
            redirect(ROOT + "sign/in");
            return false;
        }
        return true;
    }

    protected boolean checkAdmin() {
        if (checkSignIn()) {
            if (user.getType() != ADMIN_TYPE) {
                setAlert(BootstrapAlerts.Type.ERROR, "You're not an admin mate.");
                redirect(ROOT);
                return false;
            }
            return true;
        }
        return false;
    }
}
