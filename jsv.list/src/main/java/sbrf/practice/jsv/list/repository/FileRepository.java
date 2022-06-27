package sbrf.practice.jsv.list.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import sbrf.practice.jsv.list.model.File;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    public List<File> findByAuthorId(UUID id);

    public Streamable<Order> findByAuthorId(UUID id, PageRequest of);
}
