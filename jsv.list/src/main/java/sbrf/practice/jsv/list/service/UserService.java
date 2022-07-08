package sbrf.practice.jsv.list.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sbrf.practice.jsv.list.dto.users.CreateUserDto;
import sbrf.practice.jsv.list.dto.users.UpdateUserDto;
import sbrf.practice.jsv.list.dto.users.UserDto;
import sbrf.practice.jsv.list.mappers.UserMapper;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDto> findAll() {
        return repository.findAll().stream().map(u -> mapper.userToUserDto(u)).collect(Collectors.toList());
    }

    public UserDto findById(UUID id) throws EntityNotFoundException {
        return mapper.userToUserDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no user with id='%d'", id))));
    }

    public UserDto findByUsername(String username) {
        return mapper.userToUserDto(repository.findByUsername(username));
    }

    public UserDto create(CreateUserDto dto) {
        User user = repository.save(mapper.createUserDtoToUser(dto));
        return mapper.userToUserDto(user);
    }

    public UserDto update(UUID id, UpdateUserDto dto) {
        User user = repository.save(mapper.updateUserDtoToUser(dto));
        return mapper.userToUserDto(user);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
