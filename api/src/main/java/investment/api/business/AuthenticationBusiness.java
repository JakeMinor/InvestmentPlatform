package investment.api.business;

import investment.api.dtos.HashedPasswordDto;
import investment.api.dtos.LoginDto;
import investment.api.dtos.RegisterBrokerDto;
import investment.api.dtos.RegisterInvestorDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.InvestorRepository;
import investment.api.repositories.entities.Broker;
import investment.api.repositories.entities.Investor;
import investment.api.security.CustomPasswordEncoder;
import investment.api.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationBusiness {

    private final BrokerRepository brokerRepository;
    private final InvestorRepository investorRepository;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenService;

    public AuthenticationBusiness(BrokerRepository brokerRepository, InvestorRepository investorRepository, CustomPasswordEncoder customPasswordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider tokenService) {
        this.brokerRepository = brokerRepository;
        this.investorRepository = investorRepository;
        this.customPasswordEncoder = customPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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

    public ResponseEntity<String> registerInvestor(RegisterInvestorDto investorDto) {
        if(investorRepository.existsByUsername(investorDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        HashedPasswordDto hashedPasswordDto = customPasswordEncoder.hashPassword(investorDto.getPassword(), java.util.Optional.empty());

        Investor investor = new Investor(investorDto.getUsername(), investorDto.getFirstName(), investorDto.getLastName(), hashedPasswordDto.getHashedPassword(), hashedPasswordDto.getSalt());

        investorRepository.save(investor);

        return new ResponseEntity<>("Investor registered successfully!", HttpStatus.OK);
    }

    public ResponseEntity<String> login(LoginDto userDetails) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername() + ":" + userDetails.getUserType().toString(), userDetails.getPassword()));

            return new ResponseEntity<>(tokenService.generateToken(authentication), HttpStatus.OK);
        } catch(BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
