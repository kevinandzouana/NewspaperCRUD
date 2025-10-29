package newspapercrud.domain.mappers;


import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.model.TypeEntity;
import newspapercrud.domain.model.ArticleDTO;
import newspapercrud.domain.model.TypeDTO;

public class ArticleMapper {
    public ArticleEntity dtoToEntity(ArticleDTO dto) {
        return new ArticleEntity(
                dto.getId(),
                dto.getName(),
                new TypeEntity(dto.getTypeDTO().getId(), dto.getTypeDTO().getName()),
                dto.getNpaperId());
    }

    public ArticleDTO entityToDto(ArticleEntity entity) {
        return new ArticleDTO(
                entity.getId(),
                entity.getName(),
                new TypeDTO(entity.getType().getId(), entity.getType().getDescription()),
                entity.getNPaperID(), 0);
    }

}
