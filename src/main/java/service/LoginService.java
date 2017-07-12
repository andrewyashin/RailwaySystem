package service;

import dao.DataBase;
import model.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import service.util.LogMessageServiceUtil;
import java.util.logging.Logger;

@Service
public class LoginService {
    private static final Logger LOG = Logger.getLogger(LoginService.class.getName());
    private static final DataBase DB = DataBase.MYSQL;
    private static LoginService INSTANCE;

    private static final String USER_DAO = "UserDAO";

    private static final String ADD_USER = "addUser()";
    private static final String IS_PRESENT_LOGIN = "isPresentUser()";

    private DAOFactory factory;

    private LoginService(){
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static LoginService getInstance(){
        if(INSTANCE == null){
            synchronized (LoginService.class){
                if (INSTANCE == null){
                    INSTANCE = new LoginService();
                }
            }
        }

        return INSTANCE;
    }

    public User isPresentLogin(String login){
        User user = factory.createUserDAO().findByEmail(login);
        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, IS_PRESENT_LOGIN));
        return user;
    }

    public User addUser(User user){
        user = securePassword(user);
        User createdUser = factory.createUserDAO().create(user);
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

    User securePassword(final User user){
        String securePassword = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(securePassword);
        return user;
    }
}
