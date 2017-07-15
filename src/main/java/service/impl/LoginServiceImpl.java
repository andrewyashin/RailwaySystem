package service.impl;

import dao.UserDAO;
import model.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.LoginService;
import service.util.LogMessageServiceUtil;

import java.util.logging.Logger;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private static final Logger LOG = Logger.getLogger(LoginServiceImpl.class.getName());

    private static final String USER_DAO = "UserDAO";

    private static final String ADD_USER = "addUser()";
    private static final String IS_PRESENT_LOGIN = "isPresentUser()";


    @Autowired
    private UserDAO userDAO;


    public User isPresentLogin(String login){
        User user = userDAO.findByEmail(login);
        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, IS_PRESENT_LOGIN));
        return user;
    }

    public User addUser(User user){
        user = securePassword(user);
        User createdUser = userDAO.create(user);
        if (createdUser == null){
            LOG.severe(LogMessageServiceUtil.createMethodError(USER_DAO, ADD_USER));
        }

        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, ADD_USER));
        return createdUser;
    }

    public boolean checkPassword(User user, String password){
        String securePassword = DigestUtils.md5Hex(password);
        return securePassword.equals(user.getPassword());
    }

    private User securePassword(final User user){
        String securePassword = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(securePassword);
        return user;
    }
}
