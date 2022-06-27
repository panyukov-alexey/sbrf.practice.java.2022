package sbrf.practice.jsv.list.dto.files;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class UpdateFileDto extends CreateFileDto {

    public UpdateFileDto(UUID authorId, MultipartFile file) {
        super(authorId, file);
    }
}
