package sbrf.practice.jsv.list.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
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

    private final FileService fileService;

    @GetMapping()
    private List<FileDto> findAllFiles() throws IOException {
        log.info("Got all files");
        return fileService.findAllFiles();
    }

    @GetMapping("/{id}")
    private FileDto findFileById(@PathVariable("id") UUID id) throws IOException {
        log.info("Got a file with given id={}", id);
        return fileService.findFileById(id);

    }

    @GetMapping("/sorted")
    private Page<FileDto> findAllSorted(@RequestParam("sort") Sort sort,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("val") Integer valPerPage) throws IOException {
        log.info("Got and sorted all files by", sort);
        return fileService.findAllSorted(sort, page, valPerPage);
    }


    @PostMapping("/upload")
    public FileDto create(@Valid @ModelAttribute CreateFileDto dto) throws IOException {
        log.info("Uploaded new file");
        return fileService.create(dto);

    }

    @GetMapping("/{id}/download")
    private byte[] downloadFileById(@PathVariable("id") UUID id) {
        log.info("File with id={} was dowloaded", id);
        return fileService.downloadFileById(id);
    }

    @PutMapping("/{id}")
    public FileDto update(@PathVariable("id") UUID id, @Valid @ModelAttribute UpdateFileDto dto) throws IOException {
        log.info("Updated a file with given id={}", id);
        return fileService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        log.info("Deleted a file with given id={}", id);
        fileService.deleteById(id);
    }
}
