package sbrf.practice.jsv.list.dto.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    @NotNull
    private UUID id;

    @NotNull
    @JsonProperty("username")
    private String username;
}
