package service;

import model.entity.User;

public interface LoginService {
    User isPresentLogin(String login);
    User addUser(User user);

    boolean checkPassword(User user, String password);
}
