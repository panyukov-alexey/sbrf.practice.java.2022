package sbrf.practice.jsv.list.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "files")
@Data
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

    public File(String filename, UUID authorId, byte[] content) {
        this.filename = filename;
        this.authorId = authorId;
        this.content = content;
    }
}
