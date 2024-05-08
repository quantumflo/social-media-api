package com.quantumflo.socialmediaapi.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }
    @GetMapping("/users")
    public List<User> fetchUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User fetchUser(@PathVariable int id) {
    return service.findOne(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return service.save(user);
    }
}
