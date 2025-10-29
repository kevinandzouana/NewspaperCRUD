package newspapercrud.dao;

import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.model.CredentialEntity;

import java.util.List;

public interface CredentialRepository {

    List<CredentialEntity> getAll();
    boolean delete(int patient_id);
    void save(ArticleEntity patient);
    void update(CredentialEntity credential);
    CredentialEntity get(String username);
}
