package sbrf.practice.jsv.list.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return fileRepository.findAll().stream().map(mapper::fileToFileDto).collect(Collectors.toList());
    }

    public Page<FileDto> findAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable).map(mapper::fileToFileDto);
    }

    public FileDto findFileById(UUID id) throws EntityNotFoundException {
        File file;
        file = fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no file with id={}", id)));
        return mapper.fileToFileDto(file);
    }

    public List<FileDto> findFilesByAuthor(UUID authorId) {
        return fileRepository.findByAuthorId(authorId).stream().map(mapper::fileToFileDto).collect(Collectors.toList());
    }

    public Page<FileDto> findFilesByAuthor(UUID id, Pageable pageable) {
        return fileRepository.findByAuthorId(id, pageable).map(mapper::fileToFileDto);
    }

    public List<FileDto> findByFilenameContains(String filename) {
        return fileRepository.findByFilenameContains(filename).stream().map(mapper::fileToFileDto).collect(Collectors.toList());
    }

    public Page<FileDto> findByFilenameContains(String filename, Pageable pageable) {
        return fileRepository.findByFilenameContains(filename,pageable).map(mapper::fileToFileDto);
    }

    public Page<FileDto> findByAuthorIdAndFilenameContains(UUID id, String filename, Pageable pageable) {
        return fileRepository.findByAuthorIdAndFilenameContains(id, filename, pageable).map(mapper::fileToFileDto);
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
