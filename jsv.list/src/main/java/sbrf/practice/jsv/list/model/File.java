package sbrf.practice.jsv.list.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity()
@Table(name = "files")
public class File extends Base {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @ManyToOne()
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    @JsonIgnore
    private User user;
    @Column(name = "filename")
    private String filename;
    @Column(name = "binary")
    private byte[] binary;

    public File(UUID userId, String filename, byte[] binary) {
        this.userId = userId;
        this.filename = filename;
        this.binary = binary;
    }
}
