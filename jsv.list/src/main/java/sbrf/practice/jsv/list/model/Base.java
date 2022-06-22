package sbrf.practice.jsv.list.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Base {
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
