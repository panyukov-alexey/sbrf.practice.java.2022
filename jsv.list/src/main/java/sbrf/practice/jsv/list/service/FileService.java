package sbrf.practice.jsv.list.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

    public FileDto findFileById(UUID id) throws EntityNotFoundException{
        return mapper.fileToFileDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no file with id='%d'", id))));
    }

    public List<FileDto> findFilesByAuthor(UUID authorId) {
        return repository.findByAuthorId(authorId).stream().map(f->mapper.fileToFileDto(f)).collect(Collectors.toList());
    }

    public Page<FileDto> findAllSorted(Sort sort, Integer page, Integer valPerPage) {
        List<FileDto> files = repository.findAll(PageRequest.of(page, valPerPage, sort)).stream().map(f->mapper.fileToFileDto(f)).collect(Collectors.toList());
        return new PageImpl<FileDto>(files);
    }

    public FileDto create(CreateFileDto dto) throws IOException {
        File file = repository.save(mapper.createFileDtoToFile(dto));
        return mapper.fileToFileDto(file);
    }

    public FileDto update(UUID id, UpdateFileDto dto) throws IOException {
        File file = repository.save(mapper.updateFileDtoToFile(dto));
        return mapper.fileToFileDto(file);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
