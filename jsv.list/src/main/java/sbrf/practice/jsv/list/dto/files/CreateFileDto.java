package sbrf.practice.jsv.list.dto.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sbrf.practice.jsv.list.validator.IsValidSchema;
import sbrf.practice.jsv.list.validator.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFileDto {

    @NotNull
    @JsonProperty("authorId")
    private UUID authorId;

    @JsonProperty("filename")
    private String filename;

    @NotNull
    @NotEmpty
    @IsValidSchema
    @JsonProperty("file")
    private MultipartFile file;
}