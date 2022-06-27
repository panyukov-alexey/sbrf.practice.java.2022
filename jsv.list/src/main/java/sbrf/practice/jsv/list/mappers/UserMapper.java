package sbrf.practice.jsv.list.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import sbrf.practice.jsv.list.dto.users.CreateUserDto;
import sbrf.practice.jsv.list.dto.users.UpdateUserDto;
import sbrf.practice.jsv.list.model.User;

@RequiredArgsConstructor
@Mapper(componentModel = "spring")
public abstract class UserMapper {

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
}
