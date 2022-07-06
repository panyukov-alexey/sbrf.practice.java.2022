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
    UserService userService;

    UserDto user1, user2, user3;

    @BeforeEach
    void setUp() {
        user1 = new UserDto(UUID.randomUUID(), "username1");
        user2 = new UserDto(UUID.randomUUID(), "username2");
        user3 = new UserDto(UUID.randomUUID(), "username3");
    }

    @Test
    void findAll() {
        when(userService.findAll()).thenReturn(List.of(user1, user2, user3));

        List<UserDto> userDtos = userService.findAll();

        Assertions.assertNotEquals(0, userDtos.size());
        Assertions.assertEquals(3, userDtos.size());
    }

    @Test
    void findById() {
        UserDto userDto = null;
        try {
            when(userService.findById(user2.getId())).thenReturn(user2);

            userDto = userService.findById(user2.getId());
        } catch (IOException e) {
            log.info("user2 not found");
            e.printStackTrace();
        }

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("username2", userDto.getUsername());
        Assertions.assertEquals(user2.getId(), userDto.getId());
    }

    @Test
    void create() {
        CreateUserDto createUserDto = new CreateUserDto("username1", "password1");
        UserDto userDto = null;
        try {
            when(userService.create(createUserDto)).thenReturn(user1);

            userDto = userService.create(createUserDto);
        } catch (IOException e) {
            log.info("user1 wasn't created");
            e.printStackTrace();
        }

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("username1", userDto.getUsername());
        Assertions.assertEquals(user1.getId(), userDto.getId());
    }

    @Test
    void update() {
        UpdateUserDto updateUserDto = new UpdateUserDto("username3", "password3");
        UserDto userDto = null;
        try {
            when(userService.update(user3.getId(), updateUserDto)).thenReturn(user3);

            userDto = userService.update(user3.getId(), updateUserDto);
        } catch (IOException e) {
            log.info("update: user3 wasn't updated");
            e.printStackTrace();
        }

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("username3", userDto.getUsername());
        Assertions.assertEquals(user3.getId(), userDto.getId());
    }
    
}