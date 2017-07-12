package service;

import dao.DataBase;
import model.entity.User;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import service.util.LogMessageServiceUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private static final Logger LOG = Logger.getLogger(AdminService.class.getName());
    private static final DataBase DB = DataBase.MYSQL;
    private static AdminService INSTANCE;

    private static final String USER_DAO = "UserDAO";

    private static final String GET_ALL_USERS = "getAllUsers()";
    private static final String GET_USERS = "getUsers()";
    private static final String UPDATE_USER = "updateUser()";
    private static final String DELETE_USER = "deleteUser()";

    private DAOFactory factory;

    private AdminService(){
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static AdminService getInstance(){
        if(INSTANCE == null){
            synchronized (AdminService.class){
                if (INSTANCE == null){
                    INSTANCE = new AdminService();
                }
            }
        }

        return INSTANCE;
    }

    public List<User> getAllUsers(){
        List<User> result = factory.createUserDAO().findAll();
        if(result == null){
            LOG.severe(LogMessageServiceUtil.createMethodError(USER_DAO, GET_ALL_USERS));
        }

        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, GET_ALL_USERS));
        return result;
    }

    public List<User> getUsers(){
        List<User> result = factory.createUserDAO().findAll();
        if(result == null){
            LOG.severe(LogMessageServiceUtil.createMethodError(USER_DAO, GET_USERS));
        }
        
        List<User> users = new ArrayList<>();
        for(User user: result){
            if(!user.getAdmin())
                users.add(user);
        }

        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, GET_USERS));
        return users;
    }
    
    public User updateUser(User user){
        User updatedUser = factory.createUserDAO().update(user);
        if (updatedUser == null){
            LOG.severe(LogMessageServiceUtil.createMethodError(USER_DAO, UPDATE_USER));
        }

        LOG.fine(LogMessageServiceUtil.createMethodInfo(USER_DAO, UPDATE_USER));
        return updatedUser;
    }
    
    public void deleteUser(User user){
        factory.createUserDAO().delete(user);
        LOG.fine(LogMessageServiceUtil.createMethodInfo(USER_DAO, DELETE_USER));
    }

}
