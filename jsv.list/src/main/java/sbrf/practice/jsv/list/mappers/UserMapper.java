package sbrf.practice.jsv.list.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import sbrf.practice.jsv.list.dto.users.CreateUserDto;
import sbrf.practice.jsv.list.dto.users.UpdateUserDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.model.User;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mappings({
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))"),
    })
    public abstract User createUserDtoToUser(CreateUserDto dto);

    @Mappings({
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))"),
    })
    public abstract User updateUserDtoToUser(UUID id, UpdateUserDto dto);

    public abstract UserDto userToUserDto(User user);
}
