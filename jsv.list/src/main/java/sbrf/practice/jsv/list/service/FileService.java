package sbrf.practice.jsv.list.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import java.io.UncheckedIOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileService {

    private final FileRepository fileRepository;
    private final FileMapper mapper;

    public List<FileDto> findAllFiles() {
        return fileRepository.findAll().stream().map(f -> {
            try {
                return mapper.fileToFileDto(f);
            } catch (IOException e) {
                log.info("Exception encountered while getting all files: {}", e);
                log.info("Error getting file with id: {}", f.getId());
                throw new UncheckedIOException("Error: unable to get files", e);
            }
        }).collect(Collectors.toList());
    }

    public Page<FileDto> findAllFiles(Sort sort, Integer page, Integer size) {
        List<FileDto> files = fileRepository.findAll(PageRequest.of(page, size, sort)).stream().map(f -> {
            try {
                return mapper.fileToFileDto(f);
            } catch (IOException e) {
                log.info("Exception encountered while getting and sorting all files: {}", e);
                throw new UncheckedIOException("Error: unable to get and sort files", e);
            }
        }).collect(Collectors.toList());
        return new PageImpl<>(files);
    }

    public FileDto findFileById(UUID id) throws EntityNotFoundException {
        File file;
        file = fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no file with id='%d'", id)));
        try {
            return mapper.fileToFileDto(file);
        } catch (IOException e) {
            log.info("Error mapping file with id: {}", file.getId());
            throw new UncheckedIOException("Error: unable to get file by id", e);
        }
    }

    public List<FileDto> findFilesByAuthor(UUID authorId) {
        return fileRepository.findByAuthorId(authorId).stream().map(f -> {
            try {
                return mapper.fileToFileDto(f);
            } catch (IOException e) {
                log.info("Exception encountered while getting files uploaded by a user: {}", e);
                log.info("Error mapping file with id: {}", f.getId());
                throw new UncheckedIOException("Error: unable to get files uploaded by the user", e);
            }
        }).collect(Collectors.toList());
    }

    public Page<FileDto> findFilesByAuthor(UUID id, Sort sort, Integer page, Integer size) {
        List<FileDto> files = fileRepository.findByAuthorId(id, PageRequest.of(page, size, sort)).stream().map(f -> {
            try {
                return mapper.fileToFileDto(f);
            } catch (IOException e) {
                log.info("Exception encountered while getting and sorting all files: {}", e);
                throw new UncheckedIOException("Error: unable to get and sort files", e);
            }
        }).collect(Collectors.toList());
        return new PageImpl<>(files);
    }

    public FileDto create(CreateFileDto dto) {
        try {
            File file = fileRepository.save(mapper.createFileDtoToFile(dto));
            return mapper.fileToFileDto(file);
        } catch (IOException e) {
            log.info("Exception encountered while creating file: {}", e);
            throw new UncheckedIOException("Error: unable to create file", e);
        }
    }

    public FileDto update(UUID id, UpdateFileDto dto) {
        try {
            File file = fileRepository.save(mapper.createFileDtoToFile(dto));
            return mapper.fileToFileDto(file);
        } catch (IOException e) {
            log.info("Exception encountered while updating file: {}", e);
            throw new UncheckedIOException("Error: unable to update file", e);
        }
    }

    public void deleteById(UUID id) {
        fileRepository.deleteById(id);
    }

    public byte[] downloadFileById(UUID id) {
        File file = fileRepository.findById(id).orElseThrow();
        return file.getContent();
    }
}
