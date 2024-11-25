package com.example.appwww.Components;

import com.example.appwww.Models.Entities.RoleEntity;
import com.example.appwww.Models.Entities.UserEntity;
import com.example.appwww.Services.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserResolver {
    private final JwtServiceImpl jwtService;

    @Autowired
    public UserResolver(JwtServiceImpl jwtService) {
        this.jwtService = jwtService;
    }

    public Long userIdResolver(String token){
        if(token == null || token.isEmpty()) return -2L;
        UserEntity currentUser = jwtService.extractUser(token.substring(7));
        return checkIfAdmin(currentUser) ? -1L : currentUser.getId();
    }

    private Boolean checkIfAdmin(UserEntity user){
        RoleEntity dummyRole = new RoleEntity();
        dummyRole.setName("ADMIN");
        return user.getRoles().contains(dummyRole);
    }
}
