package newspapercrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {
    private int id;
    private String name;
    private TypeEntity type;
    private int nPaperID;
}
