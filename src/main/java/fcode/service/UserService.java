package fcode.service;

import fcode.model.User;

import java.util.List;

public interface UserService {
    List<User> getListUser();

    User getUserById(String id);

    User createUser(User user);

    User editUser(User user);

    void deleteUser(String id);

    User login(String displayNameOrEmailAddress, String password);

    User findByDisplayName(String displayName);

    User findByEmailAddress(String emailAddress);

}
