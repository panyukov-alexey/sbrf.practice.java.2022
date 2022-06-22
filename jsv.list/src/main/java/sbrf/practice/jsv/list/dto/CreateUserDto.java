package sbrf.practice.jsv.list.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@AllArgsConstructor
public class CreateUserDto {
    @NotNull
    @JsonProperty("username")
    private String username;
    /**
     * Минимум одна буква, одна цифра и длина от 8 до 32 символов
     */
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    @JsonProperty("password")
    private String password;
}
