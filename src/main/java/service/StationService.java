package service;

import model.entity.Route;
import model.entity.Station;

public interface StationService {
    Station findFromStation(Route route);
    Station findToStation(Route route);
}
