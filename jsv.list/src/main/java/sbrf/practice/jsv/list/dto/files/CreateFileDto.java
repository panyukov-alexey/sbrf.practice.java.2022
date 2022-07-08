package sbrf.practice.jsv.list.dto.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sbrf.practice.jsv.list.validator.IsValidSchema;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateFileDto {

    @NotNull
    @JsonProperty("authorId")
    private UUID authorId;

    @NotNull
    @JsonProperty("file")
    @IsValidSchema
    private MultipartFile file;
}