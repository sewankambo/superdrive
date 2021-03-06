package com.udacity.jwdnd.course1.superdrive.service;

import com.udacity.jwdnd.course1.superdrive.mapper.UserMapper;
import com.udacity.jwdnd.course1.superdrive.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * This class handles login authentication. Annotated with @Service for
 * auto-detection through classpath scannig.
 */
@Service
public class AuthenticationService implements AuthenticationProvider {
    private HashService hashService;
    private UserMapper userMapper;
    
    public AuthenticationService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userMapper.getUser(username);
        
        if (user != null) {
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            
            if (hashedPassword.equals(user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
