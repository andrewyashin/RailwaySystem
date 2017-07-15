package service.impl;

import dao.UserDAO;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.AdminService;
import service.util.LogMessageServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
class AdminServiceImpl implements AdminService{
    private static final Logger LOG = Logger.getLogger(AdminServiceImpl.class.getName());

    private static final String USER_DAO = "UserDAO";

    private static final String GET_ALL_USERS = "getAllUsers()";
    private static final String GET_USERS = "getUsers()";
    private static final String UPDATE_USER = "updateUser()";
    private static final String DELETE_USER = "deleteUser()";

    @Autowired
    private UserDAO userDAO;

    public List<User> getAllUsers(){
        List<User> result = userDAO.findAll();
        if(result == null){
            LOG.severe(LogMessageServiceUtil.createMethodError(USER_DAO, GET_ALL_USERS));
        }

        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, GET_ALL_USERS));
        return result;
    }

    public List<User> getUsers(){
        List<User> result = userDAO.findAll();
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
        User updatedUser = userDAO.update(user);
        if (updatedUser == null){
            LOG.severe(LogMessageServiceUtil.createMethodError(USER_DAO, UPDATE_USER));
        }

        LOG.fine(LogMessageServiceUtil.createMethodInfo(USER_DAO, UPDATE_USER));
        return updatedUser;
    }
    
    public void deleteUser(User user){
        userDAO.delete(user);
        LOG.fine(LogMessageServiceUtil.createMethodInfo(USER_DAO, DELETE_USER));
    }

}
