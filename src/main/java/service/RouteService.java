package service;

import model.entity.Route;
import model.entity.Station;
import model.entity.Train;

import java.util.Date;
import java.util.List;

public interface RouteService {
    Route findRouteById(Long id);
    Route findRouteByTrain(Train train);

    List<Station> findAvailableFromStations();
    List<Station> findAvailableToStations();

    List<Route> findRouteByStations(Station from, Station to);
    List<Route> findRoutesFromTime(List<Route> routes, Date date);

    Double findCompartmentPrice(Route route);
    Double findBerthPrice(Route route);
    Double findDeluxePrice(Route route);



}
