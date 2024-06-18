package investment.api.business;

import investment.api.dtos.HashedPasswordDto;
import investment.api.dtos.LoginUserDto;
import investment.api.dtos.RegisterBrokerDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import investment.api.security.CustomPasswordEncoder;
import investment.api.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerBusiness {

    private final BrokerRepository brokerRepository;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public BrokerBusiness(BrokerRepository brokerRepository, CustomPasswordEncoder customPasswordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.brokerRepository = brokerRepository;
        this.customPasswordEncoder = customPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public List<Broker> getAllBrokers() {
        return brokerRepository.findAll();
    }

    public ResponseEntity<String> registerBroker(RegisterBrokerDto brokerDto) {
        if(brokerRepository.existsByUsername(brokerDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        HashedPasswordDto hashedPasswordDto = customPasswordEncoder.hashPassword(brokerDto.getPassword(), java.util.Optional.empty());

        Broker broker = new Broker(brokerDto.getUsername(), brokerDto.getCompany(), hashedPasswordDto.getHashedPassword(), hashedPasswordDto.getSalt());

        brokerRepository.save(broker);

        return new ResponseEntity<>("Broker registered successfully!", HttpStatus.OK);
    }

    public ResponseEntity<String> loginBroker(LoginUserDto brokerDetails) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(brokerDetails.getUsername(), brokerDetails.getPassword()));

            return new ResponseEntity<>(tokenService.generateToken(authentication), HttpStatus.OK);
        } catch(BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
