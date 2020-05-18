package fcode.controller;

import fcode.model.User;
import fcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getListUser() {
        List<User> users = this.userService.getListUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = this.userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User byDisplayName = this.userService.findByDisplayName(user.getDisplayName());
        User byEmailAddress = this.userService.findByEmailAddress(user.getDisplayName());

        if (byDisplayName != null ) {
            return new ResponseEntity<>(byDisplayName, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(byEmailAddress != null){
            return new ResponseEntity<>(byEmailAddress, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        this.userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> editUser(@PathVariable("id") String id, @RequestBody User user) {
        User getUser = this.userService.getUserById(id);
        if (getUser != null) {
            this.userService.editUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        User user = this.userService.getUserById(id);
        if (user != null) {
            this.userService.deleteUser(id);
            return new ResponseEntity<>(user.getDisplayName(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<User> loginUser(@PathVariable("displayNameOrEmailAddress") String displayNameOrEmailAddress, @PathVariable("password") String password) {
        User user = this.userService.login(displayNameOrEmailAddress, password);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
