package dao.mysql;

import dao.RouteDAO;
import dao.mysql.util.LogMessageDAOUtil;
import model.entity.Route;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@Repository
class MySQLRouteDAO implements RouteDAO{
    private static final Logger LOG = Logger.getLogger(MySQLRouteDAO.class.getName());

    private static final String TABLE_NAME = "route";
    private static final String LABEL_ID = "id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Route> findAll() {
        List<Route> result = entityManager
                .createNamedQuery("Route.findAll", Route.class)
                .getResultList();

        LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Route findById(Long id) {
        Route result = entityManager
                .createNamedQuery("Route.findById", Route.class)
                .setParameter("id", id)
                .getSingleResult();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> findByFromId(Long id) {
        List<Route> result = entityManager
                .createNamedQuery("Route.findByFromId", Route.class)
                .setParameter("fromId", id)
                .getResultList();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Route create(Route route) {
        entityManager.persist(route);
        LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, route.getId()));
        return route;
    }

    @Override
    @Transactional(readOnly = true)
    public Route update(Route route) {
        entityManager.merge(route);
        LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, route.getId()));
        return route;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Route route) {
        Route mergedRoute = entityManager.merge(route);
        entityManager.remove(mergedRoute);
        LOG.info(LogMessageDAOUtil.createInfoDelete(TABLE_NAME, route.getId()));
    }
}
