package investment.api.security;

import investment.api.dtos.UserDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private BrokerRepository brokerRepository;

    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Broker broker = brokerRepository.findByUsername(username);

        var password = broker.getPasswordHash();

        int rlen = password.length;
        while (password[rlen-1] == 0) {
            --rlen;
        }

        password = Arrays.copyOf(password, rlen);

        return new UserDto(broker.getUsername(), password, broker.getPasswordSalt());
    }
}