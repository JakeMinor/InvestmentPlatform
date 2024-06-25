package investment.api.business;

import investment.api.dtos.BrokerDto;
import investment.api.dtos.UserDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerBusiness {

    private final BrokerRepository brokerRepository;


    public BrokerBusiness(BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
    }

    public ResponseEntity<List<BrokerDto>> getAllBrokers() {

        var brokers = brokerRepository.findAll();

        var brokerDtos = brokers.stream().map(br -> new BrokerDto(br.getId(), br.getUsername(), br.getCompany())).toList();

        return new ResponseEntity<>(brokerDtos, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteBroker(Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();

        if(!brokerRepository.existsById(user.getBroker().getId())) {
            return new ResponseEntity<>("Broker doesn't exist.", HttpStatus.BAD_REQUEST);
        }

        brokerRepository.deleteById(user.getBroker().getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
