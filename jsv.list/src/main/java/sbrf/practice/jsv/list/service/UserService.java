package sbrf.practice.jsv.list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbrf.practice.jsv.list.dto.CreateUserDto;
import sbrf.practice.jsv.list.dto.UpdateUserDto;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(@Autowired UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public User create(CreateUserDto dto) {
        User user = new User(dto.getUsername(), dto.getPassword());
        return repository.save(user);
    }

    public User update(UUID id, UpdateUserDto dto) {
        User user;
        try {
            user = findById(id);
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
        } catch (EntityNotFoundException e) {
            user = new User(dto.getUsername(), dto.getPassword());
        }
        return repository.save(user);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
