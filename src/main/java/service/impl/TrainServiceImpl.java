package service.impl;

import dao.DataBase;
import dao.StationDAO;
import dao.TrainDAO;
import dto.TrainRoute;
import model.entity.Route;
import model.entity.Station;
import model.entity.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RouteService;
import service.StationService;
import service.TrainService;

import java.util.logging.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
class TrainServiceImpl implements TrainService {
    private static final Logger LOG = Logger.getLogger(TrainServiceImpl.class.getName());

    @Autowired
    private TrainDAO trainDAO;

    @Autowired
    private StationDAO stationDAO;

    @Autowired
    private StationService stationService;

    @Autowired
    private RouteService routeService;


    public Train findTrainById(Long id) {
        return trainDAO.findById(id);
    }

    public List<TrainRoute> findTrainsAndRoutes(Long fromId, Long toId, Date fromDate) {
        Station from = stationDAO.findById(fromId);
        Station to = stationDAO.findById(toId);

        List<Route> routes = routeService.findRouteByStations(from, to);
        routes = routeService.findRoutesFromTime(routes, fromDate);
        List<Train> trains = findTrainsForRoutes(routes);

        List<TrainRoute> trainRoutes = new ArrayList<>();
        for (Train train : trains) {
            Route route = routeService.findRouteByTrain(train);

            TrainRoute trainRoute = new TrainRoute();
            trainRoute.setRouteId(route.getId());
            trainRoute.setTrainId(train.getId());

            trainRoute.setFromCity(stationService.findFromStation(route).getName());
            trainRoute.setToCity(stationService.findToStation(route).getName());

            trainRoute.setFromDate(formatDate(route.getFromTime()));
            trainRoute.setToDate(formatDate(route.getToTime()));

            trainRoute.setDistance(route.getDistance());

            trainRoute.setBerthFree(train.getBerthFree());
            trainRoute.setCompartmentFree(train.getCompartmentFree());
            trainRoute.setDeluxeFree(train.getDeluxeFree());

            trainRoute.setBerthPrice(routeService.findBerthPrice(route));
            trainRoute.setCompartmentPrice(routeService.findCompartmentPrice(route));
            trainRoute.setDeluxePrice(routeService.findDeluxePrice(route));

            if ((trainRoute.getBerthFree() + trainRoute.getCompartmentFree() + trainRoute.getDeluxeFree()) != 0) {
                trainRoutes.add(trainRoute);
            }
        }

        LOG.info(String.format("Find Trains FROM ID=%d -- TO ID=%d, FROM DATE=%s", fromId, toId, fromDate));
        return trainRoutes;
    }


    public Train reserveCompartmentPlace(Train train) {
        train.setCompartmentFree(train.getCompartmentFree() - 1);
        return trainDAO.update(train);
    }

    public Train reserveBerthPlace(Train train) {
        train.setBerthFree(train.getBerthFree() - 1);
        return trainDAO.update(train);
    }

    public Train reserveDeluxePlace(Train train) {
        train.setDeluxeFree(train.getDeluxeFree() - 1);
        return trainDAO.update(train);
    }


    public Train cancelBerthPlace(Train train) {
        train.setBerthFree(train.getBerthFree() + 1);
        return trainDAO.update(train);
    }


    public Train cancelCompartmentPlace(Train train) {
        train.setCompartmentFree(train.getCompartmentFree() + 1);
        return trainDAO.update(train);
    }

    public Train cancelDeluxePlace(Train train) {
        train.setDeluxeFree(train.getDeluxeFree() + 1);
        return trainDAO.update(train);
    }

    public String formatDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        Date result = null;
        try {
            result = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(result);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        String resultDate = String.format("%02d.%02d.%04d\n%02d:%02d",
                day, month, year, hour, minutes);

        LOG.info("Format date - " + resultDate);
        return resultDate;

    }

    private List<Train> findTrainsForRoutes(List<Route> routes) {
        List<Train> result = new ArrayList<>();

        for (Route route : routes) {
            result.addAll(trainDAO.findByRoute(route.getId()));
        }

        LOG.info("Find Trains by Routes");
        return result;
    }
}
