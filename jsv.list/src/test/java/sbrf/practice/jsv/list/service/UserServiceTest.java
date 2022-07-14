package sbrf.practice.jsv.list.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sbrf.practice.jsv.list.dto.users.CreateUserDto;
import sbrf.practice.jsv.list.dto.users.UpdateUserDto;
import sbrf.practice.jsv.list.dto.users.UserDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserService userService;
    private UserDto userDto1, userDto2, userDto3;

    @BeforeEach
    void setUp() {
        userDto1 = new UserDto(UUID.randomUUID(), "userNameDto1");
        userDto2 = new UserDto(UUID.randomUUID(), "userNameDto2");
        userDto3 = new UserDto(UUID.randomUUID(), "userNameDto3");
    }

    @Test
    void findAll() {
        when(userService.findAll()).thenReturn(List.of(userDto1, userDto2, userDto3));

        List<UserDto> userDtos = userService.findAll();

        Assertions.assertNotEquals(0, userDtos.size());
        Assertions.assertEquals(3, userDtos.size());
    }

    @Test
    void findById() throws IOException {
        UserDto userDto;

        when(userService.findById(userDto2.getId())).thenReturn(userDto2);
        userDto = userService.findById(userDto2.getId());

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("userNameDto2", userDto.getUsername());
        Assertions.assertEquals(userDto2.getId(), userDto.getId());
    }

    @Test
    void findByUsername() throws IOException {
        UserDto userDto;

        when(userService.findByUsername("userNameDto2")).thenReturn(userDto2);
        userDto = userService.findByUsername("userNameDto2");

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("userNameDto2", userDto.getUsername());
        Assertions.assertEquals(userDto2.getId(), userDto.getId());
    }

    @Test
    void create() throws IOException {
        CreateUserDto createUserDto = new CreateUserDto("userNameDto1", "passwordDto1");
        UserDto userDto;

        when(userService.create(createUserDto)).thenReturn(userDto1);
        userDto = userService.create(createUserDto);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("userNameDto1", userDto.getUsername());
        Assertions.assertEquals(userDto1.getId(), userDto.getId());
    }

    @Test
    void update() throws IOException {
        UpdateUserDto updateUserDto = new UpdateUserDto("userNameDto3", "passwordDto3");
        UserDto userDto;

        when(userService.update(userDto3.getId(), updateUserDto)).thenReturn(userDto3);
        userDto = userService.update(userDto3.getId(), updateUserDto);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("userNameDto3", userDto.getUsername());
        Assertions.assertEquals(userDto3.getId(), userDto.getId());
    }
}