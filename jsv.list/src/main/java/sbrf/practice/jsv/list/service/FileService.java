package sbrf.practice.jsv.list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.repository.FileRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository repository;

    public List<File> findAllFiles() {
        return repository.findAll();
    }

    public File findFileById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no file with id=\'%d\'", id)));
    }

    public List<File> findFilesByAuthor(UUID authorId) {
        List<File> files = findAllFiles();
        return files.stream().filter(f -> f.getAuthorID().toString().equals(authorId.toString())).collect(Collectors.toList());
    }

    // public Page<File> findAllSorted(Pageable pageable) {
    //     return repository.findAll(pageable);
    // }

    public Page<File> findAllSorted(Sort sort, Integer page, Integer valPerPage) {
        return repository.findAll(PageRequest.of(page, valPerPage, sort));
    }

    public File create(CreateFileDto dto) throws IOException {
        File file = new File(dto.getFileName(), dto.getAuthorID(), dto.getContent());
        return repository.save(file);
    }

    public File update(UUID id, UpdateFileDto dto) throws IOException {
        File file;
        try {
            file = findFileById(id);
            file.setFileName(dto.getFileName());
            file.setAuthorID(file.getAuthorID());
            file.setContent(dto.getContent());
        } catch (EntityNotFoundException e) {
            file = new File(dto.getFileName(), dto.getAuthorID(), dto.getContent());
        }
        return repository.save(file);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
