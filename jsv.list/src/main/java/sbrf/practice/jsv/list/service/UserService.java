package sbrf.practice.jsv.list.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sbrf.practice.jsv.list.dto.users.CreateUserDto;
import sbrf.practice.jsv.list.dto.users.UpdateUserDto;
import sbrf.practice.jsv.list.mappers.UserMapper;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no user with id='%d'", id)));
    }

    public User create(CreateUserDto dto) {
        return repository.save(mapper.createUserDtoToUser(dto));
    }

    public User update(UUID id, UpdateUserDto dto) {
        return repository.save(mapper.updateUserDtoToUser(dto));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
