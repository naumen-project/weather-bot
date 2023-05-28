package com.example.weatherbot.controller;

import com.example.weatherbot.exception.UserNotFoundException;
import com.example.weatherbot.model.User;
import com.example.weatherbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RESTController {

    private final UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> showAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) throws Exception {
        Optional<User> optionalUser = userService.findUserByChatId(id);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException("There is no user with ID = " + id + " in Database");
        return optionalUser.get();
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user){
        userService.createUser(user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteClient(@PathVariable Long id) throws Exception {
        Optional<User> optionalUser = userService.findUserByChatId(id);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException("There is no user with ID = " + id + " in Database");
        userService.deleteUser(id);
        return "User with ID = " + id + " was deleted";
    }
}
