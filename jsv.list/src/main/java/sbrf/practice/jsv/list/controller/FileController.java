package sbrf.practice.jsv.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.service.FileService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Log4j2
public class FileController {

    private final FileService service;

    @GetMapping("files")
    private List<File> findAllFiles() {
        log.info("Trying to get all existing files");
        return service.findAllFiles();
    }

    @GetMapping("files/{id}")
    private File findFileById(@PathVariable("id") UUID id) {
        log.info("Trying to get a file by given id");
        return service.findFileById(id);
        
    }

    @GetMapping("users/{userId}/files")
    private List<File> findFilesByAuthor(@PathVariable("id") UUID authorId) {
        log.info("Trying to get all files the user with given id owns");
        return service.findFilesByAuthor(authorId);
        
    }

    @PostMapping()
    public File create(@Valid @ModelAttribute CreateFileDto dto) throws IOException {
        log.info("Trying to upload new file");
        return service.create(dto);
        
    }

    @PutMapping("/{id}")
    public File update(@PathVariable("id") UUID id, @Valid @ModelAttribute UpdateFileDto dto) throws IOException {
        log.info("Trying to update a file with given id");
        return this.service.update(id, dto);
        
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        service.deleteById(id);
        log.info("Trying to delete a file with given id");
    }
}
