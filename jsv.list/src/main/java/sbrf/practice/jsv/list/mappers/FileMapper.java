package sbrf.practice.jsv.list.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.model.File;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mappings({
            @Mapping(target = "filename", expression = "java(dto.getFile().getOriginalFilename())"),
            @Mapping(target = "authorId", source = "dto.authorId"),
            @Mapping(target = "content", expression = "java(dto.getFile().getBytes())"),
    })
    File createFileDtoToFile(CreateFileDto dto);

    @Mappings({
            @Mapping(target = "filename", expression = "java(dto.getFile().getOriginalFilename())"),
            @Mapping(target = "authorId", source = "dto.authorId"),
            @Mapping(target = "content", expression = "java(dto.getFile().getBytes())"),
    })
    File updateFileDtoToFile(UpdateFileDto dto);

    @Mappings({
        @Mapping(target = "fileName", source = "fileName"),
        @Mapping(target = "authorId", source = "authorId"),
        @Mapping(target = "content", source = "content")
    })
    FileDto fileToFileDto(File file);
}
