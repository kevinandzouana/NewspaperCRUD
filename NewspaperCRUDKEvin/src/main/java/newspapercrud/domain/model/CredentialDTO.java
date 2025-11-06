package newspapercrud.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialDTO {
    @JsonAlias({"username","userName"})
    private String username;

    @JsonAlias({"password","pass"})
    private String password;
}
