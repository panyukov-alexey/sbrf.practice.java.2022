package sbrf.practice.jsv.list.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.users.CreateUserDto;
import sbrf.practice.jsv.list.dto.users.UpdateUserDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.service.FileService;
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
    private final UserService userService;
    private final FileService fileService;

    @GetMapping()
    public List<UserDto> findAll() throws IOException {
        log.info("Got all users");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") UUID id) throws IOException {
        log.info("Got a file with given id={}", id);
        return userService.findById(id);
    }

    // @GetMapping("/{id}/files")
    // public List<File> findFilesById(@PathVariable("id") UUID id) {
    //     log.info("Trying to get all files by given id={}", id);
    //     return findById(id).getFiles();
    // }

    @GetMapping("/{id}/files")
    private List<FileDto> findFilesByAuthor(@PathVariable("id") UUID authorId) throws IOException {
        log.info("Got all files the user with given id={} owns", authorId);
        return fileService.findFilesByAuthor(authorId);

    }

    @PostMapping()
    public UserDto create(@Valid @RequestBody CreateUserDto dto) throws IOException {
        log.info("Created new user");
        return userService.create(dto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateUserDto dto) throws IOException {
        log.info("Updated a user with given id={}", id);
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        log.info("Deleted a user with given id={}", id);
        userService.deleteById(id);
    }

}