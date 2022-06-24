package sbrf.practice.jsv.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
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
public class FileController {
    private final FileService service;

    @GetMapping("files")
    private List<File> findAllFiles() {
        return service.findAllFiles();
        log.info("Trying to get all existing files");
    }

    @GetMapping("files/sorted")
    private List<File> findAllSorted(@RequestParam("sort") String sort, Pageable pageable) {
        return service.findAllSorted(pageable);
        log.info("Trying to get and sort all existing files");
    }

    @GetMapping("files/{id}")
    private File findFileById(@PathVariable("id") UUID id) {
        return service.findFileById(id);
        log.info("Trying to get a file by given id");
    }

    @GetMapping("users/{userId}/files")
    private List<File> findFilesByAuthor(@PathVariable("id") UUID authorId) {
        return service.findFilesByAuthor(authorId);
        log.info("Trying to get all files the user with given id owns");
    }

    @PostMapping()
    public File create(@Valid @ModelAttribute CreateFileDto dto) throws IOException {
        return service.create(dto);
        log.info("Trying to upload new file")
    }

    @PutMapping("/{id}")
    public File update(@PathVariable("id") UUID id, @Valid @ModelAttribute UpdateFileDto dto) throws IOException {
        return this.service.update(id, dto);
        log.info("Trying to update a file with given id")
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        service.deleteById(id);
        log.info("Trying to delete a file with given id")
    }
}
