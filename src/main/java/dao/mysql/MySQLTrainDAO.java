package dao.mysql;

import dao.TrainDAO;
import dao.mysql.util.LogMessageDAOUtil;
import dao.mysql.util.QueryDAOUtil;
import model.entity.Train;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
class MySQLTrainDAO implements TrainDAO{
    private static final Logger LOG = Logger.getLogger(MySQLTrainDAO.class.getName());

    private static final String TABLE_NAME = "train";
    private static final String LABEL_ID = "id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Train> findAll() {
        List<Train> result = entityManager
                .createNamedQuery("Train.findAll", Train.class)
                .getResultList();

        LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Train> findByRoute(Long route_id) {
        List<Train> result = entityManager
                .createNamedQuery("Train.findByRouteId", Train.class)
                .getResultList();

       // LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Train findById(Long id) {
        Train result = entityManager
                .createNamedQuery("Train.findById", Train.class)
                .setParameter("id", id)
                .getSingleResult();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Train create(final Train train) {
        entityManager.persist(train);
        LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, train.getId()));
        return train;
    }

    @Override
    @Transactional(readOnly = true)
    public Train update(final Train train) {
        entityManager.merge(train);
        LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, train.getId()));
        return train;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(final Train train) {
        entityManager.merge(train);
        entityManager.remove(train);
    }

}
