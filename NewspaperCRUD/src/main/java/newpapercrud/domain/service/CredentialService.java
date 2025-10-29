package newspapercrud.domain.service;

import jakarta.inject.Inject;
import newspapercrud.dao.CredentialRepository;
import newspapercrud.domain.model.CredentialDTO;

public class CredentialService {
    private final CredentialRepository credentialRepository;

    @Inject
    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public boolean login(CredentialDTO userCredentialsUI) {
        if (credentialRepository.get(userCredentialsUI.getUsername()) == null)
            return false;
        else
            return credentialRepository
                .get(userCredentialsUI.getUsername())
                .getPassword().equals(userCredentialsUI.getPassword());
    }
}
