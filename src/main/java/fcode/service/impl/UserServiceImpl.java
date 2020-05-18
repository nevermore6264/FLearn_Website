package fcode.service.impl;

import fcode.model.User;
import fcode.repository.UserRepository;
import fcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<User> getListUser() {
        LOGGER.info("getListUser");
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        LOGGER.info("getUserById");
        return this.userRepository.findOne(id);
    }

    @Override
    public User createUser(User user) {
        LOGGER.info("BCryptPasswordEncoder Password");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        LOGGER.info("createUser");
        return this.userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        LOGGER.info("editUser");
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        this.userRepository.delete(id);
    }

    @Override
    public User login(String displayNameOrEmailAddress, String password) {
        User displayNameExists = userRepository.findByDisplayName(displayNameOrEmailAddress);
        User emailAddressExists = userRepository.findByEmailAddress(password);

        if (displayNameExists == null && emailAddressExists == null) {
            LOGGER.info("Not found User");
            return null;
        }

        if (!passwordEncoder.matches(password, displayNameExists.getPassword())) {
            LOGGER.info("Incorrect password");
            return null;
        }
        return displayNameExists;
    }

}
