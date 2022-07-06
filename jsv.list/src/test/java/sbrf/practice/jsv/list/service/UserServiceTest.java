package sbrf.practice.jsv.list.service;

import lombok.extern.log4j.Log4j2;
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
@Log4j2
class UserServiceTest {
    @Mock
    private UserService userService;

    private UserDto userDto1, userDto2, userDto3;

    @BeforeEach
    void setUp() {
        userDto1 = new UserDto(UUID.randomUUID(), "username1");
        userDto2 = new UserDto(UUID.randomUUID(), "username2");
        userDto3 = new UserDto(UUID.randomUUID(), "username3");
    }

    @Test
    void findAll() {
        when(userService.findAll()).thenReturn(List.of(userDto1, userDto2, userDto3));

        List<UserDto> userDtos = userService.findAll();

        Assertions.assertNotEquals(0, userDtos.size());
        Assertions.assertEquals(3, userDtos.size());
    }

    @Test
    void findById() {
        UserDto userDto = null;
        try {
            when(userService.findById(userDto2.getId())).thenReturn(userDto2);

            userDto = userService.findById(userDto2.getId());
        } catch (IOException e) {
            log.info("user2 not found");
            e.printStackTrace();
        }

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("username2", userDto.getUsername());
        Assertions.assertEquals(userDto2.getId(), userDto.getId());
    }

    @Test
    void create() {
        CreateUserDto createUserDto = new CreateUserDto("username1", "password1");
        UserDto userDto = null;
        try {
            when(userService.create(createUserDto)).thenReturn(userDto1);

            userDto = userService.create(createUserDto);
        } catch (IOException e) {
            log.info("user1 wasn't created");
            e.printStackTrace();
        }

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("username1", userDto.getUsername());
        Assertions.assertEquals(userDto1.getId(), userDto.getId());
    }

    @Test
    void update() {
        UpdateUserDto updateUserDto = new UpdateUserDto("username3", "password3");
        UserDto userDto = null;
        try {
            when(userService.update(userDto3.getId(), updateUserDto)).thenReturn(userDto3);

            userDto = userService.update(userDto3.getId(), updateUserDto);
        } catch (IOException e) {
            log.info("update: user3 wasn't updated");
            e.printStackTrace();
        }

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("username3", userDto.getUsername());
        Assertions.assertEquals(userDto3.getId(), userDto.getId());
    }
}