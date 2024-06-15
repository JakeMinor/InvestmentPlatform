package investment.api.business;

import investment.api.dtos.HashedPasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Service
public class AuthenticationBusiness {

    @Autowired
    private SecureRandom secureRandom;

    @Autowired
    private MessageDigest messageDigest;

    public AuthenticationBusiness() { }

    public HashedPasswordDto hashPassword(String passwordToHash) {
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);

        messageDigest.update(salt);

        // Password Hash is too big, think I might need to store the Password Hash without the salt or find a way of setting a max size.
        return new HashedPasswordDto(messageDigest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8)), salt);
    }

}
