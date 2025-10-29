package newspapercrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReaderEntity {
    private int id;
    private String name;
    private LocalDate dob;
    private CredentialEntity credential;

    public ReaderEntity(int id, String nameReader, LocalDate birthReader) {
        this.id = id;
        this.name = nameReader;
        this.dob = birthReader;
    }
}
