package sbrf.practice.jsv.list.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.mappers.FileMapper;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.repository.FileRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository repository;
    private final FileMapper mapper;

    public List<File> findAllFiles() {
        return repository.findAll();
    }

    public File findFileById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no file with id='%d'", id)));
    }

    // public List<FileDto> findFilesByAuthor(UUID authorId) {
    //     List<FileDto> files = findAllFiles();
    //     return files.stream().filter(f -> f.getAuthorId().toString().equals(authorId.toString())).collect(Collectors.toList());
    // }

    // public Page<File> findAllSorted(Pageable pageable) {
    //     return repository.findAll(pageable);
    // }

    public Page<File> findAllSorted(Sort sort, Integer page, Integer valPerPage) {
        List<File> files = repository.findAll(PageRequest.of(page, valPerPage, sort)).toList();
        return new PageImpl<File>(files);
    }

    public File create(CreateFileDto dto) throws IOException {
        return repository.save(mapper.fileDtoToFile(dto));
    }

    public File update(UUID id, UpdateFileDto dto) throws IOException {
        return repository.save(mapper.fileDtoToFile(dto));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
