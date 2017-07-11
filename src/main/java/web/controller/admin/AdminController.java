package web.controller.admin;

import dto.Ticket;
import model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.AdminService;
import service.RequestService;
import util.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static web.controller.admin.CommandAdminUtil.*;

@Controller
public class AdminController {

    @RequestMapping("/users")
    public String users(HttpServletRequest request){
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null || !userNow.isAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(USERS_ATTRIBUTE, AdminService.getInstance().getAllUsers());
        return Configuration.getInstance().getConfig(Configuration.ADMIN);
    }

    @RequestMapping("/tickets")
    public String tickets(HttpServletRequest request){
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null || !userNow.isAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, RequestService.getInstance().findAllTickets());
        return Configuration.getInstance().getConfig(Configuration.TICKETS_ADMIN);
    }

    @RequestMapping("/admin")
    public String adminUsers(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if (userNow == null || !userNow.isAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        List<User> users = AdminService.getInstance().getAllUsers();
        for (User user : users) {
            switch (request.getParameter(user.getId().toString())) {
                case DELETE:
                    AdminService.getInstance().deleteUser(user);
                    break;
                case ADMIN:
                    user.makeAdmin();
                    AdminService.getInstance().updateUser(user);
                    break;
                case USER:
                    user.makeUser();
                    AdminService.getInstance().updateUser(user);
                    break;
                default:
                    break;

            }
        }

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(USERS_ATTRIBUTE, AdminService.getInstance().getAllUsers());
        return Configuration.getInstance().getConfig(Configuration.ADMIN);
    }

    @RequestMapping("/cancel")
    public String adminTickets(@RequestParam(name = "command") String param, HttpServletRequest request){
        String result = null;
        if (param.equals("cancel")){
            result = cancel(request);
        } else {
            result = cancelAll(request);
        }

        return result;
    }

    private String cancelAll(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null || !userNow.isAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        List<Ticket> tickets = RequestService.getInstance().findAllTickets();
        for (Ticket ticket : tickets) {
            RequestService.getInstance().cancelRequest(ticket);
        }

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, RequestService.getInstance().findAllTickets());
        return Configuration.getInstance().getConfig(Configuration.TICKETS_ADMIN);
    }

    private String cancel(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if (userNow == null || !userNow.isAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        List<Ticket> tickets = RequestService.getInstance().findAllTickets();
        for (Ticket ticket : tickets) {
            if (request.getParameter(ticket.getRequestId().toString()) != null)
                RequestService.getInstance().cancelRequest(ticket);
        }

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, RequestService.getInstance().findAllTickets());
        return Configuration.getInstance().getConfig(Configuration.TICKETS_ADMIN);
    }
}
