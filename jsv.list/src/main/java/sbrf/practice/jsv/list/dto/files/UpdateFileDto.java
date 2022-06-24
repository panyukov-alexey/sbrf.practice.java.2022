package sbrf.practice.jsv.list.dto.files;

import java.sql.Blob;

import javax.validation.constraints.NotNull;

public class UpdateFileDto extends CreateFileDto{
    public UpdateFileDto(@NotNull String fileName, @NotNull Blob content){
        super(fileName, content);
    }
}
