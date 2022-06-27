package sbrf.practice.jsv.list.mappers;

import java.io.IOException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.model.File;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mappings({
            @Mapping(target = "filename", expression = "java(dto.getFile().getOriginalFilename())"),
            @Mapping(target = "authorId", source = "dto.authorId"),
            @Mapping(target = "content", expression = "java(dto.getFile().getBytes())"),
    })
    File createFileDtoToFile(CreateFileDto dto) throws IOException;

    @Mappings({
            @Mapping(target = "filename", expression = "java(dto.getFile().getOriginalFilename())"),
            @Mapping(target = "authorId", source = "dto.authorId"),
            @Mapping(target = "content", expression = "java(dto.getFile().getBytes())"),
    })
    File updateFileDtoToFile(UpdateFileDto dto) throws IOException;

    @Mappings({
        @Mapping(target = "fileName", source = "filename"),
        @Mapping(target = "authorId", source = "authorId"),
    })
    FileDto fileToFileDto(File file) throws IOException;
}
