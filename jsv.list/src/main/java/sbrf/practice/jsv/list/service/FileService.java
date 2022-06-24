package sbrf.practice.jsv.list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.repository.FileRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository repository;

    @Autowired
    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public List<File> findAll() {
        return repository.findAll();
    }

    public File findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no file with id=\'%d\'", id)));
    }

    public File create(CreateFileDto dto) throws IOException {
        File file = new File(dto.getUserId(), dto.getFile().getOriginalFilename(), dto.getFile().getBytes());
        return repository.save(file);
    }

    public File update(UUID id, UpdateFileDto dto) throws IOException {
        File file;
        try {
            file = findById(id);
            file.setUserId(dto.getUserId());
            file.setFilename(dto.getFile().getOriginalFilename());
            file.setBinary(dto.getFile().getBytes());
        } catch (EntityNotFoundException e) {
            file = new File(dto.getUserId(), dto.getFile().getOriginalFilename(), dto.getFile().getBytes());
        }
        return repository.save(file);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
