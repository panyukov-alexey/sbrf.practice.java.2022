package sbrf.practice.jsv.list.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.CreateFileDto;
import sbrf.practice.jsv.list.dto.CreateUserDto;
import sbrf.practice.jsv.list.dto.UpdateFileDto;
import sbrf.practice.jsv.list.dto.UpdateUserDto;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.service.FileService;
import sbrf.practice.jsv.list.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService service;

    @Autowired
    public FileController( FileService service) {
        this.service = service;
    }

    @GetMapping()
    public List<File> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public File findById(@PathVariable("id") UUID id) {
        return service.findById(id);
    }

    @PostMapping()
    public File create(@Valid @ModelAttribute  CreateFileDto dto) throws IOException {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public File update(@PathVariable("id") UUID id, @Valid @ModelAttribute  UpdateFileDto dto) throws IOException {
        return this.service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
