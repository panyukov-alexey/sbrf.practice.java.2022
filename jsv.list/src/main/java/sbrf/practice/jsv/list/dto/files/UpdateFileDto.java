package sbrf.practice.jsv.list.dto.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sbrf.practice.jsv.list.validator.IsValidSchema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFileDto {

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("file")
    @IsValidSchema
    private MultipartFile file;

}
