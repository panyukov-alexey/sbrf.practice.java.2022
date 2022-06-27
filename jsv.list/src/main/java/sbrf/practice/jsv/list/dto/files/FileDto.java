package sbrf.practice.jsv.list.dto.files;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileDto {
    @NotNull
    @JsonProperty("fileName")
    private String fileName;

    @NotNull
    @JsonProperty("authorId")
    private UUID authorId;

    @NotNull
    @JsonProperty("file")
    private MultipartFile file;
}