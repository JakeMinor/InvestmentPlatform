package investment.api.business;

import investment.api.dtos.HashedPasswordDto;
import investment.api.dtos.RegisterBrokerDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerBusiness {

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private AuthenticationBusiness authenticationBusiness;

    public BrokerBusiness() { }

    public List<Broker> getAllBrokers() {
        return brokerRepository.findAll();
    }

    public ResponseEntity<String> registerBroker(RegisterBrokerDto brokerDto) {
        if(brokerRepository.existsByUsername(brokerDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        HashedPasswordDto hashedPasswordDto = authenticationBusiness.hashPassword(brokerDto.getPassword());

        Broker broker = new Broker(brokerDto.getUsername(), brokerDto.getCompany(), hashedPasswordDto.getHashedPassword(), hashedPasswordDto.getSalt());


        brokerRepository.save(broker);

        return new ResponseEntity<>("Broker registered successfully!", HttpStatus.OK);
    }
}
