package dao.mysql;

import dao.UserDAO;
import dao.mysql.util.LogMessageDAOUtil;
import dao.mysql.util.QueryDAOUtil;
import model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
class MySQLUserDAO implements UserDAO{
    private static final Logger LOG = Logger.getLogger(MySQLUserDAO.class.getName());
    private static final String TABLE_NAME = "user";
    private static final String LABEL_ID = "id";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> result = entityManager
                .createNamedQuery("User.findAll", User.class)
                .getResultList();

        LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        User result = entityManager
                .createNamedQuery("User.findById", User.class)
                .setParameter("id", id)
                .getSingleResult();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        User result = entityManager
                .createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email)
                .getSingleResult();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public User create(User user) {
        entityManager.persist(user);
        LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, user.getId()));
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User update(User user) {
        entityManager.merge(user);
        LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, user.getId()));
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(User user) {
        entityManager.merge(user);
        entityManager.remove(user);
        LOG.info(LogMessageDAOUtil.createInfoDelete(TABLE_NAME, user.getId()));
    }
}
