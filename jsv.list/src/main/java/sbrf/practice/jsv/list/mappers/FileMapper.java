package sbrf.practice.jsv.list.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.repository.FileRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class FileMapper {

    @Autowired
    protected FileRepository fileRepository;

    public File createFileDtoToFile(CreateFileDto dto) {
        try {
            File f = new File();
            f.setContent(dto.getFile().getBytes());
            f.setFilename(dto.getFile().getOriginalFilename());
            f.setAuthorId(dto.getAuthorId());
            return f;
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot convert File to FileDto", e);
        }
    }

    public File updateFileDtoToFile(UUID id, UpdateFileDto dto) {
        File f = fileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        f.setFilename(dto.getFilename());
        return f;
    }

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "filename", source = "filename"),
            @Mapping(target = "authorId", source = "authorId"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    public abstract File fileDtoToFile(FileDto file);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "filename", source = "filename"),
            @Mapping(target = "authorId", source = "authorId"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    public abstract FileDto fileToFileDto(File file);
}
