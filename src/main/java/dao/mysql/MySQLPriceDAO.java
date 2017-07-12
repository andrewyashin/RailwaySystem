package dao.mysql;

import dao.PriceDAO;
import dao.mysql.util.LogMessageDAOUtil;
import model.entity.Price;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;


@Repository
class MySQLPriceDAO implements PriceDAO{
    private static final Logger LOG = Logger.getLogger(MySQLPriceDAO.class.getName());
    private static final String TABLE_NAME = "price";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Price> findAll() {
        List<Price> result = entityManager
                .createNamedQuery("Price.findAll", Price.class)
                .getResultList();
        LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Price findById(Long id) {
        Price result = entityManager
                .createNamedQuery("Price.findById", Price.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Price create(Price price) {
        entityManager.persist(price);
        LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, price.getId()));
        return price;
    }

    @Override
    @Transactional(readOnly = true)
    public Price update(Price price) {
        price = entityManager.merge(price);
        LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, price.getId()));
        return price;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Price price) {
        Price mergedPrice = entityManager.merge(price);
        entityManager.remove(mergedPrice);
        LOG.info(LogMessageDAOUtil.createInfoDelete(TABLE_NAME, price.getId()));
    }

}
