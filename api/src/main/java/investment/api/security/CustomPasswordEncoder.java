package investment.api.security;

import investment.api.dtos.HashedPasswordDto;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomPasswordEncoder {

    @Autowired
    private SecureRandom secureRandom;

    @Autowired
    private MessageDigest messageDigest;

    public HashedPasswordDto hashPassword(String passwordToHash, Optional<byte[]> passwordSalt) {
        byte[] salt = new byte[32];

        if(passwordSalt.isPresent()) {
            salt = passwordSalt.get();
        } else{
            secureRandom.nextBytes(salt);
        }

        messageDigest.update(salt);

        return new HashedPasswordDto(messageDigest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8)), salt);
    }

    public boolean matches(byte[] hashedPassword, byte[] hashedPasswordToCompare) {
        return Arrays.equals(hashedPassword, hashedPasswordToCompare);
    }
}
