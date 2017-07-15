package service;

import dto.TrainRoute;
import model.entity.Train;

import java.util.Date;
import java.util.List;

public interface TrainService {
    Train findTrainById(Long id);
    List<TrainRoute> findTrainsAndRoutes(Long fromId, Long toId, Date fromDate);

    Train reserveCompartmentPlace(Train train);
    Train reserveBerthPlace(Train train);
    Train reserveDeluxePlace(Train train);

    Train cancelBerthPlace(Train train);
    Train cancelCompartmentPlace(Train train);
    Train cancelDeluxePlace(Train train);

    String formatDate(String date);
}
