package newspapercrud.domain.service;

import jakarta.inject.Inject;
import newspapercrud.dao.NewspaperRepository;
import newspapercrud.dao.ReadArticleRepository;
import newspapercrud.dao.ReaderRepository;
import newspapercrud.dao.model.CredentialEntity;
import newspapercrud.dao.model.NewspaperEntity;
import newspapercrud.dao.model.ReaderEntity;
import newspapercrud.domain.model.ReaderArticleDTO;
import newspapercrud.domain.model.ReaderDTO;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ReaderService {
    private final ReaderRepository readerRepository;
    private final NewspaperRepository newspaperRepository;
    private final ReadArticleRepository readArticleRepository;

    @Inject
    public ReaderService(ReaderRepository readerRepository, NewspaperRepository newspaperRepository, ReadArticleRepository readArticleRepository) {
        this.readerRepository = readerRepository;
        this.newspaperRepository = newspaperRepository;
        this.readArticleRepository = readArticleRepository;
    }

    public List<ReaderDTO> getAll() {
        List<ReaderDTO> readersUI = new ArrayList<>();
        readerRepository.getAll().forEach(mr ->
                readersUI.add(new ReaderDTO(mr.getId(), mr.getName(),
                        mr.getDob(), null)));
        return readersUI;

    }

    public ReaderArticleDTO get(int idReader) {
        ReaderEntity reader= readerRepository.get(idReader);
        ReaderArticleDTO readerUI= new ReaderArticleDTO(0, reader.getId(), reader.getName(),
                reader.getDob(),
                parseStringNewspaper(newspaperRepository.getAllByReader(reader.getId())), 0);
        return readerUI;
    }

    public List<ReaderArticleDTO> getAllReadersByArticle(int idArticle) {
        List<ReaderArticleDTO> readersUI = new ArrayList<>();
        readerRepository.getAllByArticle(idArticle).forEach(mr ->
                readersUI.add(new ReaderArticleDTO(idArticle, mr.getId(), mr.getName(),
                        mr.getDob(),
                        parseStringNewspaper(newspaperRepository.getAllByReader(mr.getId())), readArticleRepository.get(idArticle, mr.getId()).getRanking())));
        return readersUI;
    }

    public int addReader(ReaderDTO readerDTO) {
        return readerRepository.save(new ReaderEntity(readerDTO.getIdReader(),
                readerDTO.getNameReader(), readerDTO.getDobReader(),
                new CredentialEntity(readerDTO.getCredentialDTO().getUsername(), readerDTO.getCredentialDTO().getPassword())));
    }

    private List<NewspaperEntity> parseNewspaper(List<String> newspaper, int readerId) {
        List<NewspaperEntity> newspaperList = new ArrayList<>();
      //  newspaperList.forEach(newspaper -> newspaperList.add(new Newspaper(1,newspaper,readerId,"every 8 hours")));
        return newspaperList;
    }



    private List<String> parseStringNewspaper(List<NewspaperEntity> newspapers) {
        List<String> stringNewspaper = new ArrayList<>();
        newspapers.forEach(m -> stringNewspaper.add(m.getName()));
        return stringNewspaper;
    }

    public void deleteReader(int id) {
        readerRepository.delete(id);
    }

}
