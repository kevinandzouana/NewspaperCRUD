package newspapercrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NewspaperEntity {
    private int id;
    private String name;
    private LocalDate releaseDate;
}
