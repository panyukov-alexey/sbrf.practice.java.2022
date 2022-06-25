package sbrf.practice.jsv.list.mappers;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.model.File;

@Mapper
public interface FileMapper {
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "authorID", source = "authorID")
    @Mapping(target = "content", source = "content")
    File fileDtoToFile(CreateFileDto dto);
    File fileDtoToFile(FileDto dto);

    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "authorID", source = "authorID")
    @Mapping(target = "content", source = "content")
    FileDto fileToFileDto(File file);
    FileDto fileToFileDto(Optional<File> file);
}