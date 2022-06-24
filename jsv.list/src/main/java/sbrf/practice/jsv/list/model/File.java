package sbrf.practice.jsv.list.model;

import java.sql.Blob;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
public class File extends Base{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "file_name", unique = true)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "author_id", insertable=false, updatable=false)
    private User author;

    @Column(name = "author_id")
    private UUID authorID;

    @Column(name = "content")
    private Blob content;

    public File(String fileName, UUID authorID, Blob content) {
        this.fileName = fileName;
        this.authorID = authorID;
        this.content = content;
    }
}
