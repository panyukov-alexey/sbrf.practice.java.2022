package sbrf.practice.jsv.list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.mappers.FileMapper;
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
    private final FileMapper mapper;

    public List<FileDto> findAllFiles() {
        return repository.findAll().stream().map(f->mapper.fileToFileDto(f)).collect(Collectors.toList());
    }

    public FileDto findFileById(UUID id) {
        return mapper.fileToFileDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no file with id=\'%d\'", id))));
    }

    public List<FileDto> findFilesByAuthor(UUID authorId) {
        List<FileDto> files = findAllFiles();
        return files.stream().filter(f -> f.getAuthorID().toString().equals(authorId.toString())).collect(Collectors.toList());
    }

    // public Page<File> findAllSorted(Pageable pageable) {
    //     return repository.findAll(pageable);
    // }

    public Page<FileDto> findAllSorted(Sort sort, Integer page, Integer valPerPage) {
        List<FileDto> files = repository.findAll(PageRequest.of(page, valPerPage, sort)).stream().map(f->mapper.fileToFileDto(f)).collect(Collectors.toList());
        return new PageImpl<>(files);
    }

    public FileDto create(CreateFileDto dto) throws IOException {
        File file = mapper.fileDtoToFile(dto);
        File createdFile = repository.save(file);
        return mapper.fileToFileDto(createdFile);
    }

    public FileDto update(UUID id, UpdateFileDto dto) throws IOException {
        FileDto fileToUpdate;
        try {
            fileToUpdate = findFileById(id);
            fileToUpdate.setFileName(dto.getFileName());
            fileToUpdate.setAuthorID(fileToUpdate.getAuthorID());
            fileToUpdate.setContent(dto.getContent());
        } catch (EntityNotFoundException e) {
            throw e;
        }
        File file = mapper.fileDtoToFile(fileToUpdate);
        File updatedFile =  repository.save(file);
        return mapper.fileToFileDto(updatedFile);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
