package sbrf.practice.jsv.list.dto.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.sql.Blob;
import java.util.UUID;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class CreateFileDto{
    @NotNull
    @JsonProperty("file_name")
    private String fileName;

    @NotNull
    @JsonProperty("author_id")
    private UUID authorID;

    @NotNull
    @JsonProperty("content")
    private Blob content;

    public CreateFileDto(String fileName, Blob content) {
        this.fileName = fileName;
        this.content = content;
    }
}