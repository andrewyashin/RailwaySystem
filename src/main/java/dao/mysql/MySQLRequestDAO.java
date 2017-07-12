package dao.mysql;

import dao.RequestDAO;
import dao.mysql.util.LogMessageDAOUtil;
import model.entity.Request;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@Repository
class MySQLRequestDAO implements RequestDAO{
    private static final Logger LOG = Logger.getLogger(MySQLRequestDAO.class.getName());
    private static final String TABLE_NAME = "request";
    private static final String LABEL_ID = "id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Request> findAll() {
        List<Request> result = entityManager
                .createNamedQuery("Request.findAll", Request.class)
                .getResultList();

        LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Request findById(Long id) {
        Request result = entityManager
                .createNamedQuery("Request.findById", Request.class)
                .setParameter("id", id)
                .getSingleResult();

        LOG.info(LogMessageDAOUtil.createInfoFindByParameter(TABLE_NAME, LABEL_ID, id));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Request create(Request request) {
        entityManager.persist(request);
        LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, request.getId()));
        return request;
    }

    @Override
    @Transactional(readOnly = true)
    public Request update(Request request) {
        Request mergedRequest = entityManager.merge(request);
        LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, mergedRequest.getId()));
        return mergedRequest;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Request request) {
        Request mergedRequest = entityManager.merge(request);
        entityManager.remove(mergedRequest);
        LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, mergedRequest.getId()));
    }
}
