package controller;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggemvc.component.BootstrapAlerts;
import hyggemvc.controller.Controller;
import model.Connector;
import model.entity.Cupcake;
import model.entity.User;
import model.repository.CupcakeRepository;
import model.repository.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adam on 26/02/2017.
 */
public abstract class BaseController extends Controller {
    private HyggeDb db = null;
    public static final int ADMIN_TYPE = 2;
    protected final String ROOT = "/CupCakes_war/";
    protected final String ASSETS = ROOT+"assets/";
    protected User user = null;

    public BaseController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        user = getUser();
        if (isLoggedIn()) {
            Repository<Cupcake> cupcakeRepository = CupcakeRepository.getInstance(getDatabase());
            putLoggedUserCupcakesInSession(cupcakeRepository);
        }
    }

    private User getUser() {
        HttpSession session = request.getSession();
        return ((User) session.getAttribute("user"));
    }

    protected boolean isLoggedIn() {
        return user != null;
    }

    protected void putLoggedUserCupcakesInSession(Repository<Cupcake> orderRepository) {
        List<Cupcake> cupcakes = orderRepository.findBy(
                new Condition("", "status=?", 1)
                        .and("user_id=?", user.getId())
        );
        HttpSession session = request.getSession();
        session.setAttribute("cupcakes", cupcakes);
    }

    protected HyggeDb getDatabase() {
        if (db == null) {
            return db = new HyggeDb(new Connector());
        } else {
            return db;
        }
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
        Integer intValue;
        while (names.hasMoreElements()) {
            key = ((String) names.nextElement());
            value = request.getParameter(key);
            try {
                intValue = Integer.parseInt(value);
                parameters.put(key, intValue);
            } catch (NumberFormatException e) {
                parameters.put(key, value);
            }
        }
        return parameters;
    }
}
