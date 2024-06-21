package investment.api.security;

import investment.api.dtos.UserDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.InvestorRepository;
import investment.api.repositories.entities.Broker;
import investment.api.repositories.entities.Investor;
import org.hibernate.query.sqm.sql.ConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private InvestorRepository InvestorRepository;

    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {

        var name = username.split(":")[0];
        var userType = username.split(":")[1];

        if(Objects.equals(userType, "BROKER")) {
            Broker broker = brokerRepository.findByUsername(name);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_BROKER"));

            return new UserDto(broker.getUsername(), broker, formatPassword(broker.getPasswordHash()), broker.getPasswordSalt(), authorities);
        }
        else {
            Investor investor = InvestorRepository.findByUsername(name);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_INVESTOR"));

            return new UserDto(investor.getUsername(), investor, formatPassword(investor.getPasswordHash()), investor.getPasswordSalt(), authorities);
        }
    }

    private byte[] formatPassword(byte[] password) {
        int rlen = password.length;
        while (password[rlen-1] == 0) {
            --rlen;
        }

        return Arrays.copyOf(password, rlen);
    }
}