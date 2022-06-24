package sbrf.practice.jsv.list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sbrf.practice.jsv.list.model.File;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
}
