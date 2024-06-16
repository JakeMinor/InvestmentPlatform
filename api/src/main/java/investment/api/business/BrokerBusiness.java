package investment.api.business;

import investment.api.dtos.AuthenticationResponseDto;
import investment.api.dtos.HashedPasswordDto;
import investment.api.dtos.LoginBrokerDto;
import investment.api.dtos.RegisterBrokerDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import investment.api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrokerBusiness {

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private AuthenticationBusiness authenticationBusiness;

    private JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public BrokerBusiness(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    public List<Broker> getAllBrokers() {
        return brokerRepository.findAll();
    }

    public ResponseEntity<String> registerBroker(RegisterBrokerDto brokerDto) {
        if(brokerRepository.existsByUsername(brokerDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        HashedPasswordDto hashedPasswordDto = authenticationBusiness.hashPassword(brokerDto.getPassword(), java.util.Optional.empty());

        Broker broker = new Broker(brokerDto.getUsername(), brokerDto.getCompany(), hashedPasswordDto.getHashedPassword(), hashedPasswordDto.getSalt());


        try {
            brokerRepository.save(broker);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Broker registered successfully!", HttpStatus.OK);
    }

    public ResponseEntity<?> loginBroker(LoginBrokerDto brokerDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            brokerDto.getUsername(),
                            brokerDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>(new AuthenticationResponseDto(jwtTokenProvider.generateToken(authentication)), HttpStatus.OK);

        }
        catch(AuthenticationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
