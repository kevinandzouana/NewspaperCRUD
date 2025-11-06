package newspapercrud.domain.service;

import newspapercrud.dao.CredentialRepository;
import newspapercrud.domain.model.CredentialDTO;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CredentialService {
    private final CredentialRepository credentialRepository;

    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public boolean login(CredentialDTO userCredentialsUI) {
        if (userCredentialsUI == null) return false;
        var credentialEntity = credentialRepository.get(userCredentialsUI.getUsername());
        if (credentialEntity == null) return false;

        String dbPassword = credentialEntity.getPassword();
        String uiPassword = userCredentialsUI.getPassword();
        if (dbPassword == null || uiPassword == null) return false;

        return dbPassword.trim().equals(uiPassword.trim());
    }
}
