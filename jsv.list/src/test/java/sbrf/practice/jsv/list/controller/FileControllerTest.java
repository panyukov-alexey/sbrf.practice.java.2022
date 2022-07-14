package sbrf.practice.jsv.list.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sbrf.practice.jsv.list.model.File;
import sbrf.practice.jsv.list.service.FileService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @MockBean
    private FileService service;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new FileController(service)).build();
    }

    @Test
    void findAll() throws Exception {
        UUID uuidTest1 = UUID.randomUUID();
        UUID uuidTest2 = UUID.randomUUID();
        UUID uuidTest3 = UUID.randomUUID();

        when(service.findAll())
                .thenReturn(List.of(
                        new File(uuidTest1, "testFile1", new byte[]{(byte) 1, (byte) 0}),
                        new File(uuidTest2, "testFile2", new byte[]{(byte) 0, (byte) 1, (byte) 1}),
                        new File(uuidTest3, "testFile3", new byte[]{(byte) 1, (byte) 1, (byte) 1})
                ));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/files"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(uuidTest1.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].filename").value("testFile1"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(uuidTest2.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].filename").value("testFile2"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].userId").value(uuidTest3.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].filename").value("testFile3"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void findById() throws Exception {
        UUID uuidTest1 = UUID.randomUUID();
        UUID uuidTest2 = UUID.randomUUID();
        UUID uuidTest3 = UUID.randomUUID();

        when(service.findById(uuidTest2))
                .thenReturn(
                        new File(uuidTest2, "testFile2", new byte[]{(byte) 0, (byte) 1, (byte) 1})
                );

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/files/uuidTest2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.is(uuidTest2.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].filename", Matchers.is("testFile2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}