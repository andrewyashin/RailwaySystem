package dao.mysql;

import dao.StationDAO;
import dao.mysql.util.LogMessageDAOUtil;
import dao.mysql.util.QueryDAOUtil;
import model.entity.Station;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
class MySQLStationDAO implements StationDAO{
    private static final Logger LOG = Logger.getLogger(MySQLStationDAO.class.getName());

    private static final String TABLE_NAME = "station";
    private static final String LABEL_ID = "id";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    public List<Station> findAll() {
        List<Station> result = entityManager
                .createNamedQuery("Station.findAll", Station.class)
                .getResultList();
        LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Station findById(Long id) {
        Station result = entityManager
                .createNamedQuery("Station.findById", Station.class)
                .setParameter("id", id)
                .getSingleResult();
        LOG.info(LogMessageDAOUtil.createInfoFindByParameter(TABLE_NAME, LABEL_ID, id));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Station create(Station station) {
        entityManager.persist(station);
        LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, station.getId()));
        return station;
    }

    @Override
    @Transactional(readOnly = true)
    public Station update(Station station) {
        entityManager.merge(station);
        LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, station.getId()));
        return station;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Station station) {
        entityManager.merge(station);
        entityManager.remove(station);
        LOG.info(LogMessageDAOUtil.createInfoDelete(TABLE_NAME, station.getId()));

    }

}
