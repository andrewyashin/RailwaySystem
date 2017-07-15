package web.controller;

import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.AdminService;
import service.LoginService;
import service.RouteService;
import util.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

import static web.controller.CommandUtil.*;


@Controller
public class UtilController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RouteService routeService;

    @RequestMapping("/route")
    public String login(HttpServletRequest request) {
        String page = null;
        String email = request.getParameter(EMAIL).trim();
        String password = request.getParameter(PASSWORD).trim();

        User user = loginService.isPresentLogin(email);

        if(user == null){
            page = redirectToErrorPage(request);
        } else {
            page = checkIfCorrectPassword(user, request, password);
        }
        return page;
    }

    @RequestMapping("/login")
    public String register(HttpServletRequest request) {
        String page = null;
        String email = request.getParameter(EMAIL).trim();
        String password = request.getParameter(PASSWORD).trim();
        String name = request.getParameter(NAME).trim();
        String surname = request.getParameter(SURNAME).trim();
        String phone = request.getParameter(PHONE).trim();


        if(loginService.isPresentLogin(email) == null){
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            user.setPhone(phone);
            user.setSurname(surname);
            user.setAdmin(false);

            user = loginService.addUser(user);
            page = Configuration.getInstance().getConfig(Configuration.LOGIN);
        } else {
            request.setAttribute("errorMessage", true);
            page = Configuration.getInstance().getConfig(Configuration.REGISTER);
        }

        return page;
    }

    @RequestMapping("/")
    public String startPage() {
        return Configuration.getInstance().getConfig(Configuration.LOGIN);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        String page = Configuration.getInstance().getConfig(Configuration.LOGIN);
        HttpSession session = request.getSession(false);
        if(session.getAttribute("user") != null){
            session.setAttribute("user", null);
        }
        return page;
    }


    private String checkIfCorrectPassword(User user, HttpServletRequest request, String inputPassword){
        String page = null;
        if (loginService.checkPassword(user, inputPassword)) {
            page = checkIfAdmin(user, request);
        } else {
            page = redirectToErrorPage(request);
        }

        return page;
    }

    private String checkIfAdmin(User user, HttpServletRequest request){
        String page = null;
        if(user.getAdmin()){
            page = redirectToAdminPage(request, user);
        } else {
            page = redirectToUserPage(request, user);
        }
        return page;
    }

    private String redirectToAdminPage(HttpServletRequest request, User user){
        HttpSession session = request.getSession(false);
        session.setAttribute(USER_ATTRIBUTE, user);

        request.setAttribute(USERS_ATTRIBUTE, adminService.getAllUsers());
        request.setAttribute(USERNAME_ATTRIBUTE, user.getName());
        return Configuration.getInstance().getConfig(Configuration.ADMIN);
    }

    private String redirectToUserPage(HttpServletRequest request, User user){
        HttpSession session = request.getSession(false);
        session.setAttribute(USER_ATTRIBUTE, user);

        request.setAttribute(CITIES_FROM_ATTRIBUTE, routeService.findAvailableFromStations());
        request.setAttribute(CITIES_TO_ATTRIBUTE, routeService.findAvailableToStations());
        request.setAttribute(TRAINS_ATTRIBUTE, null);

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        request.setAttribute(DATE_NOW_ATTRIBUTE, format.format(new Date()));

        request.setAttribute(USERNAME_ATTRIBUTE, user.getName());
        return Configuration.getInstance().getConfig(Configuration.DATE);
    }

    private String redirectToErrorPage(HttpServletRequest request){
        request.setAttribute(ERROR_MESSAGE, true);
        return Configuration.getInstance().getConfig(Configuration.LOGIN);
    }
}
