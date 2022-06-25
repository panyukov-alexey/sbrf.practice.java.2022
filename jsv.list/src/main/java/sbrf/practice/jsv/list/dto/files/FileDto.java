package sbrf.practice.jsv.list.dto.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

import java.sql.Blob;
import java.util.UUID;
import javax.validation.constraints.NotNull;

@Getter
@Setter
// @AllArgsConstructor
public class FileDto{
    @NotNull
    @JsonProperty("file_name")
    private String fileName;

    @NotNull
    @JsonProperty("author_id")
    private UUID authorID;

    @NotNull
    @JsonProperty("content")
    private Blob content;

    @Default
    public FileDto(String fileName, UUID authorID, Blob content){
        this.fileName = fileName;
        this.authorID = authorID;
        this.content = content;
    }

    public FileDto(String fileName, Blob content) {
        this.fileName = fileName;
        this.content = content;
    }
}
