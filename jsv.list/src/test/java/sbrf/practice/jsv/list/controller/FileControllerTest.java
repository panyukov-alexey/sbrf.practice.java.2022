package sbrf.practice.jsv.list.controller;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sbrf.practice.jsv.list.dto.files.CreateFileDto;
import sbrf.practice.jsv.list.dto.files.FileDto;
import sbrf.practice.jsv.list.dto.files.UpdateFileDto;
import sbrf.practice.jsv.list.service.FileService;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FileController.class)
@Log4j2
class FileControllerTest {

    @MockBean
    private FileService fileService;
    private UpdateFileDto updateFileDto;
    @MockBean
    private CreateFileDto createFileDto;
    @MockBean
    private FileDto fileDto;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before("")
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
//    @WithUserDetails("user")
//    @WithSecurityContext()
        // TODO: 01.07.2022 @WithSecurityContext либо @WithUserDetails
        // TODO: 02.07.2022 выполнить тест от лица пользователя из базы данных для ручной авторизации
    @WithMockUser(username = "Charlie Scene", password = "123fds", roles = {"USER", "ADMIN"})
    void create() {
        createFileDto = new CreateFileDto(UUID.randomUUID(), new MockMultipartFile("MockMultipartFile", new byte[]{0, 1, 1}));
        try {
            when(fileService.create(createFileDto))
                    .thenReturn(fileDto);
        } catch (IOException e) {
            log.info("create: when -failed");
            throw new RuntimeException(e);
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/files/upload", createFileDto))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            log.info("create: mockMvc -failed");
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        UUID fakeId = UUID.randomUUID();

        try {
            when(fileService.update(fakeId, updateFileDto))
                    .thenReturn(fileDto);
        } catch (IOException e) {
            log.info("update: when -failed");
            throw new RuntimeException(e);
        }

        try {
            mockMvc.perform(put("/files/fakeId", updateFileDto))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            log.info("update: mockMvc -failed");
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteById() {
        UUID fakeId = UUID.randomUUID();
        try {
            fileService.update(fakeId,updateFileDto);
//            when(fileService.deleteById(fakeId)).thenReturn();

            mockMvc.perform(delete("/files/fakeId"))
                    .andExpect(MockMvcResultMatchers.status().isAccepted())
                    .andDo(print());
        } catch (Exception e) {
            log.info("deleteById : mockMvc -failed");
            throw new RuntimeException(e);
        }

    }
}