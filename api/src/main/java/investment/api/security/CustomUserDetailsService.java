package investment.api.security;

import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final BrokerRepository brokerRepository;

    @Autowired
    public CustomUserDetailsService(final BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Broker broker = brokerRepository.findByUsername(username);

        return new User(broker.getUsername(), Arrays.toString(broker.getPasswordHash()), new ArrayList<>());
    }
}
