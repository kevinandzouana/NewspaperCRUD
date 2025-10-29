package newspapercrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialEntity {
    private String userName;
    private String password;
    private int patientId;
    private int doctorId;

    public CredentialEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
