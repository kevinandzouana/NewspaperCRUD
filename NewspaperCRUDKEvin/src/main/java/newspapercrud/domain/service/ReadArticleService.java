package newspapercrud.domain.service;


import newspapercrud.dao.ReadArticleRepository;
import newspapercrud.dao.model.ReadArticleEntity;
import newspapercrud.domain.model.ReaderArticleDTO;
import org.springframework.stereotype.Service;

@Service
public class ReadArticleService {

    private final ReadArticleRepository readArticleRepository;

    public ReadArticleService(ReadArticleRepository readArticleRepository) {
        this.readArticleRepository = readArticleRepository;
    }


    public int addReadArticle(ReaderArticleDTO readerArticleUI) {
        ReadArticleEntity readArticle = new ReadArticleEntity(0, readerArticleUI.getIdArticle(), readerArticleUI.getIdReader(), readerArticleUI.getRating());
        return readArticleRepository.save(readArticle);
            }

    public void updateReadArticle(ReaderArticleDTO readerArticleUI) {
        ReadArticleEntity readArticle = new ReadArticleEntity(0, readerArticleUI.getIdArticle(), readerArticleUI.getIdReader(), readerArticleUI.getRating());
        readArticleRepository.update(readArticle);
    }

    public boolean delete(ReaderArticleDTO readerArticleUI) {
        ReadArticleEntity readArticle = new ReadArticleEntity(0, readerArticleUI.getIdArticle(), readerArticleUI.getIdReader(), 0);
        return readArticleRepository.delete(readArticle);
    }
}
