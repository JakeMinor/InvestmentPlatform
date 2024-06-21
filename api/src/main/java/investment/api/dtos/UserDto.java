package investment.api.dtos;

import investment.api.repositories.entities.Broker;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Arrays;

public class UserDto extends User {

    @Getter
    private Broker broker;

    @Getter
    private byte[] passwordHash;

    @Getter
    private byte[] salt;

    public UserDto(String username, Broker broker, byte[] password, byte[] salt) {
        super(username, Arrays.toString(password), new ArrayList<GrantedAuthority>());

        this.broker = broker;
        this.passwordHash = password;
        this.salt = salt;
    }
}
