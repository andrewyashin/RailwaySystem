package service;

import dao.AbstractDAOFactory;
import dao.DAOFactory;
import dao.DataBase;
import dto.TrainRoute;
import model.entity.Route;
import model.entity.Station;
import model.entity.Train;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TrainService {
    private static final Logger LOG = Logger.getLogger(TrainService.class.getName());
    private static final DataBase DB = DataBase.MYSQL;
    private static TrainService INSTANCE;

    private DAOFactory factory;

    private TrainService(){
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static TrainService getInstance(){
        if(INSTANCE == null){
            synchronized (TrainService.class){
                if (INSTANCE == null){
                    INSTANCE = new TrainService();
                }
            }
        }

        return INSTANCE;
    }

    public Train findTrainById(Long id){
        return factory.createTrainDAO().findById(id);
    }

    public List<Train> findTrainsForRoutes(List<Route> routes){
        List<Train> result = new ArrayList<>();

        for (Route route: routes){
            result.addAll(factory.createTrainDAO().findByRoute(route.getId()));
        }

        LOG.info("Find Trains by Routes");
        return result;
    }


    public List<TrainRoute> findTrainsAndRoutes(Long fromId, Long toId, Date fromDate){
        Station from = factory.createStationDAO().findById(fromId);
        Station to = factory.createStationDAO().findById(toId);

        List<Route> routes = RouteService.getInstance().findRouteByStations(from, to);
        routes = RouteService.getInstance().findRoutesFromTime(routes, fromDate);
        List<Train> trains = TrainService.getInstance().findTrainsForRoutes(routes);

        List<TrainRoute> trainRoutes = new ArrayList<>();
        for(Train train: trains){
            Route route = RouteService.getInstance().findRouteByTrain(train);

            TrainRoute trainRoute = new TrainRoute();
            trainRoute.setRouteId(route.getId());
            trainRoute.setTrainId(train.getId());

            trainRoute.setFromCity(StationService.getInstance().findFromStation(route).getName());
            trainRoute.setToCity(StationService.getInstance().findToStation(route).getName());

            trainRoute.setFromDate(formatDate(route.getFromTime()));
            trainRoute.setToDate(formatDate(route.getToTime()));

            trainRoute.setDistance(route.getDistance());

            trainRoute.setBerthFree(train.getBerthFree());
            trainRoute.setCompartmentFree(train.getCompartmentFree());
            trainRoute.setDeluxeFree(train.getDeluxeFree());

            trainRoute.setBerthPrice(RouteService.getInstance().findBerthPrice(route));
            trainRoute.setCompartmentPrice(RouteService.getInstance().findCompartmentPrice(route));
            trainRoute.setDeluxePrice(RouteService.getInstance().findDeluxePrice(route));

            if((trainRoute.getBerthFree() + trainRoute.getCompartmentFree() + trainRoute.getDeluxeFree()) != 0) {
                trainRoutes.add(trainRoute);
            }
        }

        LOG.info(String.format("Find Trains FROM ID=%d -- TO ID=%d, FROM DATE=%s", fromId, toId, fromDate));
        return trainRoutes;
    }

    String formatDate(String date){
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

    Train reserveCompartmentPlace(Train train){
        train.setCompartmentFree(train.getCompartmentFree() - 1);
        return factory.createTrainDAO().update(train);
    }


    Train reserveBerthPlace(Train train){
        train.setBerthFree(train.getBerthFree() - 1);
        return factory.createTrainDAO().update(train);
    }


    Train reserveDeluxePlace(Train train){
        train.setDeluxeFree(train.getDeluxeFree() - 1);
        return factory.createTrainDAO().update(train);
    }

    Train cancelBerthPlace(Train train){
        train.setBerthFree(train.getBerthFree() + 1);
        return factory.createTrainDAO().update(train);
    }

    Train cancelCompartmentPlace(Train train){
        train.setCompartmentFree(train.getCompartmentFree() + 1);
        return factory.createTrainDAO().update(train);
    }

    Train cancelDeluxePlace(Train train){
        train.setDeluxeFree(train.getDeluxeFree() + 1);
        return factory.createTrainDAO().update(train);
    }

}
