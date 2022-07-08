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

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mappings({
            @Mapping(target = "username", source = "dto.username"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))"),
    })
    public abstract User createUserDtoToUser(CreateUserDto dto);

    @Mappings({
            @Mapping(target = "username", source = "dto.username"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))"),
    })
    public abstract User updateUserDtoToUser(UpdateUserDto dto);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "username", source = "username"),
    })
    public abstract UserDto userToUserDto(User user);
}
