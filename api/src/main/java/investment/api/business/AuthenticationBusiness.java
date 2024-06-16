package investment.api.business;

import investment.api.dtos.HashedPasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthenticationBusiness {

    @Autowired
    private SecureRandom secureRandom;

    @Autowired
    private MessageDigest messageDigest;

    public AuthenticationBusiness() { }

    public HashedPasswordDto hashPassword(String passwordToHash, Optional<byte[]> passwordSalt) {
        byte[] salt = new byte[32];

        if(passwordSalt.isPresent()) {
            salt = passwordSalt.get();
        } else {
            secureRandom.nextBytes(salt);
        }

        messageDigest.update(salt);

        byte[] hashedPassword = messageDigest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));

        return new HashedPasswordDto(hashedPassword, salt);
    }

    public boolean doesPasswordMatch(String password, byte[] hashedPassword, byte[] salt) {
        HashedPasswordDto passwordToCompare = this.hashPassword(password, Optional.ofNullable(salt));

        byte[] p = Arrays.copyOf(hashedPassword, passwordToCompare.getHashedPassword().length);

        return Arrays.equals(p, passwordToCompare.getHashedPassword());
    }

}
