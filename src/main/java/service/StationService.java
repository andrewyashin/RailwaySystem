package service;

import dao.DataBase;
import model.entity.Route;
import model.entity.Station;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class StationService {
    private static final Logger LOG = Logger.getLogger(StationService.class.getName());
    private static final DataBase DB = DataBase.MYSQL;
    private static StationService INSTANCE;

    private DAOFactory factory;

    private StationService(){
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static StationService getInstance(){
        if(INSTANCE == null){
            synchronized (StationService.class){
                if (INSTANCE == null){
                    INSTANCE = new StationService();
                }
            }
        }

        return INSTANCE;
    }

    Station findFromStation(Route route){
        return factory.createStationDAO().findById(route.getFromId());
    }

    Station findToStation(Route route){
        return factory.createStationDAO().findById(route.getToId());
    }
}
