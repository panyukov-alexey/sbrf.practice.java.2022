package sbrf.practice.jsv.list.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class CreateUserDto {
    @NotNull
    @JsonProperty("username")
    private String username;
    /**
     * Минимум одна буква, одна цифра и длина от 8 до 32 символов
     */
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*]\\d)[A-Za-z\\d]{8,32}$")
    @JsonProperty("password")
    private String password;

    public CreateUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
