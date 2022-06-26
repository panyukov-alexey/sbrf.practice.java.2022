package sbrf.practice.jsv.list.dto.users;


import javax.validation.constraints.Pattern;

public class UpdateUserDto extends CreateUserDto {
    public UpdateUserDto(String username, @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$") String password) {
        super(username, password);
    }
}
