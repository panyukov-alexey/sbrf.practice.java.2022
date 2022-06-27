package sbrf.practice.jsv.list.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.service.FileService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
@Log4j2
public class FileController {

    private final FileService service;

    @GetMapping()
    private List<File> findAllFiles() {
        log.info("Trying to get all existing files");
        return service.findAllFiles();
    }

    @GetMapping("/{id}")
    private File findFileById(@PathVariable("id") UUID id) {
        log.info("Trying to get a file by given id={}", id);
        return service.findFileById(id);

    }

    // @GetMapping("users/{userId}/files")
    // private List<FileDto> findFilesByAuthor(@PathVariable("id") UUID authorId) {
    //    log.info("Trying to get all files the user with given id owns");
    //    return service.findFilesByAuthor(authorId);
    //
    // }

    @GetMapping("/sorted")
    private Page<File> findAllSorted(@RequestParam("sort") Sort sort,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("val") Integer valPerPage) {
        log.info("Trying to get and sort all existing files by", sort);
        return service.findAllSorted(sort, page, valPerPage);
    }


    @PostMapping("/upload")
    public File create(@Valid @ModelAttribute CreateFileDto dto) throws IOException {
        log.info("Trying to upload new file");
        return service.create(dto);

    }

    @PutMapping("/{id}")
    public File update(@PathVariable("id") UUID id, @Valid @ModelAttribute UpdateFileDto dto) throws IOException {
        log.info("Trying to update a file with given id={}", id);
        return this.service.update(id, dto);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        log.info("Trying to delete a file with given id={}", id);
        service.deleteById(id);
    }
}
