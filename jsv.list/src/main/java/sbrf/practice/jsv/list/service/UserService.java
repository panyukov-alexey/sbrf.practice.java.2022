package sbrf.practice.jsv.list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbrf.practice.jsv.list.model.User;
import sbrf.practice.jsv.list.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(@Autowired UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
