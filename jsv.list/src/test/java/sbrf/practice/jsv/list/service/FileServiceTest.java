package sbrf.practice.jsv.list.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.dto.users.UserDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Log4j2
class FileServiceTest {

    @Mock
    private FileService fileService;

    FileDto fileDto1, fileDto2, fileDto3;
    UserDto userDto1;

    @BeforeEach
    void setup() {
        userDto1 = new UserDto(UUID.randomUUID(), "username1");

        fileDto1 = new FileDto(UUID.randomUUID(), "filename1", userDto1.getId());
        fileDto2 = new FileDto(UUID.randomUUID(), "filename2", userDto1.getId());
        fileDto3 = new FileDto(UUID.randomUUID(), "filename3", userDto1.getId());
    }

    @Test
    void findAllFiles() {
        List<FileDto> fileDtos = null;
        try {
            when(fileService.findAllFiles()).thenReturn(List.of(fileDto1, fileDto2, fileDto3));

            fileDtos = fileService.findAllFiles();
        } catch (IOException e) {
            log.info("fileDtos not found");
            e.printStackTrace();
        }

        Assertions.assertNotEquals(0, fileDtos.size());
        Assertions.assertEquals(3, fileDtos.size());
    }

    @Test
    void findFileById() {
        FileDto fileDto = null;
        try {
            when(fileService.findFileById(fileDto2.getId())).thenReturn(fileDto2);

            fileDto = fileService.findFileById(fileDto2.getId());
        } catch (IOException e) {
            log.info("fileDto not found");
            e.printStackTrace();
        }

        Assertions.assertNotNull(fileDto);
        Assertions.assertEquals("filename2", fileDto.getFileName());
        Assertions.assertEquals(fileDto2.getId(), fileDto.getId());
        Assertions.assertEquals(fileDto2.getAuthorId(), fileDto.getAuthorId());
    }

    @Test
    void findFilesByAuthor() {
        List<FileDto> fileDtos = null;
        try {
            when(fileService.findFilesByAuthor(userDto1.getId())).thenReturn(List.of(fileDto1, fileDto2, fileDto3));

            fileDtos = fileService.findFilesByAuthor(userDto1.getId());
        } catch (IOException e) {
            log.info("author's files not found");
            e.printStackTrace();
        }

        Assertions.assertNotEquals(0, fileDtos.size());
        Assertions.assertEquals(3, fileDtos.size());
        Assertions.assertEquals(userDto1.getId(), fileDtos.get(0).getAuthorId());
    }

    @Test
    void findAllSorted() {
//        Page<FileDto> fileDtos;
//        Sort sort = Sort.unsorted();
//        Integer page = 3;
//        Integer valPerPage = 3;
//        when(fileService.findAllSorted(sort, page, valPerPage))
//                .thenReturn(fileDtos);
//
//        fileDtos = fileService.findAllSorted(sort, page, valPerPage);
//
//        Assertions.assertNotNull(fileDtos);
//        Assertions.assertEquals(3, fileDtos.getTotalPages());
//        Assertions.assertEquals(Sort.unsorted(), fileDtos.getSort());
    }

    @Test
    void create() {
        FileDto fileDto = null;
        MultipartFile multipartFile = null;
        CreateFileDto createFileDto = new CreateFileDto(userDto1.getId(), multipartFile);
        try {
            when(fileService.create(createFileDto)).thenReturn(fileDto3);

            fileDto = fileService.create(createFileDto);
        } catch (IOException e) {
            log.info("fileDto1 wasn't created");
            e.printStackTrace();
        }

        Assertions.assertNotNull(fileDto);
        Assertions.assertEquals(fileDto3.getFileName(), fileDto.getFileName());
        Assertions.assertEquals(fileDto3.getAuthorId(), fileDto.getAuthorId());
        Assertions.assertEquals(fileDto3.getId(), fileDto.getId());
    }

    @Test
    void update() {
        FileDto fileDto = null;
        MultipartFile multipartFile = null;
        UpdateFileDto updateFileDto = new UpdateFileDto(userDto1.getId(), multipartFile);
        try {
            when(fileService.update(fileDto1.getId(), updateFileDto)).thenReturn(fileDto1);

            fileDto = fileService.update(fileDto1.getId(), updateFileDto);
        } catch (IOException e) {
            log.info("file wasn't updated");
            e.printStackTrace();
        }

        Assertions.assertNotNull(fileDto);
        Assertions.assertEquals(fileDto1.getFileName(), fileDto.getFileName());
        Assertions.assertEquals(fileDto1.getAuthorId(), fileDto.getAuthorId());
        Assertions.assertEquals(fileDto1.getId(), fileDto.getId());
    }

    @Test
    void downloadFileById() {
        byte[] bytes;
        when(fileService.downloadFileById(fileDto1.getId())).thenReturn(new byte[]{1, 0, 1});

        bytes = fileService.downloadFileById(fileDto1.getId());

        Assertions.assertNotEquals(0, bytes.length);
        Assertions.assertEquals(3, bytes.length);
    }
}