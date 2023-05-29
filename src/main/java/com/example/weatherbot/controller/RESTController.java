package com.example.weatherbot.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.weatherbot.exception.UserNotFoundException;
import com.example.weatherbot.model.User;
import com.example.weatherbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "REST Controller", description = "REST API Controller")
@OpenAPIDefinition(
        info = @Info(
                title = "Weather Bot System Api",
                description = "Weather Bot", version = "1.1.2"
        )
)
@RestController
@RequestMapping("/api")
public class RESTController {

    private final UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "get all users", description = "allows to get all users that exist in the database")
    @GetMapping("/users")
    public List<User> showAllUsers(){
        return userService.getAllUsers();
    }

    @Operation(summary = "get any user", description = "allows to get any user that exists in the database")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable @Parameter(description = "user id") Long id) throws Exception {
        Optional<User> optionalUser = userService.findUserByChatId(id);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException("There is no user with ID = " + id + " in Database");
        return optionalUser.get();
    }

    @Operation(summary = "add new user", description = "allows to save new user in the database")
    @PostMapping("/users")
    public User addNewUser(@RequestBody User user){
        userService.createUser(user);
        return user;
    }

    @Operation(summary = "update user", description = "allows to update any user that exists in the database")
    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        userService.updateUser(user);
        return user;
    }

    @Operation(summary = "delete user", description = "allows to delete any user that exists in the database")
    @DeleteMapping("/users/{id}")
    public String deleteClient(@PathVariable @Parameter(description = "user id") Long id) throws Exception {
        Optional<User> optionalUser = userService.findUserByChatId(id);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException("There is no user with ID = " + id + " in Database");
        userService.deleteUser(id);
        return "User with ID = " + id + " was deleted";
    }
}
