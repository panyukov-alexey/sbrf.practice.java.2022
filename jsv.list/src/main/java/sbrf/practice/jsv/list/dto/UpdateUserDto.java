package sbrf.practice.jsv.list.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UpdateUserDto extends CreateUserDto {
    public UpdateUserDto(@NotNull String username, @NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$") String password) {
        super(username, password);
    }
}
