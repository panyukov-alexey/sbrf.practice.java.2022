package sbrf.practice.jsv.list.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.CreateUserDto;
import sbrf.practice.jsv.list.dto.UpdateUserDto;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {
    private final UserService service;

    public UserController(@Autowired UserService service) {
        this.service = service;
        log.info("UserController has been initialized");
    }

    @GetMapping()
    public List<User> findAll() {
        log.info("Attempt to find all users");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") UUID id) {
        log.info("Attempt to find user with id={}", id);
        return service.findById(id);
    }

    @PostMapping()
    public User create(@Valid @RequestBody CreateUserDto dto) {
        log.info("Attempt to create new user with username={} and password={}", dto.getUsername(), dto.getPassword());
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateUserDto dto) {
        log.info("Attempt to update user with id {}", id);
        return this.service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        log.info("Attempt to delete user with id {}", id);
        service.deleteById(id);
    }

}