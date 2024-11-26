package com.example.appwww.Controllers;

import com.example.appwww.Components.UserResolver;
import com.example.appwww.Dtos.CreateVisitRequestDto;
import com.example.appwww.Services.VisitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class VisitController {

    private final VisitServiceImpl visitService;
    private final UserResolver userResolver;

    @Autowired
    public VisitController(VisitServiceImpl visitService, UserResolver userResolver) {
        this.visitService = visitService;
        this.userResolver = userResolver;
    }

    @PreAuthorize("hasAnyAuthority('VISIT_CREATE')")
    @PostMapping("/visits")
    public ResponseEntity<?> createVisit(@RequestBody CreateVisitRequestDto createVisitRequestDto,
                                         @RequestHeader(name = "Authorization", required = false) String token) {
        visitService.createVisit(userResolver.userIdResolver(token), createVisitRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
