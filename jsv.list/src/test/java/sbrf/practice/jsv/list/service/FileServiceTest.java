package sbrf.practice.jsv.list.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.dto.users.UserDto;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@Log4j2
@ExtendWith(SpringExtension.class)
class FileServiceTest {
    @Mock
    private FileService fileService;
    private FileDto fileDto1, fileDto2, fileDto3;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(UUID.randomUUID(), "userNameDto");

        fileDto1 = new FileDto(UUID.randomUUID(), "fileNameDto1", userDto.getId(), new Date(), new Date());
        fileDto2 = new FileDto(UUID.randomUUID(), "fileNameDto2", userDto.getId(), new Date(), new Date());
        fileDto3 = new FileDto(UUID.randomUUID(), "fileNameDto3", userDto.getId(), new Date(), new Date());
    }

    @Test
    void findAllFiles() throws IOException {
        List<FileDto> fileDtos;

        when(fileService.findAllFiles()).thenReturn(List.of(fileDto1, fileDto2, fileDto3));
        fileDtos = fileService.findAllFiles();

        Assertions.assertNotEquals(0, fileDtos.size());
        Assertions.assertEquals(3, fileDtos.size());

    }

    @Test
    void findFileById() throws IOException {
        FileDto fileDto;

        when(fileService.findFileById(fileDto2.getId())).thenReturn(fileDto2);
        fileDto = fileService.findFileById(fileDto2.getId());

        Assertions.assertNotNull(fileDto);
        Assertions.assertEquals("fileNameDto2", fileDto.getFileName());
        Assertions.assertEquals(fileDto2.getId(), fileDto.getId());
        Assertions.assertEquals(fileDto2.getAuthorId(), fileDto.getAuthorId());
    }

    @Test
    void findFilesByAuthor() throws IOException {
        List<FileDto> fileDtos;

        when(fileService.findFilesByAuthor(userDto.getId())).thenReturn(List.of(fileDto1, fileDto2, fileDto3));
        fileDtos = fileService.findFilesByAuthor(userDto.getId());

        Assertions.assertNotEquals(0, fileDtos.size());
        Assertions.assertEquals(3, fileDtos.size());
        Assertions.assertEquals(userDto.getId(), fileDtos.get(0).getAuthorId());
    }

    @Test
    void findAllSorted() throws IOException {
        Sort sort = Sort.unsorted();
        Integer page = 1;
        Integer valPerPage = 3;
        Page<FileDto> fileDtoPage;
        PageImpl<FileDto> pageImpl = new PageImpl<>(List.of(fileDto1, fileDto2, fileDto3));

        when(fileService.findAllSorted(sort, page, valPerPage)).thenReturn(pageImpl);
        fileDtoPage = fileService.findAllSorted(sort, page, valPerPage);

        Assertions.assertEquals(1, fileDtoPage.getTotalPages());
        Assertions.assertEquals(3, fileDtoPage.getTotalElements());


    }

    @Test
    void create() throws IOException {
        FileDto fileDto;
        MultipartFile multipartFile = null;
        CreateFileDto createFileDto = new CreateFileDto(userDto.getId(), multipartFile);

        when(fileService.create(createFileDto)).thenReturn(fileDto3);
        fileDto = fileService.create(createFileDto);

        Assertions.assertNotNull(fileDto);
        Assertions.assertEquals(fileDto3.getFileName(), fileDto.getFileName());
        Assertions.assertEquals(fileDto3.getAuthorId(), fileDto.getAuthorId());
        Assertions.assertEquals(fileDto3.getId(), fileDto.getId());
    }

    @Test
    void update() throws IOException {
        FileDto fileDto;

        MultipartFile multipartFile = null;
        UpdateFileDto updateFileDto = new UpdateFileDto(userDto.getId(), multipartFile);

        when(fileService.update(fileDto1.getId(), updateFileDto)).thenReturn(fileDto1);
        fileDto = fileService.update(fileDto1.getId(), updateFileDto);

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