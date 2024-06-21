package investment.api.security;

import investment.api.dtos.UserDto;
import investment.api.enums.UserTypeEnum;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

public class CustomAuthenticationProviderService implements AuthenticationManager {

    private CustomUserDetailsService customUserDetailsService;

    private CustomPasswordEncoder passwordEncoder;

    public CustomAuthenticationProviderService(CustomUserDetailsService customUserDetailsService, CustomPasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDto user;

        try {
            user = customUserDetailsService.loadUserByUsername(authentication.getName());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Username or Password");
        }

        var hashedPassword = passwordEncoder.hashPassword(authentication.getCredentials().toString(), Optional.ofNullable(user.getSalt()));

        var s = user.getPasswordHash();

        if(!passwordEncoder.matches(s, hashedPassword.getHashedPassword())) {
            throw new BadCredentialsException("Invalid Username or Password");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString());
    }


}
