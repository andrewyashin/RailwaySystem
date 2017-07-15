package service.impl;

import dao.PriceDAO;
import dao.RouteDAO;
import dao.StationDAO;
import model.entity.Price;
import model.entity.Route;
import model.entity.Station;
import model.entity.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RouteService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
class RouteServiceImpl implements RouteService {
    private static final Logger LOG = Logger.getLogger(RouteServiceImpl.class.getName());

    @Autowired
    private RouteDAO routeDAO;

    @Autowired
    private StationDAO stationDAO;

    @Autowired
    private PriceDAO priceDAO;


    public Route findRouteById(Long id) {
        return routeDAO.findById(id);
    }

    public Route findRouteByTrain(Train train) {
        return routeDAO.findById(train.getRoute().getId());
    }

    public List<Station> findAvailableFromStations() {
        List<Route> routes = routeDAO.findAll();
        Set<Station> stations = new HashSet<>();

        for (Route route : routes) {
            Station station = stationDAO.findById(route.getFromStation().getId());
            stations.add(station);
        }

        List<Station> result = new ArrayList<>(stations);
        result.sort(Comparator.comparing(Station::getName));

        LOG.info("Find All Available FROM Stations");
        return result;
    }

    public List<Station> findAvailableToStations() {
        List<Route> routes = routeDAO.findAll();
        Set<Station> stations = new HashSet<>();

        for (Route route : routes) {
            Station station = stationDAO.findById(route.getToStation().getId());
            stations.add(station);
        }

        List<Station> result = new ArrayList<>(stations);
        result.sort(Comparator.comparing(Station::getName));

        LOG.info("Find All Available TO Stations");
        return result;
    }

    public List<Route> findRouteByStations(Station from, Station to) {
        List<Route> routes = routeDAO.findByFromId(from.getId());
        List<Route> result = new ArrayList<>();

        for (Route route : routes) {
            if (route.getToStation().getId().equals(to.getId())) {
                result.add(route);
            }
        }

        LOG.info(String.format("Find Route by Stations: FROM %s --- TO %s", from.getName(), to.getName()));
        return result;
    }

    public List<Route> findRoutesFromTime(List<Route> routes, Date date) {
        long time = date.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");

        List<Route> result = new ArrayList<>();
        try {
            for (Route route : routes) {
                if (format.parse(route.getFromTime()).getTime() > time) {
                    result.add(route);
                }
            }
        } catch (ParseException e) {
            LOG.severe(e.getMessage());
            e.printStackTrace();
        }

        LOG.info("Find Routes from TIME - " + date);
        return result;
    }

    public Double findCompartmentPrice(Route route) {
        Price compartment = priceDAO.findById(route.getPrice().getId());
        return compartment.getCompartmentFactor() * route.getDistance();
    }


    public Double findBerthPrice(Route route) {
        Price compartment = priceDAO.findById(route.getPrice().getId());
        return compartment.getBerthFactor() * route.getDistance();
    }


    public Double findDeluxePrice(Route route) {
        Price compartment = priceDAO.findById(route.getPrice().getId());
        return compartment.getDeluxeFactor() * route.getDistance();
    }
}
