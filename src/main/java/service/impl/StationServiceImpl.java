package service.impl;

import dao.StationDAO;
import model.entity.Route;
import model.entity.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StationService;

import java.util.logging.Logger;

@Service
class StationServiceImpl implements StationService{
    private static final Logger LOG = Logger.getLogger(StationServiceImpl.class.getName());

    @Autowired
    private StationDAO stationDAO;

    public Station findFromStation(Route route){
        return stationDAO.findById(route.getFromStation().getId());
    }

    public Station findToStation(Route route){
        return stationDAO.findById(route.getToStation().getId());
    }
}
