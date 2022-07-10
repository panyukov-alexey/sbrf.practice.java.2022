package sbrf.practice.jsv.list.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sbrf.practice.jsv.list.model.File;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    List<File> findByAuthorId(UUID id);

    Page<File> findByAuthorId(UUID id, Pageable pageable);

    List<File> findByFilenameContains(String filename);

    Page<File> findByFilenameContains(String filename, Pageable pageable);

    List<File> findByAuthorIdAndFilenameContains(UUID id, String filename);

    Page<File> findByAuthorIdAndFilenameContains(UUID id, String filename, Pageable page);
}
