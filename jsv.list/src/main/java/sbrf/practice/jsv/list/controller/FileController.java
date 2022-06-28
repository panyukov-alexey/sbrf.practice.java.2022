package sbrf.practice.jsv.list.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.service.FileService;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
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
    private List<FileDto> findAllFiles() throws IOException{
        log.info("Trying to get all existing files");
        return fileService.findAllFiles();
    }

    @GetMapping("/{id}")
    private FileDto findFileById(@PathVariable("id") UUID id) throws IOException{
        log.info("Trying to get a file by given id={}", id);
        return fileService.findFileById(id);

    }

    @GetMapping("/sorted")
    private Page<FileDto> findAllSorted(@RequestParam("sort") Sort sort,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("val") Integer valPerPage) throws IOException{
        log.info("Trying to get and sort all existing files by", sort);
        return fileService.findAllSorted(sort, page, valPerPage);
    }


    @PostMapping("/upload")
    public FileDto create(@Valid @ModelAttribute CreateFileDto dto) throws IOException {
        log.info("Trying to upload new file");
        return fileService.create(dto);

    }

    @GetMapping("/{id}/download")
    private byte[] downloadFileById(@PathVariable("id") UUID id) {
        return fileService.downloadFileById(id);
    }

    @PutMapping("/{id}")
    public FileDto update(@PathVariable("id") UUID id, @Valid @ModelAttribute UpdateFileDto dto) throws IOException {
        log.info("Trying to update a file with given id={}", id);
        return fileService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        log.info("Trying to delete a file with given id={}", id);
        fileService.deleteById(id);
    }
}
