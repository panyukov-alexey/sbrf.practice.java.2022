package sbrf.practice.jsv.list.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class File extends Base {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "length")
    private Long length;

    @ManyToOne()
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @JsonIgnore
    private User author;

    @Column(name = "author_id")
    @Type(type = "uuid-char")
    private UUID authorId;

    @Column(name = "content")
    @Lob
    private byte[] content;

    public File(String filename, Long length, UUID authorId, byte[] content) {
        this.filename = filename;
        this.length = length;
        this.authorId = authorId;
        this.content = content;
    }
}
