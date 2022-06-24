package sbrf.practice.jsv.list.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateFileDto extends CreateFileDto {
    public UpdateFileDto(@NotNull MultipartFile file, @NotNull UUID userId) {
        super(file, userId);
    }
}
