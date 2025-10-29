package newspapercrud.dao;

import newspapercrud.dao.model.ReadArticleEntity;

import java.util.List;


public interface ReadArticleRepository {
    List<ReadArticleEntity> getAllByArticleId();
    ReadArticleEntity get(int articleID, int readerID);
    ReadArticleEntity getByIdArticle(int articleID);

    int save(ReadArticleEntity readArticle);
    void update(ReadArticleEntity readArticle);
    boolean delete(ReadArticleEntity readArticle);
}
