package sbrf.practice.jsv.list.convertors;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converters.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import sbrf.practice.jsv.list.dto.CreateUserDto;
import sbrf.practice.jsv.list.model.User;

@Component
public class UserConvertor {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;
    private Converter<String, String> encodePasswordConverter = e -> passwordEncoder.encode(e);
    public UserConvertor() {
        this.modelMapper = new ModelMapper();

        modelMapper.createTypeMap(CreateUserDto.class, User.class)
        .addMappings(mapper -> mapper.using(encodePasswordConverter).map(CreateUserDto::getPassword, User::setPassword));
    }
    public User convertToEntity(CreateUserDto dto){
        return modelMapper.map(dto,User.class);
    }
}