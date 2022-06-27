package sbrf.practice.jsv.list.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.users.CreateUserDto;
import sbrf.practice.jsv.list.dto.users.UpdateUserDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.service.UserService;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping()
    public List<UserDto> findAll() throws IOException {
        log.info("Trying to get all existing users");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") UUID id) throws IOException {
        log.info("Trying to get a file by given id={}", id);
        return service.findById(id);
    }

    // @GetMapping("/{id}/files")
    // public List<File> findFilesById(@PathVariable("id") UUID id) {
    //     log.info("Trying to get all files by given id={}", id);
    //     return findById(id).getFiles();
    // }

    @PostMapping()
    public UserDto create(@Valid @RequestBody CreateUserDto dto) throws IOException {
        log.info("Trying to create new user");
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateUserDto dto) throws IOException {
        log.info("Trying to update a user with given id={}", id);
        return this.service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        log.info("Trying to delete a user with given id={}", id);
        service.deleteById(id);
    }

}
