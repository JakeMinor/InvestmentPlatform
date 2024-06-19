package investment.api.business;

import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerBusiness {

    private final BrokerRepository brokerRepository;


    public BrokerBusiness(BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
    }

    public ResponseEntity<List<Broker>> getAllBrokers() {
        return new ResponseEntity<>(brokerRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteBroker(int brokerId) {
        brokerRepository.deleteById(brokerId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
