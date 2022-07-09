package sbrf.practice.jsv.list.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileMapper mapper;

    public List<FileDto> findAllFiles() {
        return fileRepository.findAll().stream().map(f -> mapper.fileToFileDto(f)).collect(Collectors.toList());
    }

    public Page<FileDto> findAllFiles(Sort sort, Integer page, Integer size) {
        return fileRepository.findAll(PageRequest.of(page, size, sort)).map(mapper::fileToFileDto);
    }

    public FileDto findFileById(UUID id) {
        File file;
        file = fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no file with id={}", id)));
        return mapper.fileToFileDto(file);
    }

    public List<FileDto> findFilesByAuthor(UUID authorId) {
        return fileRepository.findByAuthorId(authorId).stream().map(f -> mapper.fileToFileDto(f)).collect(Collectors.toList());
    }

    public Page<FileDto> findFilesByAuthor(UUID id, Sort sort, Integer page, Integer size) {
        return fileRepository.findByAuthorId(id, PageRequest.of(page, size, sort)).map(mapper::fileToFileDto);
    }

    public FileDto create(CreateFileDto dto) {
        return mapper.fileToFileDto(fileRepository.save(mapper.createFileDtoToFile(dto)));
    }

    public FileDto update(UUID id, UpdateFileDto dto) {
        return mapper.fileToFileDto(fileRepository.save(mapper.updateFileDtoToFile(id, dto)));
    }

    public void deleteById(UUID id) {
        fileRepository.deleteById(id);
    }

    public byte[] downloadFileById(UUID id) {
        File file = fileRepository.findById(id).orElseThrow();
        return file.getContent();
    }
}
