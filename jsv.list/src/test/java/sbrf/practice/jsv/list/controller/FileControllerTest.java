package sbrf.practice.jsv.list.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
        UUID uuidTest = UUID.randomUUID();
        when(service.findAll())
                .thenReturn(List.of(new File(uuidTest,
                        "test1",
                        new byte[]{(byte) 1, (byte) 0})));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/files"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(uuidTest.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].filename").value("test1"));
    }

    @Test
    void findById() {
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