package web.controller.admin;

import dto.Ticket;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.AdminService;
import service.RequestService;
import util.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static web.controller.admin.CommandAdminUtil.*;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RequestService requestService;

    @RequestMapping("/users")
    public String users(HttpServletRequest request){
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null || !userNow.getAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(USERS_ATTRIBUTE, adminService.getAllUsers());
        return Configuration.getInstance().getConfig(Configuration.ADMIN);
    }

    @RequestMapping("/tickets")
    public String tickets(HttpServletRequest request){
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null || !userNow.getAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, requestService.findAllTickets());
        return Configuration.getInstance().getConfig(Configuration.TICKETS_ADMIN);
    }

    @RequestMapping("/admin")
    public String adminUsers(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if (userNow == null || !userNow.getAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        List<User> users = adminService.getAllUsers();
        for (User user : users) {
            switch (request.getParameter(user.getId().toString())) {
                case DELETE:
                    adminService.deleteUser(user);
                    break;
                case ADMIN:
                    user.makeAdmin();
                    adminService.updateUser(user);
                    break;
                case USER:
                    user.makeUser();
                    adminService.updateUser(user);
                    break;
                default:
                    break;

            }
        }

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(USERS_ATTRIBUTE, adminService.getAllUsers());
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
        if(userNow == null || !userNow.getAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        List<Ticket> tickets = requestService.findAllTickets();
        for (Ticket ticket : tickets) {
            requestService.cancelRequest(ticket);
        }

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, requestService.findAllTickets());
        return Configuration.getInstance().getConfig(Configuration.TICKETS_ADMIN);
    }

    private String cancel(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if (userNow == null || !userNow.getAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        List<Ticket> tickets = requestService.findAllTickets();
        for (Ticket ticket : tickets) {
            if (request.getParameter(ticket.getRequestId().toString()) != null)
                requestService.cancelRequest(ticket);
        }

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, requestService.findAllTickets());
        return Configuration.getInstance().getConfig(Configuration.TICKETS_ADMIN);
    }
}
