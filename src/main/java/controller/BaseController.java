package controller;

import hyggemvc.component.BootstrapAlerts;
import hyggemvc.controller.Controller;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
        if (isLoggedIn()) {
            request.setAttribute("admin", isAdmin());
        } else {
            request.setAttribute("admin", false);
        }
        super.renderTemplate(template);
    }

    protected void redirectIfNotSignedIn() {
        if (!isLoggedIn()) {
            setAlert(BootstrapAlerts.Type.ERROR, "First sign in mate.");
            redirect(ROOT + "sign/in");
        }
    }

    protected void redirectIfNotAdmin() {
        redirectIfNotSignedIn();
        if (!isAdmin()) {
            setAlert(BootstrapAlerts.Type.ERROR, "You're not an admin mate.");
            redirect(ROOT);
        }
    }

    private boolean isAdmin() {
        return user.getType() == ADMIN_TYPE;
    }

    protected Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        Enumeration names = request.getParameterNames();
        String key;
        String value;
        while (names.hasMoreElements()) {
            key = ((String) names.nextElement());
            parameters.put(key, request.getParameter(key));
        }
        return parameters;
    }
}
