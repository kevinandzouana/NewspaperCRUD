package newspapercrud.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private int id;
    private String name;
    private TypeDTO typeDTO;
    private int npaperId;
    private double avgRating;
}
