package sbrf.practice.jsv.list.model;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class Base {
    @Column(name = "created_at", updatable = false)
    @ColumnDefault("current_timestamp()")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @ColumnDefault("current_timestamp()")
    @UpdateTimestamp
    private Date updatedAt;
}
