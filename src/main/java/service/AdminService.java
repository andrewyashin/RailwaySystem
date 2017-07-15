package service;

import model.entity.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    List<User> getUsers();

    User updateUser(User user);
    void deleteUser(User user);
}
