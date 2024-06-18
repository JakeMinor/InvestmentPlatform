package investment.api.dtos;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Arrays;

public class UserDto extends User {

    @Getter
    private byte[] passwordHash;

    @Getter
    private byte[] salt;

    public UserDto(String username, byte[] password, byte[] salt) {
        super(username, Arrays.toString(password), new ArrayList<GrantedAuthority>());

        this.passwordHash = password;
        this.salt = salt;
    }
}
