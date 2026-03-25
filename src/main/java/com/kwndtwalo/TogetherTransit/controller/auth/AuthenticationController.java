package com.kwndtwalo.TogetherTransit.controller.auth;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.dto.auth.AuthenticationDTO;
import com.kwndtwalo.TogetherTransit.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/create")
    public ResponseEntity<AuthenticationDTO> create(@RequestBody Authentication authentication) {

        Authentication createdAuthentication = authenticationService.create(authentication);

        if (createdAuthentication == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new AuthenticationDTO(createdAuthentication));
    }

    @GetMapping("/read/{Id}")
    public ResponseEntity<AuthenticationDTO> read(@PathVariable Long Id) {

        Authentication authentication = authenticationService.read(Id);
        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new AuthenticationDTO(authentication));
    }

    @PutMapping("/update")
    public ResponseEntity<AuthenticationDTO> update(@RequestBody Authentication authentication) {

        Authentication updatedAuthentication = authenticationService.update(authentication);
        if (updatedAuthentication == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new AuthenticationDTO(updatedAuthentication));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AuthenticationDTO>> getAllAuthentications() {

        List<AuthenticationDTO> authentication = authenticationService.getAuthentications()
                .stream()
                .map(AuthenticationDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(authentication);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long Id) {

        boolean deleted = authenticationService.delete(Id);

        return ResponseEntity.ok(deleted);
    }
}
