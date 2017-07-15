package service.impl;

import dao.*;
import dao.mysql.TypePlace;
import dto.Ticket;
import dto.TrainRoute;
import exception.InvalidDataBaseOperation;
import model.entity.Request;
import model.entity.Route;
import model.entity.Train;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RequestService;
import service.RouteService;
import service.TrainService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Service
class RequestServiceImpl implements RequestService {
    private static final Logger LOG = Logger.getLogger(RequestDAO.class.getName());

    @Autowired
    private TrainDAO trainDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private RouteDAO routeDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private StationDAO stationDAO;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TrainService trainService;


    public void reserveTickets(final List<Ticket> tickets) throws InvalidDataBaseOperation {
        for (Ticket ticket : tickets) {
            Request request = new Request();
            request.setPrice(ticket.getPrice());
            request.setType(TypePlace.valueOf(ticket.getTypePlace()));
            request.setUser(userDAO.findById(ticket.getUserId()));
            request.setTrain(trainDAO.findById(ticket.getTrainId()));

            ticket.setRequestId(addRequest(request).getId());
        }
    }

    public Ticket makeTicket(String parameter, User user, TrainRoute trainRoute) {
        if (!parameter.equals("none")) {
            Ticket ticket = new Ticket();
            ticket.setTrainId(trainRoute.getTrainId());

            ticket.setFromCity(trainRoute.getFromCity());
            ticket.setToCity(trainRoute.getToCity());

            ticket.setFromDate(trainRoute.getFromDate());
            ticket.setToDate(trainRoute.getToDate());

            ticket.setName(user.getName());
            ticket.setSurname(user.getSurname());

            Double price;
            Long max;
            Route route = routeService.findRouteById(trainRoute.getRouteId());
            switch (parameter) {
                case "C": {
                    max = trainRoute.getCompartmentFree();
                    price = routeService.findCompartmentPrice(route);
                    break;
                }
                case "L": {
                    max = trainRoute.getDeluxeFree();
                    price = routeService.findDeluxePrice(route);
                    break;
                }
                default: {
                    max = trainRoute.getBerthFree();
                    price = routeService.findBerthPrice(route);
                    break;
                }
            }
            ticket.setMax(max);
            ticket.setTypePlace(parameter);
            ticket.setPrice(price);
            ticket.setUserId(user.getId());
            LOG.info("Add Ticket for USER ID = " + user.getId());
            return ticket;
        }
        return null;
    }

    public List<Ticket> findAllTickets() {
        List<Request> requests = requestDAO.findAll();
        List<Ticket> result = new ArrayList<>();
        for (Request request : requests) {
            Train train = trainDAO.findById(request.getTrain().getId());
            Route route = routeDAO.findById(train.getRoute().getId());
            User user = userDAO.findById(request.getUser().getId());

            Ticket ticket = new Ticket();
            ticket.setTrainId(train.getId());
            ticket.setRequestId(request.getId());
            ticket.setUserId(request.getUser().getId());

            ticket.setFromCity(stationDAO.findById(route.getFromStation().getId()).getName());
            ticket.setToCity(stationDAO.findById(route.getToStation().getId()).getName());

            ticket.setFromDate(trainService.formatDate(route.getFromTime()));
            ticket.setToDate(trainService.formatDate(route.getToTime()));

            ticket.setName(user.getName());
            ticket.setSurname(user.getSurname());

            ticket.setTypePlace(request.getType().toString());
            ticket.setPrice(request.getPrice());
            ticket.setUserId(user.getId());
            result.add(ticket);
        }

        result.sort((o1, o2) -> {
            if (o1.getRequestId() > o2.getRequestId()) return -1;
            else if (o1.getRequestId() < o2.getRequestId()) return 1;
            else return 0;
        });
        LOG.info("Find All Tickets");
        return result;
    }

    public List<Ticket> addTickets(Ticket ticket, Integer count) {
        List<Ticket> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Ticket ticket1 = new Ticket();
            ticket1.setRequestId(ticket.getRequestId());
            ticket1.setUserId(ticket.getUserId());
            ticket1.setPrice(ticket.getPrice());
            ticket1.setTypePlace(ticket.getTypePlace());
            ticket1.setSurname(ticket.getSurname());
            ticket1.setName(ticket.getName());
            ticket1.setFromDate(ticket.getFromDate());
            ticket1.setToDate(ticket.getToDate());
            ticket1.setFromCity(ticket.getFromCity());
            ticket1.setToCity(ticket.getToCity());
            ticket1.setMax(ticket.getMax());
            ticket1.setTrainId(ticket.getTrainId());
            result.add(ticket1);
        }

        LOG.info("Add Ticket to REQUEST");
        return result;
    }

    public void cancelRequest(Ticket ticket) {
        Train train = trainService.findTrainById(ticket.getTrainId());
        switch (ticket.getTypePlace()) {
            case "C":
                trainService.cancelCompartmentPlace(train);
                break;
            case "L":
                trainService.cancelDeluxePlace(train);
                break;
            default:
                trainService.cancelBerthPlace(train);
                break;
        }
        Request request = requestDAO.findById(ticket.getRequestId());
        requestDAO.delete(request);
        LOG.info("Cancel Ticket with (Request) ID = " + ticket.getRequestId());
    }

    public Request addRequest(Request request) throws InvalidDataBaseOperation {
        TypePlace place = request.getType();
        Train train = trainDAO.findById(request.getTrain().getId());

        switch (place) {
            case B: {
                if (train.getBerthFree() == 0) {
                    throw new InvalidDataBaseOperation("Someone booked all tickets to this train." +
                            " Please, go to the main page and select other train");
                }
                trainService.reserveBerthPlace(train);
                break;
            }
            case C: {
                if (train.getCompartmentFree() == 0) {
                    throw new InvalidDataBaseOperation("Someone booked all tickets to this train." +
                            " Please, go to the main page and select other train");
                }
                trainService.reserveCompartmentPlace(train);
                break;
            }
            case L: {
                if (train.getDeluxeFree() == 0) {
                    throw new InvalidDataBaseOperation("Someone booked all tickets to this train." +
                            " Please, go to the main page and select other train");
                }
                trainService.reserveDeluxePlace(train);
                break;
            }
        }

        return requestDAO.create(request);
    }
}
