package com.quantumflo.socialmediaapi.user;

import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

@RestController
public class UserController {
    private UserDaoService service;
    private MessageSource messageSource;

    public UserController(UserDaoService service, MessageSource messageSource) {

        this.service = service;
        this.messageSource =  messageSource;
    }

    @GetMapping(path = "/hello-internationalized")
    public String helloWorldInternationalized() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale );
    }
    @GetMapping("/users")
    public List<User> fetchUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User fetchUser(@PathVariable int id) {
    User user = service.findOne(id);
    if(user==null) {
        throw new UserNotFoundException("id: " + id);
    }

        return user;

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
         User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteById(id);
    }
}
