package controller;

import hyggedb.HyggeDb;
import hyggemvc.component.BootstrapAlerts;
import model.Connector;
import model.entity.User;
import model.exception.EmailTakenException;
import model.exception.NonExistentEmailException;
import model.exception.WrongPasswordException;
import model.facade.LoginFacade;
import model.facade.RegisterFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by adam on 26/02/2017.
 */
public class SignController extends BaseController {
    private HyggeDb db;

    public SignController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        db = new HyggeDb(new Connector());
    }

    public void in() {
        if (request.getMethod().equals("POST")) {
            LoginFacade loginFacade = new LoginFacade(db);
            try {
                User user = loginFacade.loginUser(
                        request.getParameter("email"),
                        request.getParameter("password")
                );
                setLoginSession(user);
                redirect(ROOT);
                return;
            } catch (WrongPasswordException | NonExistentEmailException e) {
                setAlert(BootstrapAlerts.Type.ERROR,"This email and password pair doesn't exist.");
            }
        }
        renderTemplate("sign/in");
    }
    private void setLoginSession(User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
//        session.setMaxInactiveInterval(30*60);
        setAlert(BootstrapAlerts.Type.SUCCESS,"Successfully logged in");
    }

    public void up() {
        if (request.getMethod().equals("POST")) {
            RegisterFacade registerFacade = new RegisterFacade(db,request);
            try {
                User user = registerFacade.registerUser();
                setAlert(BootstrapAlerts.Type.SUCCESS,"Account was created");
                setLoginSession(user);
                redirect(ROOT);
                return;
            } catch (EmailTakenException e) {
                setAlert(BootstrapAlerts.Type.ERROR,e.getMessage());
            }
        }
        renderTemplate("sign/up");
    }

    public void out() {
        if (isLoggedIn()) {
            HttpSession session = request.getSession();
            session.setAttribute("user",null);
            setAlert(BootstrapAlerts.Type.SUCCESS, "Successfully logged out.");
        } else {
            setAlert(BootstrapAlerts.Type.WARNING, "Don't have to log out when you're not logged in.");
        }
        redirect(ROOT + "sign/in");
    }
}
