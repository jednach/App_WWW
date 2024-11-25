package com.example.appwww.Controllers;

import com.example.appwww.Components.UserResolver;
import com.example.appwww.Dtos.CreateRegistrationRequestDto;
import com.example.appwww.Services.RegistrationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationServiceImpl registrationService;
    private final UserResolver userResolver;

    public RegistrationController(RegistrationServiceImpl registrationService, UserResolver userResolver) {
        this.registrationService = registrationService;
        this.userResolver = userResolver;
    }

    @PostMapping("/registrations")
    public ResponseEntity<?> createRegistration(@RequestBody CreateRegistrationRequestDto registrationRequestDto,
                                                @RequestHeader(name = "Authorization", required = false) String token) {
        registrationService.createRegistration(registrationRequestDto, userResolver.userIdResolver(token));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/registration/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable("id") Long id,
                                                @RequestHeader(name = "Authorization", required = false) String token) {
        registrationService.deleteRegistration(id, userResolver.userIdResolver(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
