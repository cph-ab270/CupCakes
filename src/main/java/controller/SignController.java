package controller;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.Hasher;
import model.entity.User;
import model.repository.Repository;
import model.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by adam on 26/02/2017.
 */
public class SignController extends BaseController {
    private HyggeDb db;
    private Repository<User> userRepository;

    public SignController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        db = new HyggeDb(new Connector());
        userRepository = UserRepository.getInstance(db);
    }

    public void in() {
        if (request.getMethod().equals("POST")) {
            String email = request.getParameter("email");
            Condition condition = new Condition("", "user", "email=?", email);
            List<User> users = userRepository.findBy(condition);
            if (!users.isEmpty()) {
                User user = users.get(0);
                Hasher hasher = new Hasher();
                String insertedPassword = hasher.hashPassword(request.getParameter("password"),user.getSalt());
                if (insertedPassword.equals(user.getPassword())) {
                    setLoginSession(user);
                    redirect(ROOT);
                } else {
                    setInvalidLoginAlert();
                }
            } else {
                setInvalidLoginAlert();
            }
        }
        setTemplateOutput("sign/in");
    }
    private void setInvalidLoginAlert() {
        setAlert("danger","This email and password pair doesn't exist.");
    }

    public void up() {
        if (request.getMethod().equals("POST")) {
            String email = request.getParameter("email");
            if (isEmailFree(email)) {
                User user = mapUser(email);
                userRepository.persistAndFlush(user);
                setAlert("success","Account was created");
                setLoginSession(user);
                redirect(ROOT);
            } else {
                setAlert("danger","E-mail is taken by somebody else.");
            }
        }
        setTemplateOutput("sign/up");
    }

    private boolean isEmailFree(String email) {
        Selection selection = new Selection("user");
        selection.where("email=?", email);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        try {
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User mapUser(String email) {
        Hasher hasher = new Hasher();
        String salt = hasher.generateSalt();
        String hashedPassword = hasher.hashPassword(request.getParameter("password"), salt);
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setEmail(email);
        user.setType(1);
        user.setStatus(1);
        user.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        return user;
    }

    private void setLoginSession(User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30*60);
        setAlert("success","Successfully logged in");
    }

    public void out() {
        HttpSession session = request.getSession();
        session.invalidate();
        redirect(ROOT + "sign/in");
    }
}
