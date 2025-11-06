package newspapercrud.ui.rest;

import newspapercrud.domain.model.CredentialDTO;
import newspapercrud.domain.service.CredentialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credentials")
@CrossOrigin(origins = "*")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody CredentialDTO credentials) {
        boolean result = credentialService.login(credentials);
        return ResponseEntity.ok(result);
    }
}

