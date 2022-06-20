package sbrf.practice.jsv.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(@Autowired UserService service) {
        this.service = service;
    }

    @GetMapping()
    public List<User> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") UUID id) {
        return this.service.findById(id);
    }

    @PostMapping()
    public User create(@RequestBody() User user) {
        return this.service.create(user);
    }

}
