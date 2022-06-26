package sbrf.practice.jsv.list.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import sbrf.practice.jsv.list.dto.files.CreateFileDto;
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
    File fileDtoToFile(CreateFileDto dto) throws IOException;

    @Mappings({
            @Mapping(target = "filename", expression = "java(dto.getFile().getOriginalFilename())"),
            @Mapping(target = "authorId", source = "dto.authorId"),
            @Mapping(target = "content", expression = "java(dto.getFile().getBytes())"),
    })
    File updateFileDtoToFile(UpdateFileDto dto) throws IOException;
}
