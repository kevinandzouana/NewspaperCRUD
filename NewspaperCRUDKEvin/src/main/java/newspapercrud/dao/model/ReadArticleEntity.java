package newspapercrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadArticleEntity {
    private int id;
    private int articleId;
    private int readerId;
    private int ranking;
}
