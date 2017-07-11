package web.controller.user;

import dto.Ticket;
import dto.TrainRoute;
import exception.InvalidDataBaseOperation;
import model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.RequestService;
import service.RouteService;
import service.TrainService;
import util.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static web.controller.user.CommandUserUtil.*;
import static web.controller.user.CommandUserUtil.TIME_PARAMETER;
import static web.controller.user.CommandUserUtil.USERNAME_ATTRIBUTE;

@Controller
public class UserController {

    @RequestMapping("/date")
    public String date(@RequestParam(name = "command") String param,  HttpServletRequest request){
        String result = null;
        if(param.equals("make")){
            result = makeTickets(request);
        } else {
            result = selectCityDate(request);
        }

        return result;
    }

    @RequestMapping("/main")
    public String main(HttpServletRequest request ) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null)
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        request.setAttribute(CITIES_FROM_ATTRIBUTE, RouteService.getInstance().findAvailableFromStations());
        request.setAttribute(CITIES_TO_ATTRIBUTE, RouteService.getInstance().findAvailableToStations());
        request.setAttribute(TRAINS_ATTRIBUTE, null);
        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(DATE_NOW_ATTRIBUTE, format.format(new Date()));
        return Configuration.getInstance().getConfig(Configuration.DATE);
    }

    @RequestMapping("/book")
    public String book(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null)
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        String page = Configuration.getInstance().getConfig(Configuration.TICKET);

        List<Ticket> tickets = (List<Ticket>) request.getSession(false).getAttribute(TICKETS_ATTRIBUTE);
        if(tickets == null){
            request.setAttribute(CITIES_FROM_ATTRIBUTE, RouteService.getInstance().findAvailableFromStations());
            request.setAttribute(CITIES_TO_ATTRIBUTE, RouteService.getInstance().findAvailableToStations());
            request.setAttribute(TRAINS_ATTRIBUTE, null);

            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            request.setAttribute(DATE_NOW_ATTRIBUTE, format.format(new Date()));
            page = Configuration.getInstance().getConfig(Configuration.DATE);
        } else {
            List<Ticket> resultTickets = new ArrayList<>();

            for (Ticket ticket : tickets) {
                Integer count = Integer.parseInt(request.getParameter(ticket.getTrainId().toString()));
                resultTickets.addAll(RequestService.getInstance().addTickets(ticket, count));
            }

            try {
                RequestService.getInstance().reserveTickets(resultTickets);
                request.setAttribute(TICKETS_ATTRIBUTE, resultTickets);
            } catch (InvalidDataBaseOperation e){
                request.setAttribute(MESSAGE_ERROR_ATTRIBUTE, e.getMessage());
                page = Configuration.getInstance().getConfig(Configuration.ERROR);
            }

        }
        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        return page;
    }

    private String selectCityDate(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null)
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        String page = Configuration.getInstance().getConfig(Configuration.DATE);
        Long from_id = Long.parseLong(request.getParameter(FROM_PARAMETER));
        Long to_id = Long.parseLong(request.getParameter(TO_PARAMETER));
        Integer time = Integer.parseInt(request.getParameter(TIME_PARAMETER));
        String dateString = request.getParameter(DATE_PARAMETER);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        Date date = null;
        try {
            date = format.parse(dateString);
            date.setHours(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<TrainRoute> trains = TrainService.getInstance().findTrainsAndRoutes(from_id, to_id, date);

        request.setAttribute(CITIES_FROM_ATTRIBUTE, RouteService.getInstance().findAvailableFromStations());
        request.setAttribute(CITIES_TO_ATTRIBUTE, RouteService.getInstance().findAvailableToStations());

        request.setAttribute(FROM_PARAMETER, from_id);
        request.setAttribute(TO_PARAMETER, to_id);
        request.setAttribute(TRAINS_ATTRIBUTE, trains);
        if(trains.isEmpty()){
            request.setAttribute(NO_TRAINS_ATTRIBUTE, true);
        }
        request.setAttribute(DATE_NOW_ATTRIBUTE, format.format(date));
        request.setAttribute(TIME_PARAMETER, time);

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        return page;
    }

    private String makeTickets(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null)
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        String page = Configuration.getInstance().getConfig(Configuration.ORDER);
        Long from_id = Long.parseLong(request.getParameter(FROM_PARAMETER));
        Long to_id = Long.parseLong(request.getParameter(TO_PARAMETER));
        Integer time = Integer.parseInt(request.getParameter(TIME_PARAMETER));
        String dateString = request.getParameter(DATE_ATTRIBUTE);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        Date date = null;
        try {
            date = format.parse(dateString);
            date.setHours(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = (User) request.getSession().getAttribute(USER_ATTRIBUTE);
        List<TrainRoute> trains = TrainService.getInstance().findTrainsAndRoutes(from_id, to_id, date);
        List<Ticket> tickets = new ArrayList<>();
        for (TrainRoute trainRoute : trains) {
            String parameter = request.getParameter(TRAIN_PARAMETER + trainRoute.getTrainId());

            Ticket ticket = RequestService.getInstance().makeTicket(parameter, user, trainRoute);
            if (ticket != null){
                tickets.add(ticket);
            }
        }

        if (tickets.isEmpty()){
            request.setAttribute(NO_TICKETS_ATTRIBUTE, true);
        } else{
            request.setAttribute(TICKETS_ATTRIBUTE, tickets);
            request.getSession(false).setAttribute(TICKETS_ATTRIBUTE, tickets);
        }
        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        return page;
    }
}
